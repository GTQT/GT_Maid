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

    @Config.Comment("尝试连锁开关")
    public static boolean chainSwitch = true;

    @Config.Comment("尝试连锁范围（半径）")
    public static int chainRange = 1;

    @Config.Comment("矿辞模式")
    public static boolean oreMode = true;
}
