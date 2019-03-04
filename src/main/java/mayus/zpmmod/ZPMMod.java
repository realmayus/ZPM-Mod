package mayus.zpmmod;


import mayus.zpmmod.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ZPMMod.MODID, name = ZPMMod.MODNAME, version = ZPMMod.MODVERSION, dependencies = "required-after:forge@[14.23.5.2768,)", useMetadata = true)
public class ZPMMod {

    public static final String MODID = "zpmmod";
    public static final String MODNAME = "ZPM Mod";
    public static final String MODVERSION= "0.0.1";

    @SidedProxy(clientSide = "mayus.zpmmod.proxy.ClientProxy", serverSide = "mayus.zpmmod.proxy.ServerProxy")
    public static CommonProxy proxy;

    public static CreativeTabs creativeTab = new CreativeTabs("zpmmod") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.blockControllerSmall);
        }
    };



    @Mod.Instance
    public static ZPMMod instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

}
