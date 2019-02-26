package mayus.zpmmod.blockControllerLarge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerControllerLarge extends Container {

    public TileControllerLarge te;


    public ContainerControllerLarge(IInventory playerInventory, TileControllerLarge te) {
        this.te = te;
        addPlayerSlots(playerInventory);
        addCustomSlots();
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.canInteractWith(playerIn);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = col * 18 + 8;
                int y = row * 18 + 84;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 8 + row * 18;
            int y = 58 + 84;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addCustomSlots() {
        IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        int slotIndex = 0;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, 80, 15));
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, 49, 51));
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, 112, 51));

    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        final SPacketUpdateTileEntity updatePacket = te != null ? te.getUpdatePacket() : null;
        if (updatePacket != null) {
            for (IContainerListener containerListener : listeners) {
                if (containerListener instanceof EntityPlayerMP) {
                    ((EntityPlayerMP) containerListener).connection.sendPacket(updatePacket);
                }
            }
        }

       // if (te.getEnergy() != te.getClientEnergy()) {
        //    te.setClientEnergy(te.getEnergy());
            /*
            for (IContainerListener listener : listeners) {
                if (listener instanceof EntityPlayerMP) {
                    EntityPlayerMP player = (EntityPlayerMP) listener;
                    Messages.INSTANCE.sendTo(new PacketSyncMachineState(te.getEnergy(), 0), player);
                }*/
            }
       // }
    }

