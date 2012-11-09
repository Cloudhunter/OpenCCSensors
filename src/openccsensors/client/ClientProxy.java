package openccsensors.client;

import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntity;
import openccsensors.client.sensorperipheral.GuiSensor;
import openccsensors.common.CommonProxy;
import openccsensors.common.sensorperipheral.TileEntitySensor;

public class ClientProxy extends CommonProxy 
{

	@Override
	public Object getGui( InventoryPlayer inventory, TileEntitySensor tileentity )
	{
		return new GuiSensor( inventory, tileentity);
	}
	
}
