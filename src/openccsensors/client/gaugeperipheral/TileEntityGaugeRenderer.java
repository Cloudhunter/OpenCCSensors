package openccsensors.client.gaugeperipheral;

import org.lwjgl.opengl.GL11;

import openccsensors.client.sensorperipheral.ModelSensor;
import openccsensors.common.gaugeperipheral.TileEntityGauge;
import openccsensors.common.sensorperipheral.TileEntitySensor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGaugeRenderer extends TileEntitySpecialRenderer {

	private ModelGauge modelGauge = new ModelGauge();
	public void render(TileEntityGauge tile, double x, double y, double z,
			float partialTick) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		this.bindTextureByName("/openccsensors/resources/images/gauge.png");
		GL11.glPushMatrix();
		int facing = tile.getFacing()*90;
		this.modelGauge.renderGauge(facing);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y,
			double z, float partialTick) {
		render((TileEntityGauge) tileentity, x, y, z, partialTick);
	}


}
