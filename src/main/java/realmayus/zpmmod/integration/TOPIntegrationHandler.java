package realmayus.zpmmod.integration;

import realmayus.zpmmod.ZPMMod;
import net.minecraftforge.fml.common.Loader;

public class TOPIntegrationHandler {
    public static void registerIntegration() {
        if (Loader.isModLoaded("theoneprobe")) {
            ZPMMod.getLOGGER().info("TOP integration enabled!");
            TOPcompat.register();
        }
    }


}
