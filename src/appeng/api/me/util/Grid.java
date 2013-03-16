package appeng.api.me.util;

import java.util.ArrayList;
import java.util.List;

import appeng.api.Util;
import appeng.api.me.tiles.IGridTileEntity;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/*
 * Grid related stuff...
 */
public class Grid
{
    public static IGridInterface getGridInterface(World w, int x, int y, int z)
    {
        IGridTileEntity te = getGridEntity(w, x, y, z);

        if (te != null)
        {
            return te.getGrid();
        }

        return null;
    }
    
    public static boolean isGridEntity(World w, int x, int y, int z)
    {
        return getGridEntity(w, x, y, z) != null;
    }
    
    public static IGridTileEntity getGridEntity(World w, int x, int y, int z)
    {
        TileEntity te = w.getBlockTileEntity(x, y, z);

        if (te instanceof IGridTileEntity)
        {
            return (IGridTileEntity)te;
        }

        return null;
    }

    public static boolean isOnGrid(World w, int x, int y, int z)
    {
        IGridTileEntity te = getGridEntity(w, x, y, z);

        if (te != null)
        {
            return te.getGrid() != null;
        }

        return false;
    }

	public static void TriggerGridUpdate(World w, int x, int y, int z)
	{
		Util.updateGridAt( w, x, y, z );
	}
}
