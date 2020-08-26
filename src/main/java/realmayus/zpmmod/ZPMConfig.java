package realmayus.zpmmod;
import net.minecraftforge.common.config.Config;

@net.minecraftforge.common.config.Config(modid = ZPMMod.MODID)
public class ZPMConfig {
    @Config.Comment(value = "Buffer value of the large controller. 1 RF/t is minimum!")
    public static int MAX_POWER_CONTROLLER_LARGE = 1;


    @Config.Comment(value = "The amount of energy in RF/t that will be stored in a ZPM.")
    public static int STORED_ZPM_ENERGY = Integer.MAX_VALUE;

    @Config.Comment(value = "The maximum amount of energy in RF/t that will be emitted by controllers, per ZPM")
    public static int EMIT_ENERGY_MAXIMUM = 1000000;
}
