package mayus.zpmmod;


import li.cil.oc.api.Driver;
import mayus.zpmmod.integration.MainCompatHandler;
import mayus.zpmmod.network.PacketHandler;
import mayus.zpmmod.util.GuiHandler;
import mayus.zpmmod.util.DriverController;
import mayus.zpmmod.util.LootHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ZPMMod.MODID, name = ZPMMod.MODNAME, version = ZPMMod.MODVERSION, dependencies = "after:OpenComputers", useMetadata = true)
public class ZPMMod {

    public static final String MODID = "zpmmod";
    public static final String MODNAME = "ZPM Mod";
    public static final String MODVERSION= "0.0.1";

    public static CreativeTabs creativeTab = new CreativeTabs("zpmmod") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.CONTROLLER_SMALL);
        }
    };

    @Mod.Instance
    public static ZPMMod instance;

    private static final Logger LOGGER = LogManager.getLogger();

    public static Logger getLOGGER() {
        return LOGGER;
    }

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        PacketHandler.registerMessages();
        MainCompatHandler.registerIntegrations();

        MinecraftForge.EVENT_BUS.register(new LootHandler());
        LootTableList.register(new ResourceLocation(ZPMMod.MODID, "inject/simple_dungeon"));
        LootTableList.register(new ResourceLocation(ZPMMod.MODID, "inject/stronghold_corridor"));
        LootTableList.register(new ResourceLocation(ZPMMod.MODID, "inject/spawn_bonus_chest"));
        LootTableList.register(new ResourceLocation(ZPMMod.MODID, "inject/desert_pyramid"));
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ZPMMod.instance, new GuiHandler());
        Driver.add(new DriverController());
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }
}
