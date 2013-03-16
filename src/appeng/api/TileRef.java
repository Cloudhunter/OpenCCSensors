package appeng.api;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraftforge.common.DimensionManager;
import appeng.api.exceptions.AppEngTileMissingException;
import appeng.api.me.tiles.IGridTileEntity;

public class TileRef<T> extends WorldCoord {
	
	int dimension;
	// private World w;
	
	public TileRef( TileEntity gte ) {
		super( ( (TileEntity) gte).xCoord, ( (TileEntity) gte).yCoord, ( (TileEntity) gte).zCoord );
		TileEntity te = ( TileEntity )gte;
		// w = te.worldObj;
		dimension = te.worldObj.provider.dimensionId;
	}
	
	public T getTile() throws AppEngTileMissingException
	{
		World w = DimensionManager.getWorld( dimension );
		TileEntity te = w.getBlockTileEntity( x, y, z );
		if ( te instanceof TileEntity )
			return (T)te;
		throw new AppEngTileMissingException( w, x,y,z);
	}
			
};
