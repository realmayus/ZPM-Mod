package mayus.zpmmod.ItemZPM;

import mayus.zpmmod.ZPMMod;
import mayus.zpmmod.util.EnergyCapabilityProvider;
import mayus.zpmmod.util.EnergyStorageItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.awt.*;

public class ItemZPM extends Item {

    public static final ResourceLocation ZPM = new ResourceLocation(ZPMMod.MODID, "zpm");

    public ItemZPM()
    {
        maxStackSize = 1;
        setCreativeTab(ZPMMod.creativeTab);
        setTranslationKey("zpm");
        setRegistryName(ZPM);
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {

        if(worldIn.isRemote) {
            playerIn.sendMessage(new TextComponentString("CLICK!"));
            playerIn.getHeldItem(handIn).setItemDamage(playerIn.getHeldItem(handIn).getMaxDamage() - 1);
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
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
}
