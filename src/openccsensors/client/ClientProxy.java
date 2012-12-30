package openccsensors.client;

import java.io.File;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.InventoryPlayer;
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
	
	@Override
	public File getBase()
	{
		return FMLClientHandler.instance().getClient().getMinecraftDir();
	}
}
