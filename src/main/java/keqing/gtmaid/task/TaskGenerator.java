package keqing.gtmaid.task;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import com.github.tartaricacid.touhoulittlemaid.api.IMaidTask;
import com.github.tartaricacid.touhoulittlemaid.api.util.Util;
import com.github.tartaricacid.touhoulittlemaid.init.MaidSoundEvent;
import gregtech.common.items.MetaItems;
import keqing.gtmaid.enetity.EntityMaidGenerator;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import static keqing.gtmaid.gtmaid.Tags.MOD_ID;

public class TaskGenerator implements IMaidTask {
    public static final ResourceLocation UID = new ResourceLocation(MOD_ID, "generator");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(MetaItems.BATTERY_HULL_SMALL_VANADIUM.getMetaItem(), 1, 720);
    }

    @Override
    public SoundEvent getAmbientSound(AbstractEntityMaid maid) {
        return Util.environmentSound(maid, MaidSoundEvent.MAID_IDLE, 0.1f);
    }

    @Override
    public EntityAIBase createAI(AbstractEntityMaid maid) {
        return new EntityMaidGenerator(maid, 0.6f);
    }
}
