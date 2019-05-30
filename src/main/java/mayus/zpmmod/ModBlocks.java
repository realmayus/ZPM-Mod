package mayus.zpmmod;

import mayus.zpmmod.blockControllerLarge.BlockControllerLarge;
import mayus.zpmmod.blockControllerLarge.TileControllerLarge;
import mayus.zpmmod.blockControllerSmall.BlockControllerSmall;

import mayus.zpmmod.blockControllerSmall.TileControllerSmall;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Forge will automatically look up and bind blocks to the fields in this class
 * based on their registry name.
 */
@GameRegistry.ObjectHolder(ZPMMod.MODID)
public class ModBlocks {
    public static final BlockControllerSmall CONTROLLER_SMALL = null;
    public static final BlockControllerLarge CONTROLLER_LARGE = null;
}
