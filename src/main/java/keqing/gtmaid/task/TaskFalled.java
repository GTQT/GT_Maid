package keqing.gtmaid.task;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import com.github.tartaricacid.touhoulittlemaid.api.IMaidTask;
import com.github.tartaricacid.touhoulittlemaid.api.util.Util;
import com.github.tartaricacid.touhoulittlemaid.init.MaidSoundEvent;
import gregtech.common.items.MetaItems;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import static keqing.gtmaid.gtmaid.Tags.MOD_ID;

public class TaskFalled implements IMaidTask {
    public static final ResourceLocation UID = new ResourceLocation(MOD_ID, "falled");
    @Override
    public ResourceLocation getUid() {
        return UID;
    }
    @Override
    public ItemStack getIcon() {
        return new ItemStack(Blocks.LOG, 1,1);
    }
    @Override
    public SoundEvent getAmbientSound(AbstractEntityMaid maid) {
        return Util.environmentSound(maid, MaidSoundEvent.MAID_IDLE, 0.1f);
    }
    @Override
    public EntityAIBase createAI(AbstractEntityMaid maid) {
        return new EntityMaidFalled(maid, 0.6f);
    }
}
