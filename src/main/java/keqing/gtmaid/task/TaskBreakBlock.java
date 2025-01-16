package keqing.gtmaid.task;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import com.github.tartaricacid.touhoulittlemaid.api.IMaidTask;
import com.github.tartaricacid.touhoulittlemaid.api.util.Util;
import com.github.tartaricacid.touhoulittlemaid.init.MaidSoundEvent;
import keqing.gtmaid.enetity.EntityMaidBrokeBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import static keqing.gtmaid.gtmaid.Tags.MOD_ID;

public class TaskBreakBlock implements IMaidTask {
    Block block;
    String task;
    int low;
    int high;

    public TaskBreakBlock(Block sand, int i, int i1, String task) {
        this.block = sand;
        this.task = task;
        this.low = i;
        this.high = i1;
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(MOD_ID, task);
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(block, 1, 0);
    }

    @Override
    public SoundEvent getAmbientSound(AbstractEntityMaid maid) {
        return Util.environmentSound(maid, MaidSoundEvent.MAID_BREAK, 0.1f);
    }

    @Override
    public EntityAIBase createAI(AbstractEntityMaid maid) {
        return new EntityMaidBrokeBlock(block, maid, 0.6f);
    }
}
