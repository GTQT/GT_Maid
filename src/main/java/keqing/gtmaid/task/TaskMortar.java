package keqing.gtmaid.task;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import com.github.tartaricacid.touhoulittlemaid.api.IMaidTask;
import com.github.tartaricacid.touhoulittlemaid.api.util.Util;
import com.github.tartaricacid.touhoulittlemaid.init.MaidSoundEvent;
import keqing.gtmaid.enetity.EntityMaidMortar;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import static gregtech.api.unification.material.Materials.Iron;
import static gregtech.common.items.ToolItems.MORTAR;
import static keqing.gtmaid.gtmaid.Tags.MOD_ID;

public class TaskMortar implements IMaidTask {
    public static final ResourceLocation UID = new ResourceLocation(MOD_ID, "mortar");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public ItemStack getIcon() {
        return MORTAR.get(Iron);
    }

    @Override
    public SoundEvent getAmbientSound(AbstractEntityMaid maid) {
        return Util.environmentSound(maid, MaidSoundEvent.MAID_IDLE, 0.1f);
    }

    @Override
    public EntityAIBase createAI(AbstractEntityMaid maid) {
        return new EntityMaidMortar(maid, 0.6f);
    }
}
