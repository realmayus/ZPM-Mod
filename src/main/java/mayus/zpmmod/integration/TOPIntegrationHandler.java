package mayus.zpmmod.integration;

import mayus.zpmmod.ZPMMod;
import net.minecraftforge.fml.common.Loader;

public class TOPIntegrationHandler {
    public static void registerIntegration() {
        if (Loader.isModLoaded("theoneprobe")) {
            ZPMMod.getLOGGER().info("TOP integration enabled!");
            TOPcompat.register();
        }
    }


}
