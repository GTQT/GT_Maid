package keqing.gtmaid.enetity;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import static keqing.gtmaid.GMConfig.*;


public class EntityMaidBrokeTree extends EntityAIMoveToBlock {
    private final AbstractEntityMaid maid;

    public EntityMaidBrokeTree(AbstractEntityMaid entityMaid, float speed) {
        super(entityMaid, speed, 4);
        this.maid = entityMaid;
    }

    @Override
    public boolean searchForDestination() {
        int i = 4;
        BlockPos blockpos = new BlockPos(this.maid);

        for (int k = -1; k <= 8; k++) {
            for (int l = 0; l < i; ++l) {
                for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                    for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                        BlockPos blockpos1 = blockpos.add(i1, k - 1, j1);

                        if (maid.isWithinHomeDistanceFromPosition(blockpos1) && this.shouldMoveTo(maid.world, blockpos1)) {
                            this.destinationBlock = blockpos1;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean shouldExecute() {
        if (this.maid.getHeldItemMainhand().getItem() instanceof ItemTool tool) {
            if (!tool.getToolClasses(this.maid.getHeldItemMainhand()).contains("axe")) return false;
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
        if (chainSwitch) {
            tryToBrokeMore(destinationBlock);
        } else {
            IBlockState blockState = maid.world.getBlockState(destinationBlock);
            Block block = blockState.getBlock();
            maid.entityDropItem(block.getPickBlock(blockState, null, maid.world, destinationBlock, null), 0);
            maid.world.setBlockState(destinationBlock, Blocks.AIR.getDefaultState());
            maid.getHeldItemMainhand().damageItem(1, maid);
        }

    }

    public void tryToBrokeMore(BlockPos destinationBlock) {
        // 获取指定位置的方块状态
        IBlockState blockState = maid.world.getBlockState(destinationBlock);
        // 获取方块
        Block block = blockState.getBlock();

        for (int i = -chainRange; i <= chainRange; i++) {
            for (int j = -4; j <= 12; j++) {
                for (int k = -chainRange; k <= chainRange; k++) {
                    BlockPos blockpos1 = destinationBlock.add(i, j, k);
                    IBlockState blockState1 = maid.world.getBlockState(blockpos1);
                    Block block1 = blockState1.getBlock();
                    if (block == block1) {
                        maid.entityDropItem(block1.getPickBlock(blockState1, null, maid.world, blockpos1, null), 0);
                        maid.world.setBlockState(blockpos1, Blocks.AIR.getDefaultState());
                        maid.getHeldItemMainhand().damageItem(1, maid);
                    }
                }
            }
        }
    }

    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
        // 获取指定位置的方块状态
        IBlockState blockState = worldIn.getBlockState(pos);
        // 获取方块
        Block block = blockState.getBlock();
        ItemStack itemStack = new ItemStack(block);
        if (itemStack.isEmpty()) {
            return false;
        }
        // 使用 OreDictionary 检查方块是否为树木
        int[] oreIDs = OreDictionary.getOreIDs(itemStack);
        for (int oreID : oreIDs) {
            String oreName = OreDictionary.getOreName(oreID);
            if (oreName.contains("log")) {
                return true;
            }
        }
        return false;
    }
}