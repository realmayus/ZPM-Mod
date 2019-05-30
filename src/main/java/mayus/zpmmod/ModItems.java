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


@GameRegistry.ObjectHolder(ZPMMod.MODID)
public class ModItems {
    public static ItemZPM ZPM = null;
    public static ItemDiaHardenedIron DIAMOND_HARDENED_IRON = null;
    public static ItemDimObsidian DIMENSIONAL_OBSIDIAN = null;
    public static ItemElectrode ELECTRODE = null;
    public static ItemEnderstar ENDERSTAR = null;
    public static ItemHardenedDimStar HARDENED_DIMENSIONAL_STAR = null;
    public static ItemZPMcasing ZPM_CASING = null;
    public static ItemZPMcore ZPM_CORE = null;
    public static ItemZPMholder ZPM_HOLDER = null;

}
