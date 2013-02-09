package forestry.api.core;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

public interface IPickupHandler {

	boolean onItemPickup(EntityPlayer entityPlayer, EntityItem item);

}
