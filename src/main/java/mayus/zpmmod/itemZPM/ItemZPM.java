package mayus.zpmmod.itemZPM;

import mayus.zpmmod.util.EnergyCapabilityProvider;
import mayus.zpmmod.util.EnergyStorageItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class ItemZPM extends Item {


    public ItemZPM()
    {
        maxStackSize = 1;
        setTranslationKey("zpm");
        this.addPropertyOverride(new ResourceLocation("energy"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            @Override
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
                return getEnergyStorage(stack).getEnergyStored() > 0 ? 0F : 1F;
            }
        });
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new EnergyCapabilityProvider(new EnergyStorageItem(stack, Integer.MAX_VALUE));
    }

    public IEnergyStorage getEnergyStorage(ItemStack stack) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            return stack.getCapability(CapabilityEnergy.ENERGY, null);
        }

        return null;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        IEnergyStorage energy = getEnergyStorage(stack);
        double stored = energy.getMaxEnergyStored() - energy.getEnergyStored();
        return (double) stored / energy.getMaxEnergyStored();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }


    /**
     * Adding two items: A ZPM with zero charge and a ZPM with full charge.
     */
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab)) {
            ItemStack is = new ItemStack(this);
            list.add(is);
            is = new ItemStack(this);
            is.getCapability(CapabilityEnergy.ENERGY, null).receiveEnergy(Integer.MAX_VALUE, false);
            list.add(is);
        }
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return new Color(255, 246, 0).getRGB();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item.zpmmod.tooltip.storedEnergy") + " " + getEnergyStorage(stack).getEnergyStored() + " RF");
        tooltip.add(I18n.format("item.zpmmod.tooltip.controllerInstructions"));
    }
}
