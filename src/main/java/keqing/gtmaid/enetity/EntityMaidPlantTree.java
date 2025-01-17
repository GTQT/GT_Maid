package keqing.gtmaid.enetity;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class EntityMaidPlantTree extends EntityAIMoveToBlock {
    private final AbstractEntityMaid maid;

    public EntityMaidPlantTree(AbstractEntityMaid entityMaid, float speed) {
        super(entityMaid, speed, 8);
        this.maid = entityMaid;
    }


    @Override
    public void updateTask() {
        IItemHandler maidInv = maid.getAvailableInv(false);
        for (int i = 0; i < maidInv.getSlots(); i++) {
            ItemStack stackInSlot = maidInv.getStackInSlot(i);
            if (stackInSlot.getItem().getTranslationKey().contains("sapling")) {
                Item item = stackInSlot.getItem();
                if (item instanceof ItemBlock) {
                    ItemBlock itemBlock = (ItemBlock) item;
                    IBlockState newState = itemBlock.getBlock().getStateForPlacement(maid.world, destinationBlock, EnumFacing.UP, 0.5f, 0.5f, 0.5f, stackInSlot.getMetadata(), maid, EnumHand.MAIN_HAND);
                    maid.world.setBlockState(destinationBlock, newState);
                    stackInSlot.shrink(1);
                }
            }
        }
    }


    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos) == Blocks.AIR.getDefaultState())
            return worldIn.getBlockState(pos.add(0, -1, 0)) == Blocks.GRASS.getDefaultState() || worldIn.getBlockState(pos.add(0, -1, 0)) == Blocks.DIRT.getDefaultState();
        return false;
    }
}
