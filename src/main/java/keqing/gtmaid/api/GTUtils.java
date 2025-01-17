package keqing.gtmaid.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collection;
import java.util.Iterator;

public class GTUtils {
    public static String getOreNameByStack(ItemStack stackInSlot) {
        if (stackInSlot.isEmpty()) return "null";

        int[] oreIDs = OreDictionary.getOreIDs(stackInSlot);
        if (oreIDs == null || oreIDs.length == 0) return "null";

        int oreID = oreIDs[0];
        String oreName = OreDictionary.getOreName(oreID);
        if (oreName == null) return "null";
        else return oreName;
    }

    public static ItemStack getItemStacksFromOreNames(String oreName) {
        Collection<ItemStack> ores = OreDictionary.getOres(oreName);
        Iterator<ItemStack> iterator = ores.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack setStack(ItemStack itemstack, int amount) {
        ItemStack itemstack1;
        itemstack1 = itemstack.copy();
        itemstack1.setCount(amount);
        return itemstack1;
    }
}
