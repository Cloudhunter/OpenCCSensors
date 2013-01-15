package openccsensors.client.sensorperipheral;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import openccsensors.common.blocks.tileentity.TileEntitySensor;
import openccsensors.common.items.ItemSensorCard;

import org.lwjgl.opengl.GL11;

public class TileEntitySensorRenderer extends TileEntitySpecialRenderer {

	/** The ModelSign instance used by the TileEntitySignRenderer */
	private ModelSensor modelSensor = new ModelSensor();

	public void render(TileEntitySensor tile, double x, double y, double z,
			float partialTick) {
		boolean renderIcon = false;
		String iconTexture = "";
		int iconIndex = 0;
		ItemStack itemStack = tile.getStackInSlot(0);
		if ((itemStack != null)
				&& (itemStack.getItem() instanceof ItemSensorCard)) {
			renderIcon = true;
			iconIndex = itemStack.getIconIndex();
			iconTexture = tile.getStackInSlot(0).getItem().getTextureFile();
		}

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		this.bindTextureByName("/openccsensors/resources/images/sensorblock.png");
		GL11.glPushMatrix();

		int placing = tile.getFacing()*90;
		int rotation = (int)tile.getRotation();
		this.modelSensor.renderSensor(rotation);

		GL11.glPopMatrix();
		GL11.glRotatef(placing, 0, 1, 0);
		if (renderIcon) {
			this.bindTextureByName(iconTexture);
			GL11.glScalef(0.3F, 1.05F, 0.3F);
			GL11.glDepthMask(false);
			this.modelSensor.renderIcon(iconIndex);
			GL11.glDepthMask(true);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y,
			double z, float partialTick) {
		render((TileEntitySensor) tileentity, x, y, z, partialTick);
	}
}
