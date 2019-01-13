package mayus.zpmmod;

import net.minecraftforge.common.config.Config;

@Config(modid = ZPMMod.MODID)
public class ModConfig {

    @Config.Comment(value = "Maximum of power the large ZPM Controller can hold")
    public static int MAX_POWER_CONTROLLER_LARGE = 100000;

    @Config.Comment(value = "Maximum of power the small ZPM Controller can hold")
    public static int MAX_POWER_CONTROLLER_SMALL = 50000;


}
