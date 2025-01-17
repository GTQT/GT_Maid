package keqing.gtmaid.enetity;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import gregtech.api.items.toolitem.IGTTool;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import static keqing.gtmaid.GMConfig.runDelayTree;
import static keqing.gtmaid.api.GTUtils.getItemStacksFromOreNames;
import static keqing.gtmaid.api.GTUtils.getOreNameByStack;


public class EntityMaidHammer extends EntityAIMoveToBlock {
    private final AbstractEntityMaid maid;

    public EntityMaidHammer(AbstractEntityMaid entityMaid, float speed) {
        super(entityMaid, speed, 4);
        this.maid = entityMaid;
    }

    @Override
    public boolean shouldExecute() {
        if (this.maid.getHeldItemMainhand().getItem() instanceof IGTTool) {
            IGTTool tool = (IGTTool) this.maid.getHeldItemMainhand().getItem();
            if (!tool.getToolClasses(this.maid.getHeldItemMainhand()).contains("hammer")) return false;
            if (this.runDelay > 0) {
                --this.runDelay;
                return false;
            } else {
                this.runDelay = runDelayTree + maid.getRNG().nextInt(runDelayTree);
                return this.searchForDestination();
            }
        }
        return false;
    }

    public ItemStack setStack(ItemStack itemstack, int amount) {
        ItemStack itemstack1;
        itemstack1 = itemstack.copy();
        itemstack1.setCount(amount);
        return itemstack1;
    }

    @Override
    public void updateTask() {
        IItemHandler maidInv = maid.getAvailableInv(false);
        for (int i = 0; i < maidInv.getSlots(); i++) {
            ItemStack stackInSlot = maidInv.getStackInSlot(i);
            if (stackInSlot.isEmpty()) continue;
            String oreName = getOreNameByStack(stackInSlot);
            if (oreName.contains("null")) continue;
            if (oreName.contains("ore")) {
                ItemStack production = getItemStacksFromOreNames(oreName.replace("ore", "crushed"));
                maidInv.extractItem(i, 1, false);
                maid.entityDropItem(setStack(production, 1), 0);
                maid.getHeldItemMainhand().damageItem(1, maid);
                return;
            } else if (oreName.contains("crushedPurified")) {
                ItemStack production = getItemStacksFromOreNames(oreName.replace("crushedPurified", "dustPure"));
                maidInv.extractItem(i, 1, false);
                maid.entityDropItem(setStack(production, 1), 0);
                maid.getHeldItemMainhand().damageItem(1, maid);
                return;
            } else if (oreName.contains("crushedCentrifuged")) {
                ItemStack production = getItemStacksFromOreNames(oreName.replace("crushedCentrifuged", "dust"));
                maidInv.extractItem(i, 1, false);
                maid.entityDropItem(setStack(production, 1), 0);
                maid.getHeldItemMainhand().damageItem(1, maid);
                return;
            } else if (oreName.contains("crushed")) {
                ItemStack production = getItemStacksFromOreNames(oreName.replace("crushed", "dustImpure"));
                maidInv.extractItem(i, 1, false);
                maid.entityDropItem(setStack(production, 1), 0);
                maid.getHeldItemMainhand().damageItem(1, maid);
                return;
            } else if (oreName.contains("ingot") && stackInSlot.getCount() > 1) {
                ItemStack production = getItemStacksFromOreNames(oreName.replace("ingot", "plate"));
                maidInv.extractItem(i, 2, false);
                maid.entityDropItem(setStack(production, 1), 0);
                maid.getHeldItemMainhand().damageItem(1, maid);
                return;
            } else if (!oreName.contains("stickLong") && oreName.contains("stick") && stackInSlot.getCount() % 2 == 0) {
                ItemStack production = getItemStacksFromOreNames(oreName.replace("stick", "stickLong"));
                maidInv.extractItem(i, 2, false);
                maid.entityDropItem(setStack(production, 1), 0);
                maid.getHeldItemMainhand().damageItem(1, maid);
                return;
            } else if (!oreName.contains("stickLong") && oreName.contains("stick") && stackInSlot.getCount() % 2 == 1) {
                ItemStack production = getItemStacksFromOreNames(oreName.replace("stick", "ring"));
                maidInv.extractItem(i, 1, false);
                maid.entityDropItem(setStack(production, 1), 0);
                maid.getHeldItemMainhand().damageItem(1, maid);
                return;
            }
        }
    }

    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
        return true;
    }
}