package keqing.gtmaid.task;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMaintenanceHatch;
import keqing.gtmaid.api.GMLog;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import java.lang.reflect.Field;

public class EntityMaidMaintenance extends EntityAIMoveToBlock {
    private final AbstractEntityMaid maid;
    ItemStack taps = new ItemStack(MetaItems.DUCT_TAPE.getMetaItem(), 1, 502);

    public EntityMaidMaintenance(AbstractEntityMaid entityMaid, float speed) {
        super(entityMaid, speed, 8);
        this.maid = entityMaid;
    }

    @Override
    public void updateTask() {
        TileEntity tileEntity = maid.world.getTileEntity(destinationBlock);
        if (tileEntity instanceof IGregTechTileEntity) {
            IGregTechTileEntity gregTechTileEntity = (IGregTechTileEntity) tileEntity;
            MetaTileEntity metaTileEntity = gregTechTileEntity.getMetaTileEntity();
            if (metaTileEntity instanceof MetaTileEntityMaintenanceHatch) {
                MetaTileEntityMaintenanceHatch maintenanceHatch = (MetaTileEntityMaintenanceHatch) metaTileEntity;
                boolean isTaped = getIsTaped(maintenanceHatch);
                if (!isTaped) {
                    IItemHandler maidInv = maid.getAvailableInv(false);
                    IItemHandler itemStackHandler = getItemStackHandler(maintenanceHatch);
                    assert itemStackHandler != null;
                    for (int i = 0; i < maidInv.getSlots(); i++) {
                        ItemStack stackInSlot = maidInv.getStackInSlot(i);
                        if (stackInSlot.isItemEqual(taps)) {
                            maidInv.extractItem(i, 1, false);
                            itemStackHandler.insertItem(0, taps, false);
                            break;
                        }
                    }
                }
            }
        }
    }

    private boolean getIsTaped(MetaTileEntityMaintenanceHatch maintenanceHatch) {
        try {
            Field isTapedField = MetaTileEntityMaintenanceHatch.class.getDeclaredField("isTaped");
            isTapedField.setAccessible(true);
            return isTapedField.getBoolean(maintenanceHatch);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }

    private IItemHandler getItemStackHandler(MetaTileEntityMaintenanceHatch maintenanceHatch) {
        try {
            Field itemStackHandlerField = MetaTileEntityMaintenanceHatch.class.getDeclaredField("itemStackHandler");
            itemStackHandlerField.setAccessible(true);
            return (IItemHandler) itemStackHandlerField.get(maintenanceHatch);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof IGregTechTileEntity) {
            IGregTechTileEntity gregTechTileEntity = (IGregTechTileEntity) tileEntity;
            MetaTileEntity metaTileEntity = gregTechTileEntity.getMetaTileEntity();
            return metaTileEntity instanceof MetaTileEntityMaintenanceHatch;
        }
        return false;
    }
}
