package mithion.arsmagica.api.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class ReconstructorRepairEvent extends Event{
	
	/**
	 * The item being repaired.
	 */
	public ItemStack item;
	
	/**
	 * Called when the arcane reconstructor ticks on repairing an item.
	 * @param item
	 */
	public ReconstructorRepairEvent(ItemStack item){
		this.item = item;
	}
}
