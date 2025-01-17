package keqing.gtmaid.enetity;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import gregtech.api.items.toolitem.IGTTool;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import static keqing.gtmaid.GMConfig.runDelayTree;
import static keqing.gtmaid.api.GTUtils.*;


public class EntityMaidCutter extends EntityAIMoveToBlock {
    private final AbstractEntityMaid maid;

    public EntityMaidCutter(AbstractEntityMaid entityMaid, float speed) {
        super(entityMaid, speed, 4);
        this.maid = entityMaid;
    }

    @Override
    public boolean shouldExecute() {
        if (this.maid.getHeldItemMainhand().getItem() instanceof IGTTool) {
            IGTTool tool = (IGTTool) this.maid.getHeldItemMainhand().getItem();
            if (!tool.getToolClasses(this.maid.getHeldItemMainhand()).contains("wirecutter")) return false;
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

    @Override
    public void updateTask() {
        IItemHandler maidInv = maid.getAvailableInv(false);
        for (int i = 0; i < maidInv.getSlots(); i++) {
            ItemStack stackInSlot = maidInv.getStackInSlot(i);
            if (stackInSlot.isEmpty()) continue;
            String oreName = getOreNameByStack(stackInSlot);
            if (oreName.contains("null")) continue;
            if (oreName.contains("plate")) {
                ItemStack production = getItemStacksFromOreNames(oreName.replace("plate", "wireGtSingle"));
                maidInv.extractItem(i, 1, false);
                maid.entityDropItem(setStack(production, 1), 0);
                maid.getHeldItemMainhand().damageItem(1, maid);
                return;
            } else if (oreName.contains("foil")) {
                ItemStack production = getItemStacksFromOreNames(oreName.replace("foil", "wireFine"));
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