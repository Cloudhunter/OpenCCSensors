package appeng.api.me.tiles;

import appeng.api.me.util.IMEInventoryHandler;

public interface ICellContainer {
	
	IMEInventoryHandler getCellArray();

	int getPriority();
	
}
