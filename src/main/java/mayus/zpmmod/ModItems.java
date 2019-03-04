package mayus.zpmmod;

import mayus.zpmmod.blockControllerLarge.BlockControllerLarge;
import mayus.zpmmod.blockControllerSmall.BlockControllerSmall;
import mayus.zpmmod.craftingItems.*;
import mayus.zpmmod.itemZPM.ItemZPM;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    @GameRegistry.ObjectHolder(ZPMMod.MODID + ":zpm")
    public static ItemZPM itemZPM;

    @GameRegistry.ObjectHolder(ZPMMod.MODID + ":diamond_hardened_iron")
    public static ItemDiaHardenedIron itemDiaHardenedIron;

    @GameRegistry.ObjectHolder(ZPMMod.MODID + ":dimensional_obsidian")
    public static ItemDimObsidian itemDimObsidian;

    @GameRegistry.ObjectHolder(ZPMMod.MODID + ":electrode")
    public static ItemElectrode itemElectrode;

    @GameRegistry.ObjectHolder(ZPMMod.MODID + ":enderstar")
    public static ItemEnderstar itemEnderstar;

    @GameRegistry.ObjectHolder(ZPMMod.MODID + ":hardened_dimensional_star")
    public static ItemHardenedDimStar itemHardenedDimStar;

    @GameRegistry.ObjectHolder(ZPMMod.MODID + ":zpm_casing")
    public static ItemZPMcasing itemZPMcasing;

    @GameRegistry.ObjectHolder(ZPMMod.MODID + ":zpm_core")
    public static ItemZPMcore itemZPMcore;

    @GameRegistry.ObjectHolder(ZPMMod.MODID + ":zpm_holder")
    public static ItemZPMholder itemZPMholder;


    @SideOnly(Side.CLIENT)
    public static void initModels() {

    }

    public static void register(IForgeRegistry<Item> registry) {
        registry.register(new ItemBlock(ModBlocks.blockControllerSmall).setRegistryName(BlockControllerSmall.CONTROLLER_SMALL));
        registry.register(new ItemBlock(ModBlocks.blockControllerLarge).setRegistryName(BlockControllerLarge.CONTROLLER_LARGE));
        registry.register(new ItemZPM());
        registry.register(new ItemDiaHardenedIron());
        registry.register(new ItemDimObsidian());
        registry.register(new ItemElectrode());
        registry.register(new ItemEnderstar());
        registry.register(new ItemHardenedDimStar());
        registry.register(new ItemZPMcasing());
        registry.register(new ItemZPMcore());
        registry.register(new ItemZPMholder());

    }

    @SideOnly(Side.CLIENT)
    public static void registerRender() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemZPM, 0, new ModelResourceLocation(itemZPM.getRegistryName(), "inventory"));

    }

}
