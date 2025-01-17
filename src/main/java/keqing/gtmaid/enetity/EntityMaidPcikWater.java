package keqing.gtmaid.enetity;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import com.github.tartaricacid.touhoulittlemaid.util.ItemFindUtil;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class EntityMaidPcikWater extends EntityAIMoveToBlock {
    private final AbstractEntityMaid maid;

    public EntityMaidPcikWater(AbstractEntityMaid entityMaid, float speed) {
        super(entityMaid, speed, 8);
        this.maid = entityMaid;
    }


    @Override
    public void updateTask() {
        ItemStack bucket = ItemFindUtil.getStack(this.maid.getAvailableInv(false), (stack) -> stack.getItem() == Items.BUCKET);
        if (bucket != ItemStack.EMPTY) {
            bucket.shrink(1);
            maid.world.setBlockState(destinationBlock, Blocks.AIR.getDefaultState());
            ItemHandlerHelper.insertItemStacked(this.maid.getAvailableInv(false), new ItemStack(Items.WATER_BUCKET), false);
        }
    }


    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos) == Blocks.WATER.getDefaultState();
    }
}
