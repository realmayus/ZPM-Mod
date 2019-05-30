package mayus.zpmmod.integration;

import net.minecraftforge.fml.common.Loader;

public class MainCompatHandler {
    public static void registerIntegrations() {
        if (Loader.isModLoaded("theoneprobe")) {
            System.out.println("#########################################################");
            System.out.println("TOP integration enabled!");
            System.out.println("#########################################################");
            TOPcompat.register();
        }
        if (Loader.isModLoaded("opencomputers")) {
            System.out.println("#########################################################");
            System.out.println("OpenComputers integration enabled!");
            System.out.println("#########################################################");
        }
    }


}
