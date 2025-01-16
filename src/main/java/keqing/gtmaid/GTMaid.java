package keqing.gtmaid;

import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidAPI;
import keqing.gtmaid.api.GMLog;
import keqing.gtmaid.task.TaskBreakBlock;
import keqing.gtmaid.task.TaskBrokeTree;
import keqing.gtmaid.task.TaskDrillOre;
import keqing.gtmaid.task.TaskMaintenance;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = "gtmaid",
        name = "GTMaid",
        acceptedMinecraftVersions = "[1.12.2,1.13)",
        version = "0.0.1-beta",
        dependencies = "required-after:gregtech@[2.8.0-beta,);"
)
public class GTMaid {
    public static final String PACK = "1.0.1";

    public static final String MODID = "gtmaid";
    public static final String NAME = "GTMaid";
    public static final String VERSION = "0.1";

    @Mod.Instance(GTMaid.MODID)
    public static GTMaid instance;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        GMLog.init(event.getModLog());
    }
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {

    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LittleMaidAPI.registerTask(new TaskMaintenance());
        LittleMaidAPI.registerTask(new TaskBrokeTree());
        LittleMaidAPI.registerTask(new TaskBreakBlock(Blocks.SAND,-4,4,"sand"));
        LittleMaidAPI.registerTask(new TaskBreakBlock(Blocks.GRAVEL,-4,4,"gravel"));
        LittleMaidAPI.registerTask(new TaskBreakBlock(Blocks.DIRT,-6,2,"dirt"));
        LittleMaidAPI.registerTask(new TaskDrillOre());
    }
}
