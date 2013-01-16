package openccsensors.client.gaugeperipheral;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import openccsensors.common.blocks.tileentity.TileEntityGauge;

import org.lwjgl.opengl.GL11;

public class TileEntityGaugeRenderer extends TileEntitySpecialRenderer {
	private ModelGauge modelGauge = new ModelGauge();

	public void render(TileEntityGauge tile, double x, double y, double z,
			float partialTick) {

		int var16 = tile.getFacing();

		GL11.glPushMatrix();
		float var12;
		var12 = 0.0F;

		if (var16 == 2) {
			var12 = 180.0F;
		}

		if (var16 == 4) {
			var12 = 90.0F;
		}

		if (var16 == 5) {
			var12 = -90.0F;
		}

		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		GL11.glRotatef(-var12, 0.0F, 1.0F, 0.0F);
		this.bindTextureByName("/openccsensors/resources/images/gauge.png");
		GL11.glPushMatrix();
		this.modelGauge.renderGauge();
		GL11.glPopMatrix();

		FontRenderer fontRenderer = this.getFontRenderer();
		GL11.glTranslatef(0.0F, 0.1F, -0.43F);
		GL11.glScalef(0.02F, 0.02F, 0.02F);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glDepthMask(false);

		if (fontRenderer != null) {
			String stringPercentage = tile.getPercentage() + "%";
			fontRenderer.drawString(stringPercentage,
					-fontRenderer.getStringWidth(stringPercentage) / 2, 0,
					16777215);
		}

		GL11.glDepthMask(true);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y,
			double z, float partialTick) {
		render((TileEntityGauge) tileentity, x, y, z, partialTick);
	}

}
