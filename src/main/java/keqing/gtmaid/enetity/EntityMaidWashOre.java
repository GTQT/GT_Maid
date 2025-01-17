package keqing.gtmaid.enetity;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import static keqing.gtmaid.api.GTUtils.*;
import static net.minecraft.block.BlockCauldron.LEVEL;

public class EntityMaidWashOre extends EntityAIMoveToBlock {
    private final AbstractEntityMaid maid;

    public EntityMaidWashOre(AbstractEntityMaid entityMaid, float speed) {
        super(entityMaid, speed, 8);
        this.maid = entityMaid;
    }

    @Override
    public void updateTask() {
        IBlockState blockState = maid.world.getBlockState(destinationBlock);
        Block block = blockState.getBlock();
        if (block instanceof BlockCauldron) {
            if (blockState.getValue(LEVEL) == 0) return;
            IItemHandler maidInv = maid.getAvailableInv(false);
            for (int i = 0; i < maidInv.getSlots(); i++) {
                ItemStack stackInSlot = maidInv.getStackInSlot(i);
                if (stackInSlot.isEmpty()) continue;
                String oreName = getOreNameByStack(stackInSlot);
                if (oreName.contains("null")) continue;
                if (oreName.contains("crushedPurified")) continue;
                if (oreName.contains("crushed")) {
                    ItemStack production = getItemStacksFromOreNames(oreName.replace("crushed", "crushedPurified"));
                    int amount = stackInSlot.getCount();
                    maidInv.extractItem(i, stackInSlot.getCount(), false);
                    maidInv.insertItem(i, setStack(production, amount), false);
                    ((BlockCauldron) block).setWaterLevel(maid.world, destinationBlock, blockState, blockState.getValue(LEVEL) - 1);
                    return;
                } else if (oreName.contains("dustImpure")) {
                    ItemStack production = getItemStacksFromOreNames(oreName.replace("dustImpure", "dust"));
                    int amount = stackInSlot.getCount();
                    maidInv.extractItem(i, stackInSlot.getCount(), false);
                    maidInv.insertItem(i, setStack(production, amount), false);
                    ((BlockCauldron) block).setWaterLevel(maid.world, destinationBlock, blockState, blockState.getValue(LEVEL) - 1);
                    return;
                } else if (oreName.contains("dustPure")) {
                    ItemStack production = getItemStacksFromOreNames(oreName.replace("dustPure", "dust"));
                    int amount = stackInSlot.getCount();
                    maidInv.extractItem(i, stackInSlot.getCount(), false);
                    maidInv.insertItem(i, setStack(production, amount), false);
                    ((BlockCauldron) block).setWaterLevel(maid.world, destinationBlock, blockState, blockState.getValue(LEVEL) - 1);
                    return;
                }

            }
        }
    }

    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
        IBlockState blockState = worldIn.getBlockState(pos);
        Block block = blockState.getBlock();
        return block instanceof BlockCauldron;
    }
}
