package openccsensors.client.renderer.tileentity;

import openccsensors.client.model.ModelSensor;
import openccsensors.common.tileentity.TileEntitySensor;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySensorRenderer extends TileEntitySpecialRenderer  {

	private ModelSensor modelSensor = new ModelSensor();
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y,
			double z, float partialTick) {
		
		TileEntitySensor sensor = (TileEntitySensor) tileEntity;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		this.bindTextureByName("/mods/OpenCCSensors/textures/models/sensor.png");
		GL11.glPushMatrix();
		int placing = sensor.getFacing() * 90;
		int rotation = (int) sensor.getRotation();
		this.modelSensor.renderSensor(rotation);
		GL11.glPopMatrix();
		GL11.glRotatef(placing, 0, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

}
