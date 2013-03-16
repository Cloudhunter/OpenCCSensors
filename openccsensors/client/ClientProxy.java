package openccsensors.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import openccsensors.OpenCCSensors;
import openccsensors.client.renderer.tileentity.TileEntitySensorRenderer;
import openccsensors.common.CommonProxy;
import openccsensors.common.tileentity.TileEntitySensor;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderInformation() {
		
		OpenCCSensors.renderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderingHandler());
		
		ClientRegistry.bindTileEntitySpecialRenderer(
				TileEntitySensor.class,
				new TileEntitySensorRenderer()
		);
	}
}
