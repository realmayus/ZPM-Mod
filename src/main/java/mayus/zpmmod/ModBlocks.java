package mayus.zpmmod;

import mayus.zpmmod.BlockControllerSmall.BlockControllerSmall;
import mayus.zpmmod.lightblock.BlockLightBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {


    @GameRegistry.ObjectHolder("zpmmod:controller_small")
    public static BlockControllerSmall blockControllerSmall;


    /**
     * Initializes the Block models
     */
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockControllerSmall.initModel();
    }


    /**
     * Actually register the Blocks in the Registry
     */
    public static void register(IForgeRegistry<Block> registry) {
        registry.register(new BlockControllerSmall());
    }


}
