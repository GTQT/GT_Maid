package keqing.gtmaid;

import net.minecraftforge.common.config.Config;

@Config(modid = GTMaid.MODID)
public class GMConfig {
    @Config.Comment("执行挖矿类任务的间隔检测频率")
    public static int runDelayOre = 40;

    @Config.Comment("执行砍树类任务的间隔检测频率")
    public static int runDelayTree = 40;

    @Config.Comment("执行挖掘类（挖泥土 沙子 沙砾）任务的间隔检测频率")
    public static int runDelayBroke = 40;
}
