package mayus.zpmmod.api;

import net.minecraftforge.fml.common.Loader;

public class MainCompatHandler {
    public static void registerTOP() {
        if (Loader.isModLoaded("theoneprobe")) {
            System.out.println("#########################################################");
            System.out.println("TOP loaded");
            System.out.println("#########################################################");
            TOPcompat.register();
        }
    }
}
