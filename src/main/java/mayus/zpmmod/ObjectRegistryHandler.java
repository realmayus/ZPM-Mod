package mayus.zpmmod;

import mayus.zpmmod.blockControllerLarge.BlockControllerLarge;
import mayus.zpmmod.blockControllerLarge.TileControllerLarge;
import mayus.zpmmod.blockControllerSmall.BlockControllerSmall;
import mayus.zpmmod.blockControllerSmall.TileControllerSmall;
import mayus.zpmmod.craftingItems.*;
import mayus.zpmmod.itemZPM.ItemZPM;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;


@Mod.EventBusSubscriber
public class ObjectRegistryHandler {
    /**
     * Listen for the register event for creating custom items
     */
    @SubscribeEvent
    public static void addItems(RegistryEvent.Register<Item> event) {
        //Blocks
        event.getRegistry().register(new ItemBlock(ModBlocks.CONTROLLER_LARGE).setRegistryName(ZPMMod.MODID, "controller_large"));
        event.getRegistry().register(new ItemBlock(ModBlocks.CONTROLLER_SMALL).setRegistryName(ZPMMod.MODID, "controller_small"));

        //Items
        event.getRegistry().register(new ItemZPM().setRegistryName(ZPMMod.MODID, "zpm").setCreativeTab(ZPMMod.creativeTab));
        event.getRegistry().register(new ItemDiaHardenedIron().setRegistryName(ZPMMod.MODID, "diamond_hardened_iron").setCreativeTab(ZPMMod.creativeTab));
        event.getRegistry().register(new ItemDimObsidian().setRegistryName(ZPMMod.MODID, "dimensional_obsidian").setCreativeTab(ZPMMod.creativeTab));
        event.getRegistry().register(new ItemElectrode().setRegistryName(ZPMMod.MODID, "electrode").setCreativeTab(ZPMMod.creativeTab));
        event.getRegistry().register(new ItemEnderstar().setRegistryName(ZPMMod.MODID, "enderstar").setCreativeTab(ZPMMod.creativeTab));
        event.getRegistry().register(new ItemHardenedDimStar().setRegistryName(ZPMMod.MODID, "hardened_dimensional_star").setCreativeTab(ZPMMod.creativeTab));
        event.getRegistry().register(new ItemZPMcasing().setRegistryName(ZPMMod.MODID, "zpm_casing").setCreativeTab(ZPMMod.creativeTab));
        event.getRegistry().register(new ItemZPMcore().setRegistryName(ZPMMod.MODID, "zpm_core").setCreativeTab(ZPMMod.creativeTab));
        event.getRegistry().register(new ItemZPMholder().setRegistryName(ZPMMod.MODID, "zpm_holder").setCreativeTab(ZPMMod.creativeTab));
    }

    /**
     * Listen for the register event for creating custom blocks
     */
    @SubscribeEvent
    public static void addBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockControllerLarge().setRegistryName(ZPMMod.MODID, "controller_large"));
        event.getRegistry().register(new BlockControllerSmall().setRegistryName(ZPMMod.MODID, "controller_small"));

        GameRegistry.registerTileEntity(TileControllerLarge.class, new ResourceLocation("te_controller_large"));
        GameRegistry.registerTileEntity(TileControllerSmall.class, new ResourceLocation("te_controller_small"));
    }


    /**
     * Will be called by Forge automatically when it's time.
     * Stolen from Cadiboo https://gist.github.com/Cadiboo/3f5cdb785affc069af2fa5fdf2d70358
     */
    @SubscribeEvent
    public static void onRegisterModelsEvent(@Nonnull final ModelRegistryEvent event) {
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(item -> item.getRegistryName().getNamespace().equals(ZPMMod.MODID))
                .forEach(item -> ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "normal")));

        ZPMMod.getLOGGER().info("Registered models");
    }

}
