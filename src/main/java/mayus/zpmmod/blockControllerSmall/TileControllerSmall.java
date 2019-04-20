package mayus.zpmmod.blockControllerSmall;


import mayus.zpmmod.ZPMConfig;
import mayus.zpmmod.itemZPM.ItemZPM;
import mayus.zpmmod.util.IGuiTile;
import mayus.zpmmod.util.MyEnergyStorage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;

public class TileControllerSmall extends TileEntity implements IGuiTile, ITickable {

    /**
     * 0 = Ignore Redstone
     * 1 = Active on Redstone
     * 2 = Not active on Redstone
     */
    public int redstoneBehaviour = 0;
    public boolean isEnabled = true;

    @Override
    public void update() {
        if(isEnabled) {
            if(isActiveBasedOnSettings()) {
                if(doesContainZPM()) {
                    ItemZPM zpm = (ItemZPM) this.inputHandler.getStackInSlot(0).getItem();
                    sendEnergy(zpm.getEnergyStorage(this.inputHandler.getStackInSlot(0)));
                }
            }
        }
    }

    private boolean isActiveBasedOnSettings() {
        if(redstoneBehaviour == 0) {
            return true;
        } else if(redstoneBehaviour == 1) {
            if(this.world.isBlockPowered(this.getPos())) {
                return true;
            } else {
                return false;
            }
        } else {
            if(this.world.isBlockPowered(this.getPos())) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean doesContainZPM() {
        return !this.inputHandler.getStackInSlot(0).isEmpty();
    }

    public int getEnergyOfZPM() {
        if(this.inputHandler.getStackInSlot(0).getCapability(CapabilityEnergy.ENERGY, null) != null) {
            return this.inputHandler.getStackInSlot(0).getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored();
        } else {
            return -1;
        }

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
            return true;
        }

        return super.hasCapability(capability, facing);

    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
        }

        if(capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(myEnergyStorage);

        }

        return super.getCapability(capability, facing);
    }

    //int has to be 0 as we don't want to receive energy
    private MyEnergyStorage myEnergyStorage = new MyEnergyStorage(ZPMConfig.MAX_POWER_CONTROLLER_LARGE, 0);

    /**
     * Handler for the Input Slots
     */
    public ItemStackHandler inputHandler = new ItemStackHandler(1) {

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
        isEnabled = compound.getBoolean("enabled");
        redstoneBehaviour = compound.getInteger("redstone");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeRestorableToNBT(compound);
        return compound;
    }

    public void writeRestorableToNBT(NBTTagCompound compound) {
        compound.setBoolean("enabled", isEnabled);
        compound.setInteger("redstone", redstoneBehaviour);
    }
}
