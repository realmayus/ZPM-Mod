package realmayus.zpmmod;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandlers {

    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent e) {
        if(e.getModID().equalsIgnoreCase(ZPMMod.MODID)) {
            ConfigManager.sync(ZPMMod.MODID, Config.Type.INSTANCE);
        }
    }

}