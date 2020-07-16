package realmayus.zpmmod;

import realmayus.zpmmod.blockControllerLarge.BlockControllerLarge;
import realmayus.zpmmod.blockControllerSmall.BlockControllerSmall;

import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Forge will automatically look up and bind blocks to the fields in this class
 * based on their registry name.
 */
@GameRegistry.ObjectHolder(ZPMMod.MODID)
public class ModBlocks {
    public static final BlockControllerSmall CONTROLLER_SMALL = null;
    public static final BlockControllerLarge CONTROLLER_LARGE = null;
}
