package keqing.gtmaid.enetity;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import com.github.tartaricacid.touhoulittlemaid.util.ItemFindUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import static net.minecraft.block.BlockCauldron.LEVEL;

public class EntityMaidAddWater extends EntityAIMoveToBlock {
    private final AbstractEntityMaid maid;

    public EntityMaidAddWater(AbstractEntityMaid entityMaid, float speed) {
        super(entityMaid, speed, 8);
        this.maid = entityMaid;
    }

    @Override
    public void updateTask() {
        IBlockState blockState = maid.world.getBlockState(destinationBlock);
        Block block = blockState.getBlock();
        if (block instanceof BlockCauldron) {
            if (blockState.getValue(LEVEL) == 3) return;
            ItemStack bucket = ItemFindUtil.getStack(this.maid.getAvailableInv(false), (stack) -> stack.getItem() == Items.WATER_BUCKET);
            if (bucket != ItemStack.EMPTY) {
                bucket.shrink(1);
                ItemHandlerHelper.insertItemStacked(this.maid.getAvailableInv(false), new ItemStack(Items.BUCKET), false);
                ((BlockCauldron) block).setWaterLevel(maid.world, destinationBlock, blockState, blockState.getValue(LEVEL) + 1);
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
