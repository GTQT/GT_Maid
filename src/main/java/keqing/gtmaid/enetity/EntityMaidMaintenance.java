package keqing.gtmaid.enetity;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMaintenanceHatch;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import java.lang.reflect.Field;
import java.util.Optional;

public class EntityMaidMaintenance extends EntityAIMoveToBlock {
    private static final ItemStack TAPE_STACK = new ItemStack(MetaItems.DUCT_TAPE.getMetaItem(), 1, 502);
    private static Optional<Field> TAPED_FIELD = Optional.empty();

    static {
        try {
            Field f = MetaTileEntityMaintenanceHatch.class.getDeclaredField("isTaped");
            f.setAccessible(true);
            TAPED_FIELD = Optional.of(f);
        } catch (NoSuchFieldException e) {
            System.out.println("[GTMaid] Failed to find MetaTileEntityMaintenanceHatch.isTaped field.");
        }
    }

    private final AbstractEntityMaid maid;

    public EntityMaidMaintenance(AbstractEntityMaid entityMaid, float speed) {
        super(entityMaid, speed, 8);
        this.maid = entityMaid;
    }

    @Override
    public void updateTask() {
        TileEntity te = maid.world.getTileEntity(destinationBlock);
        if (!(te instanceof IGregTechTileEntity)) return;

        MetaTileEntity mte = ((IGregTechTileEntity)te).getMetaTileEntity();
        if (!(mte instanceof MetaTileEntityMaintenanceHatch)) return;

        MetaTileEntityMaintenanceHatch hatch = (MetaTileEntityMaintenanceHatch)mte;
        if (getIsTaped(hatch)) return; // 已处理则跳过

        IItemHandler maidInv = maid.getAvailableInv(false);
        IItemHandler hatchInv = getHatchInventory(hatch);
        if (hatchInv == null) return;

        for (int i = 0; i < maidInv.getSlots(); i++) {
            ItemStack stack = maidInv.getStackInSlot(i);
            if (!stack.isItemEqual(TAPE_STACK)) continue;

            // 安全插入流程
            ItemStack result = hatchInv.insertItem(0, TAPE_STACK, true);
            if (!result.isEmpty()) continue; // 模拟插入失败

            hatchInv.insertItem(0, TAPE_STACK, false); // 实际插入
            maidInv.extractItem(i, 1, false);         // 抽取物品
            return; // 成功完成
        }
    }

    private boolean getIsTaped(MetaTileEntityMaintenanceHatch hatch) {
        return TAPED_FIELD.map(f -> {
            try {
                return f.getBoolean(hatch);
            } catch (IllegalAccessException e) {
                return true; // 保守策略：视为已处理
            }
        }).orElse(true); // 反射失败时视为已处理
    }

    private IItemHandler getHatchInventory(MetaTileEntityMaintenanceHatch hatch) {
        // 建议改用GregTech官方API获取库存
        // 此处保留原逻辑（生产环境应替换）
        try {
            Field f = hatch.getClass().getDeclaredField("itemStackHandler");
            f.setAccessible(true);
            return (IItemHandler) f.get(hatch);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected boolean shouldMoveTo(World world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof IGregTechTileEntity)) return false;

        MetaTileEntity mte = ((IGregTechTileEntity)te).getMetaTileEntity();
        if (!(mte instanceof MetaTileEntityMaintenanceHatch)) return false;

        return !getIsTaped((MetaTileEntityMaintenanceHatch)mte);
    }
}