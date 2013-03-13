package appeng.api.me.tiles;

import net.minecraft.item.ItemStack;
import appeng.api.me.util.IMEInventory;
import appeng.api.me.util.IMEInventoryHandler;

/*
 * Both useless and incredibly useful, maybe...
 */
public interface ICellProvider
{
	public int usePowerForAddition( int items );
	
    public IMEInventoryHandler provideCell();

	public ItemStack signalInput(IMEInventory ime, ItemStack toAdd);
}
