package mayus.zpmmod;

import mayus.zpmmod.lightblock.BlockLightBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {



    @SideOnly(Side.CLIENT)
    public static void initModels() {

    }

    public static void register(IForgeRegistry<Item> registry) {
        registry.register(new ItemBlock(ModBlocks.blockLightBlock).setRegistryName(BlockLightBlock.LIGHT_BLOCK));

    }

}
