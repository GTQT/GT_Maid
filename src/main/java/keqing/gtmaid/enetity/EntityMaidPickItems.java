package keqing.gtmaid.enetity;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTTransferUtils;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class EntityMaidPickItems extends EntityAIMoveToBlock {
    private final AbstractEntityMaid maid;

    public EntityMaidPickItems(AbstractEntityMaid entityMaid, float speed) {
        super(entityMaid, speed, 8);
        this.maid = entityMaid;
    }

    @Override
    public void updateTask() {
        TileEntity tileEntity = maid.world.getTileEntity(destinationBlock);
        if (tileEntity instanceof IGregTechTileEntity) {
            IGregTechTileEntity gregTechTileEntity = (IGregTechTileEntity) tileEntity;
            MetaTileEntity metaTileEntity = gregTechTileEntity.getMetaTileEntity();
            IItemHandler exportItemStack = metaTileEntity.getExportItems();
            IItemHandler maidInv = maid.getAvailableInv(false);
            GTTransferUtils.moveInventoryItems(exportItemStack, maidInv);
        }
    }

    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        return tileEntity instanceof IGregTechTileEntity;
    }
}
