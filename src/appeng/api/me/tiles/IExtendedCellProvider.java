package appeng.api.me.tiles;

import net.minecraft.item.ItemStack;
import appeng.api.me.util.IMEInventory;
import appeng.api.me.util.IMEInventoryHandler;

/*
 * Both useless and incredibly useful, maybe...
 */
public interface IExtendedCellProvider extends ICellProvider
{
	
    public IMEInventoryHandler provideCell( String Filter );
    
}
