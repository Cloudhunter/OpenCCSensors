package forestry.api.core;

import net.minecraft.world.World;

public interface ISaveEventHandler {

	void onWorldLoad(World world);

	void onWorldUnload(World world);

	void onWorldSave(World world);

}
