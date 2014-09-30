package openccsensors.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import openccsensors.common.tileentity.TileEntitySensor;

public class ContainerSensor extends Container {

	private TileEntitySensor sensor;
	private InventoryPlayer inventory;
	private int inventorySize;
	
	public ContainerSensor(InventoryPlayer _inventory, TileEntity _tile) {
		
		sensor = (TileEntitySensor)_tile;
		inventory = _inventory;
		inventorySize = sensor.getSizeInventory();
		
		addSlotToContainer(new Slot(sensor, 0, 8 + 4 * 18, 35));

		for (int j = 0; j < 3; j++) {
			for (int i1 = 0; i1 < 9; i1++) {
				addSlotToContainer(new Slot(inventory, i1 + j * 9 + 9,
						8 + i1 * 18, 84 + j * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			addSlotToContainer(new Slot(inventory, k, 8 + k * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return sensor.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer pl, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < inventorySize) {
				if (!mergeItemStack(itemstack1, inventorySize, inventorySlots.size(), true))
					return null;
			} else if (!mergeItemStack(itemstack1, 0, inventorySize, false))
				return null;
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}

}
