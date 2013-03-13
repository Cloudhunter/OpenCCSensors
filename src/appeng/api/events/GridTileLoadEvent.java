package appeng.api.events;

import appeng.api.WorldCoord;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

public class GridTileLoadEvent extends WorldEvent {
	
	public WorldCoord coord;
	public GridTileLoadEvent(World world, WorldCoord wc ) {
		super(world);
		coord = wc;
	}
	
}
