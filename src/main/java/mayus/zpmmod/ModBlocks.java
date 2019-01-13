package mayus.zpmmod;

import mayus.zpmmod.BlockControllerLarge.BlockControllerLarge;
import mayus.zpmmod.BlockControllerLarge.TileControllerLarge;
import mayus.zpmmod.BlockControllerSmall.BlockControllerSmall;

import mayus.zpmmod.BlockControllerSmall.TileControllerSmall;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {


    @GameRegistry.ObjectHolder("zpmmod:controller_small")
    public static BlockControllerSmall blockControllerSmall;

    @GameRegistry.ObjectHolder("zpmmod:controller_large")
    public static BlockControllerLarge blockControllerLarge;


    /**
     * Initializes the Block models
     */
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockControllerSmall.initModel();
        blockControllerLarge.initModel();
    }


    /**
     * Actually register the Blocks in the Registry
     */
    public static void register(IForgeRegistry<Block> registry) {
        registry.register(new BlockControllerSmall());
        registry.register(new BlockControllerLarge());
        GameRegistry.registerTileEntity(TileControllerLarge.class, ZPMMod.MODID + "_controller_large");
        GameRegistry.registerTileEntity(TileControllerSmall.class, ZPMMod.MODID + "_controller_small");
    }


}
