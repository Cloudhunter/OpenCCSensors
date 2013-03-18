package universalelectricity.components.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import universalelectricity.components.common.tileentity.TileEntityBatteryBox;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.prefab.SlotSpecific;

public class ContainerBatteryBox extends Container
{
	private TileEntityBatteryBox tileEntity;

	public ContainerBatteryBox(InventoryPlayer par1InventoryPlayer, TileEntityBatteryBox batteryBox)
	{
		this.tileEntity = batteryBox;
		// Top slot for battery output
		this.addSlotToContainer(new SlotSpecific(batteryBox, 0, 33, 24, IItemElectric.class));
		// Bottom slot for batter input
		this.addSlotToContainer(new SlotSpecific(batteryBox, 1, 33, 48, IItemElectric.class));
		int var3;

		for (var3 = 0; var3 < 3; ++var3)
		{
			for (int var4 = 0; var4 < 9; ++var4)
			{
				this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}

		for (var3 = 0; var3 < 9; ++var3)
		{
			this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
		}

		tileEntity.openChest();
	}

	public void onCraftGuiClosed(EntityPlayer entityplayer)
	{
		super.onCraftGuiClosed(entityplayer);
		tileEntity.closeChest();
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.tileEntity.isUseableByPlayer(par1EntityPlayer);
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift clicking.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotID)
	{
		ItemStack returnStack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemStack = slot.getStack();
			returnStack = itemStack.copy();

			if (slotID != 0 && slotID != 1)
			{
				if (this.getSlot(0).isItemValid(itemStack))
				{
					if (((IItemElectric) itemStack.getItem()).getProvideRequest(itemStack).getWatts() > 0)
					{
						if (!this.mergeItemStack(itemStack, 1, 2, false))
						{
							return null;
						}
					}
					else
					{
						if (!this.mergeItemStack(itemStack, 0, 1, false))
						{
							return null;
						}
					}
				}

				else if (slotID >= 30 && slotID < 38 && !this.mergeItemStack(itemStack, 3, 30, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemStack, 3, 38, false))
			{
				return null;
			}

			if (itemStack.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemStack.stackSize == returnStack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemStack);
		}

		return returnStack;
	}
}
