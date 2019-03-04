package mayus.zpmmod.craftingItems;

import mayus.zpmmod.ZPMMod;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemZPMcasing extends Item {
    public static final ResourceLocation ZPMCASING = new ResourceLocation(ZPMMod.MODID, "zpm_casing");

    public ItemZPMcasing()
    {
        setCreativeTab(ZPMMod.creativeTab);
        setTranslationKey("zpm_casing");
        setRegistryName(ZPMCASING);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item.zpmmod.tooltip.craftingItem"));
    }
}