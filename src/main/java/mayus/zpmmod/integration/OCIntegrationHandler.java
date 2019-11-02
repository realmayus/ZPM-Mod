package mayus.zpmmod.integration;

import li.cil.oc.api.Driver;
import mayus.zpmmod.ZPMMod;

public class OCIntegrationHandler {

    public static void registerIntegration() {
            ZPMMod.getLOGGER().info("\n+++++++++\nOpenComputers Integration loaded!\n+++++++++");
            Driver.add(new DriverController());

    }
}
