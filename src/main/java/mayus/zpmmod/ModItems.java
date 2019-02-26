package mayus.zpmmod;

import mayus.zpmmod.blockControllerLarge.BlockControllerLarge;
import mayus.zpmmod.blockControllerSmall.BlockControllerSmall;
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

    @SideOnly(Side.CLIENT)
    public static void initModels() {

    }

    public static void register(IForgeRegistry<Item> registry) {
        registry.register(new ItemBlock(ModBlocks.blockControllerSmall).setRegistryName(BlockControllerSmall.CONTROLLER_SMALL));
        registry.register(new ItemBlock(ModBlocks.blockControllerLarge).setRegistryName(BlockControllerLarge.CONTROLLER_LARGE));
        registry.register(new ItemZPM());

    }

    @SideOnly(Side.CLIENT)
    public static void registerRender() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemZPM, 0, new ModelResourceLocation(itemZPM.getRegistryName(), "inventory"));

    }

}
