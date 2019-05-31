package mayus.zpmmod.blockControllerLarge;

import mayus.zpmmod.itemZPM.ItemZPM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerControllerLarge extends Container {

    public TileControllerLarge te;

    /**
     * 0 = Ignore Redstone
     * 1 = Active on Redstone
     * 2 = Not active on Redstone
     */
    public int redstoneBehaviour = 0;
    public boolean isEnabled = true;

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


    /**
     * A bit of a hack, but with Container#updateProgressBar you don't have to send a custom packet
     */
    @Override
    public void updateProgressBar(int id, int data) {
        if(id == 5) {
            isEnabled = data != 0;
        }
        if(id == 6) {
            redstoneBehaviour = data;
        }

    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if(redstoneBehaviour != te.redstoneBehaviour || isEnabled != te.isEnabled) {
            for (IContainerListener containerListener : listeners) {
                containerListener.sendWindowProperty(this, 5, te.isEnabled ? 1 : 0);
                containerListener.sendWindowProperty(this, 6, te.redstoneBehaviour);
                redstoneBehaviour = te.redstoneBehaviour;
                isEnabled = te.isEnabled;
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack() && slot.getStack().getItem() instanceof ItemZPM) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index > 35 && index < 39) {
                if (!this.mergeItemStack(itemstack1, 0, 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 36, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}

