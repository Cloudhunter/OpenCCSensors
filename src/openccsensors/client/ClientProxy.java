package openccsensors.client;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.client.MinecraftForgeClient;
import openccsensors.OpenCCSensors;
import openccsensors.client.gaugeperipheral.BlockGaugeRenderingHandler;
import openccsensors.client.gaugeperipheral.TileEntityGaugeRenderer;
import openccsensors.client.sensorperipheral.BlockSensorRenderingHandler;
import openccsensors.client.sensorperipheral.GuiSensor;
import openccsensors.client.sensorperipheral.TileEntitySensorRenderer;
import openccsensors.common.CommonProxy;
import openccsensors.common.blocks.tileentity.TileEntityGauge;
import openccsensors.common.blocks.tileentity.TileEntitySensor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public File getBase() {
		FMLClientHandler.instance().getClient();
		return Minecraft.getMinecraftDir();
	}

	@Override
	public Object getGui(InventoryPlayer inventory, TileEntitySensor tileentity) {
		return new GuiSensor(inventory, tileentity);
	}

	@Override
	public void registerRenderInformation() {

		OpenCCSensors.RenderIds.sensorRenderId = RenderingRegistry.getNextAvailableRenderId();
		OpenCCSensors.RenderIds.gaugeRenderId = RenderingRegistry.getNextAvailableRenderId();

		
		MinecraftForgeClient
				.preloadTexture("/openccsensors/resources/images/terrain.png");
		RenderingRegistry
				.registerBlockHandler(new BlockSensorRenderingHandler());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySensor.class,
				new TileEntitySensorRenderer());

		RenderingRegistry
				.registerBlockHandler(new BlockGaugeRenderingHandler());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGauge.class,
				new TileEntityGaugeRenderer());
		
	}
}
