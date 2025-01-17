package keqing.gtmaid.enetity;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityMaidGenerator extends EntityAIMoveToBlock {
    private final AbstractEntityMaid maid;

    public EntityMaidGenerator(AbstractEntityMaid entityMaid, float speed) {
        super(entityMaid, speed, 8);
        this.maid = entityMaid;
    }

    @Override
    public void updateTask() {
        TileEntity tileEntity = maid.world.getTileEntity(destinationBlock);
        if (tileEntity instanceof IGregTechTileEntity) {
            IGregTechTileEntity gregTechTileEntity = (IGregTechTileEntity) tileEntity;
            MetaTileEntity metaTileEntity = gregTechTileEntity.getMetaTileEntity();
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (metaTileEntity.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                    IEnergyContainer container = metaTileEntity.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                    assert container != null;
                    container.addEnergy(Math.min(Math.max(maid.getExp() * 2, 1), 32));
                }
            }
        }
    }


    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof IGregTechTileEntity) {
            IGregTechTileEntity gregTechTileEntity = (IGregTechTileEntity) tileEntity;
            MetaTileEntity metaTileEntity = gregTechTileEntity.getMetaTileEntity();
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (metaTileEntity.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                    IEnergyContainer container = metaTileEntity.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                    assert container != null;
                    return container.getEnergyStored() < container.getEnergyCapacity();
                }
            }
        }
        return false;
    }
}
