package appeng.api.networking.crafting;

import appeng.api.storage.data.IAEItemStack;

public interface ICraftingWatcherHost
{

	/**
	 * provides the ICraftngiWatcher for this host, for the current network, is called when the hot changes networks.
	 * You do not need to clear your old watcher, its already been removed by the time this gets called.
	 * 
	 * @param newWatcher
	 */
	void updateWatcher(ICraftingWatcher newWatcher);

	/**
	 * Called when a crafting status changes.
	 * 
	 * @param craftingGrid
	 * @param what
	 */
	void onRequestChange(ICraftingGrid craftingGrid, IAEItemStack what);

}
