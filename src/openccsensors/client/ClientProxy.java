package openccsensors.client;

import java.io.File;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import openccsensors.OpenCCSensors;
import openccsensors.client.renderer.tileentity.TileEntityGaugeRenderer;
import openccsensors.client.renderer.tileentity.TileEntitySensorRenderer;
import openccsensors.common.CommonProxy;
import openccsensors.common.tileentity.TileEntityGauge;
import openccsensors.common.tileentity.TileEntitySensor;

public class ClientProxy extends CommonProxy {

	@Override
	public File getBase() {
		FMLClientHandler.instance().getClient();
		return Minecraft.getMinecraftDir();
	}
	
	@Override
	public void registerRenderInformation() {
		
		OpenCCSensors.renderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderingHandler());
		
		ClientRegistry.bindTileEntitySpecialRenderer(
				TileEntitySensor.class,
				new TileEntitySensorRenderer()
		);
		ClientRegistry.bindTileEntitySpecialRenderer(
				TileEntityGauge.class,
				new TileEntityGaugeRenderer()
		);
	}
}
