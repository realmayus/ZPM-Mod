package mayus.zpmmod;
import net.minecraftforge.common.config.Config;

@net.minecraftforge.common.config.Config(modid = ZPMMod.MODID)
public class ZPMConfig {
    @Config.Comment(value = "Buffer value of the large controller. 1 RF/t is minimum!")
    public static int MAX_POWER_CONTROLLER_LARGE = 1;
}
