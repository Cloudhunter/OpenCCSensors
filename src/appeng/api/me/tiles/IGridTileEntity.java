package appeng.api.me.tiles;

import appeng.api.WorldCoord;
import appeng.api.me.util.IGridInterface;

/*
 * Basic ME Grid Interface, informs you of the Grid power status, and informs you of grid details, and such.
 */
public abstract interface IGridTileEntity
{
    /*
     *  Do this:
     *  	return new WorldCoord( TileEntity.xCoord, TileEntity.yCoord, TileEntity.zCoord );
     */
    public abstract WorldCoord getLocation();
    
    /*
     * Informs you of either true or false.
     * if the power feed has stopped, or been continued, only called when changes happens, or when grid updates happen.
     */
    public abstract void setPowerStatus(boolean hasPower);
    
    /*
     * yes if the device has a powered status.
     */
    public abstract boolean isPowered();
    
    /*
     * Return your last grid you got via setGrid.
     */
    public IGridInterface getGrid();
    
    /*
     * informs you of your new grid, YOU MUST return this via getGrid.
     * Store for later.
     */
    public void setGrid(IGridInterface gi);
}
