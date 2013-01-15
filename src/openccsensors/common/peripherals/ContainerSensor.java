package openccsensors.common.peripherals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import openccsensors.common.blocks.tileentity.TileEntitySensor;

public class ContainerSensor extends Container 
{
	
	private TileEntitySensor sensor;
	private InventoryPlayer inventory;
	
	
	public ContainerSensor( InventoryPlayer _inventory, TileEntitySensor _sensor )
	{
		sensor = _sensor;
		inventory = _inventory;
		
        addSlotToContainer(new Slot(sensor, 0, 8 + 4 * 18, 35));
		
        for(int j = 0; j < 3; j++)
        {
            for(int i1 = 0; i1 < 9; i1++)
            {
            	addSlotToContainer(new Slot(inventory, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));
            }
        }

        for(int k = 0; k < 9; k++)
        {
        	addSlotToContainer(new Slot(inventory, k, 8 + k * 18, 142));
        }
	}

	@Override
	public boolean canInteractWith( EntityPlayer player )
	{
		return sensor.isUseableByPlayer( player );
	}
	
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 == 0)
            {
                if (!this.mergeItemStack(var5, 1, 37, true))
                {
                    return null;
                }
            }
            else
            {
                if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(var5))
                {
                    return null;
                }

                if (var5.stackSize == 1)
                {
                    ((Slot)this.inventorySlots.get(0)).putStack(var5.copy());
                    var5.stackSize = 0;
                }
                else if (var5.stackSize >= 1)
                {
                    ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(var5.itemID, 1, var5.getItemDamage()));
                    --var5.stackSize;
                }
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }

            if (var5.stackSize == var3.stackSize)
            {
                return null;
            }

            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }

        return var3;
    }


}
