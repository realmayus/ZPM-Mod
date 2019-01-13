package mayus.zpmmod.BlockControllerSmall;


import mayus.zpmmod.ItemZPM.ItemZPM;
import mayus.zpmmod.ModConfig;
import mayus.zpmmod.util.EnergyStorageItem;
import mayus.zpmmod.util.IGuiTile;
import mayus.zpmmod.util.MyEnergyStorage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;

public class TileControllerSmall extends TileEntity implements IGuiTile, ITickable {

    private int clientEnergy = -1;
    public static int SIZE = 1;
    @Override
    public void update() {
        if(this.world.isBlockPowered(this.getPos())) {
            if(!this.inputHandler.getStackInSlot(0).isEmpty()) {
                ItemZPM item = (ItemZPM) this.inputHandler.getStackInSlot(0).getItem();
                sendEnergy(item.getEnergyStorage(this.inputHandler.getStackInSlot(0)));
            }
        }
    }

    @Override
    public Container createContainer(EntityPlayer player) {
        return new ContainerControllerSmall(player.inventory, this);
    }

    @Override
    public GuiContainer createGui(EntityPlayer player) {
        return new GuiControllerSmall(this, new ContainerControllerSmall(player.inventory, this));
    }

    /**
     * If we are too far away from this tile entity you cannot use it
     */
    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }

        if(capability == CapabilityEnergy.ENERGY) {
            if(!this.inputHandler.getStackInSlot(0).isEmpty()) {
                return this.inputHandler.getStackInSlot(0).hasCapability(capability, facing);
            }

        }


        return super.hasCapability(capability, facing);
    }


    /**
     * Sets the hopper/pipe capability (allows access to the inventory slots by automation pipes/ hoppers
     */

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
        }

        if(capability == CapabilityEnergy.ENERGY) {
            if(!this.inputHandler.getStackInSlot(0).isEmpty()) {
                return this.inputHandler.getStackInSlot(0).getCapability(capability, facing);
            }

        }

        return super.getCapability(capability, facing);
    }


    /**
     * Handler for the Input Slots
     */
    private ItemStackHandler inputHandler = new ItemStackHandler(1) {

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() instanceof ItemZPM;
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileControllerSmall.this.markDirty();
        }
    };




    /**
     * Handler for the Output Slots
     */
    private ItemStackHandler outputHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            TileControllerSmall.this.markDirty();
        }
    };

    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readRestorableFromNBT(compound);
    }

    public void readRestorableFromNBT(NBTTagCompound compound) {
        if(compound.hasKey("itemsIn")) {
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
        if(compound.hasKey("itemsOut")) {
            outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeRestorableToNBT(compound);
        return compound;
    }

    public void writeRestorableToNBT(NBTTagCompound compound) {
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
    }


    private void sendEnergy(IEnergyStorage energyStorage) {
        if (energyStorage.getEnergyStored() > 0) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                TileEntity tileEntity = world.getTileEntity(pos.offset(facing));
                if (tileEntity != null && tileEntity.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
                    IEnergyStorage handler = tileEntity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
                    if (handler != null && handler.canReceive()) {
                        int accepted = handler.receiveEnergy(energyStorage.getEnergyStored(), false);
                        energyStorage.extractEnergy(accepted, false);
                        if (energyStorage.getEnergyStored() <= 0) {
                            break;
                        }
                    }
                }
            }
            markDirty();
        }
    }

}
