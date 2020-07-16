package realmayus.zpmmod.integration;

import li.cil.oc.api.Driver;
import realmayus.zpmmod.ZPMMod;

public class OCIntegrationHandler {

    public static void registerIntegration() {
            ZPMMod.getLOGGER().info("\n+++++++++\nOpenComputers Integration loaded!\n+++++++++");
            Driver.add(new DriverController());

    }
}
