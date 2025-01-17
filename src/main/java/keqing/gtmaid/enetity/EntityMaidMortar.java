package keqing.gtmaid.enetity;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import static gregtech.api.unification.material.Materials.*;
import static keqing.gtmaid.GMConfig.runDelayTree;
import static keqing.gtmaid.api.GTUtils.*;
import static net.minecraft.block.Block.getBlockFromItem;


public class EntityMaidMortar extends EntityAIMoveToBlock {
    private final AbstractEntityMaid maid;

    public EntityMaidMortar(AbstractEntityMaid entityMaid, float speed) {
        super(entityMaid, speed, 4);
        this.maid = entityMaid;
    }

    @Override
    public boolean shouldExecute() {
        if (this.maid.getHeldItemMainhand().getItem() instanceof IGTTool) {
            IGTTool tool = (IGTTool) this.maid.getHeldItemMainhand().getItem();
            if (!tool.getToolClasses(this.maid.getHeldItemMainhand()).contains("mortar")) return false;
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

            if (getBlockFromItem(stackInSlot.getItem()) == Blocks.SAND) {
                maidInv.extractItem(i, 1, false);
                maid.entityDropItem(OreDictUnifier.get(OrePrefix.dust, Quartzite), 0);
                maid.getHeldItemMainhand().damageItem(1, maid);
                return;
            } else if (getBlockFromItem(stackInSlot.getItem()) == Blocks.CLAY) {
                maidInv.extractItem(i, 1, false);
                maid.entityDropItem(OreDictUnifier.get(OrePrefix.dust, Clay), 0);
                maid.getHeldItemMainhand().damageItem(1, maid);
                return;
            } else if (getBlockFromItem(stackInSlot.getItem()) == Blocks.BRICK_BLOCK) {
                maidInv.extractItem(i, 1, false);
                maid.entityDropItem(OreDictUnifier.get(OrePrefix.dust, Brick), 0);
                maid.getHeldItemMainhand().damageItem(1, maid);
                return;
            } else if (getBlockFromItem(stackInSlot.getItem()) == Blocks.GRAVEL) {
                maidInv.extractItem(i, 1, false);
                maid.entityDropItem(new ItemStack(Items.FLINT), 0);
                maid.getHeldItemMainhand().damageItem(1, maid);
                return;
            } else if (stackInSlot.getItem() == Items.FLINT) {
                maidInv.extractItem(i, 1, false);
                maid.entityDropItem(OreDictUnifier.get(OrePrefix.dust, Flint), 0);
                maid.getHeldItemMainhand().damageItem(1, maid);
                return;
            }
            String oreName = getOreNameByStack(stackInSlot);
            if (oreName.contains("ingot") && (
                    oreName.contains("Iron")
                            || oreName.contains("Copper")
                            || oreName.contains("Gold")
                            || oreName.contains("Silver")
                            || oreName.contains("Tin")
                            || oreName.contains("Zinc")
                            || oreName.contains("Invar")
                            || oreName.contains("Bronze")
                            || oreName.contains("Lead")
                            || oreName.contains("Steel")
                            || oreName.contains("Brass")
                            || oreName.contains("Antimony")
                            || oreName.contains("Electrum")
            )) {
                ItemStack production = getItemStacksFromOreNames(oreName.replace("ingot", "dust"));
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