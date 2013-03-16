package appeng.api.me.util;

import java.util.List;

import appeng.api.TileRef;
import appeng.api.exceptions.AppEngTileMissingException;
import appeng.api.me.tiles.IGridMachine;
import appeng.api.me.tiles.IGridTileEntity;
import appeng.api.me.tiles.IPushable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/*
 * Lets you access network related features. You will mostly care about "getCellArray()" which returns the IMEInventory for the entire network...
 */
public interface IGridInterface
{
	public ICraftRequest craftingRequest( ItemStack what, boolean showInMonitor ) throws AppEngTileMissingException;
	
	public ICraftRequest craftingRequest( ItemStack what ) throws AppEngTileMissingException;
	
	public void craftGui( EntityPlayerMP pmp, IGridTileEntity gte, ItemStack s ) throws AppEngTileMissingException;
	
    // updates the ram to match interface requests.
    public void requestUpdate( IGridTileEntity te );
    
    List< TileRef<IGridMachine> > getMachines();

    /*
     * Labeled version for debugging...
     */
	boolean useMEEnergy(float use, String for_what);
	
    /*
     * Signals the use of a non constant power drain.
     */
    boolean useMEEnergy( float amt );
    
    // this is used for standard items, anything else just use useMEEnergy.
	int usePowerForAddition(int items);
    
    // returns a single IMEInventory that represents the entire networks.
    public IMEInventoryHandler getCellArray();
    
    // returns a single IMEInventory that represents the entire network, and all crafting available.
    public IMEInventoryHandler getFullCellArray();
    
    // informs the network that the networks inventory changed.
	public void onInventoryChange();

	// add/remove which users should be notified of terminal updates.
	void addViewingPlayer(EntityPlayer p);
	void rmvViewingPlayer(EntityPlayer p);
	
	// add/remove which users should be notified of crafting queue updates.
	void addCraftingPlayer(EntityPlayer p);
	void rmvCraftingPlayer(EntityPlayer p);
	
	// add stuff to the inventory or the grid, and inform it about stuff...
	ItemStack signalInput(IMEInventory inv, ItemStack is);
	
	public TileEntity getController();
	
	ICraftRequest waitingRequest(ItemStack what);
	
	ICraftRequest pushRequest(ItemStack willAdd, IPushable out, boolean allowCrafting );
	
	public boolean isValid();
	
	void resetWaitingQueue();

	void OnCraftingChange();

	IMEInventoryHandler getCraftableArray();
}
