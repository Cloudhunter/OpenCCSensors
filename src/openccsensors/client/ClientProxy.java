package openccsensors.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import openccsensors.client.sensorperipheral.BlockSensorRenderingHandler;
import openccsensors.client.sensorperipheral.GuiSensor;
import openccsensors.client.sensorperipheral.TileEntitySensorRenderer;
import openccsensors.common.CommonProxy;
import openccsensors.common.sensorperipheral.TileEntitySensor;

public class ClientProxy extends CommonProxy 
{
	@Override
	public void registerRenderInformation()
	{
		MinecraftForgeClient.preloadTexture("/openccsensors/resources/images/terrain.png");
		RenderingRegistry.registerBlockHandler(new BlockSensorRenderingHandler());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySensor.class, new TileEntitySensorRenderer());
	}
	
	@Override
	public Object getGui( InventoryPlayer inventory, TileEntitySensor tileentity )
	{
		return new GuiSensor( inventory, tileentity);
	}	
}
