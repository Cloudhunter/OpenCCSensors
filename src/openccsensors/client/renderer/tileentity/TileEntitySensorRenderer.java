package openccsensors.client.renderer.tileentity;

import openccsensors.OpenCCSensors;
import openccsensors.client.model.ModelSensor;
import openccsensors.common.item.ItemSensorCard;
import openccsensors.common.tileentity.TileEntitySensor;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraftforge.client.ForgeHooksClient;

public class TileEntitySensorRenderer extends TileEntitySpecialRenderer {

	private ModelSensor modelSensor = new ModelSensor();
	RenderItem renderItem;

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y,
			double z, float partialTick) {

		TileEntitySensor sensor = (TileEntitySensor) tileEntity;
		GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			this.bindTextureByName(String.format("%s/models/sensor.png", OpenCCSensors.TEXTURE_PATH));
			int rotation = (int) sensor.getRotation();
			GL11.glPushMatrix();
			this.modelSensor.renderSensor(rotation);
			ItemStack sensorCardStack = sensor.getSensorCardStack();
			int placing = sensor.getFacing() * 90;
			GL11.glPopMatrix();
			GL11.glRotatef(placing, 0, 1, 0);
			if (sensorCardStack != null && sensorCardStack.getItem() instanceof ItemSensorCard) {

				GL11.glScalef(0.02f, 0.02f, 0.02f);
				GL11.glRotatef(90.0F, 1, 0, 0);
				GL11.glTranslatef(-8.0f, 4.0f, 12.0f);
				renderItem = ((RenderItem) RenderManager.instance.getEntityClassRenderObject(EntityItem.class));
			    RenderEngine re = Minecraft.getMinecraft().renderEngine;
			    FontRenderer fr = getFontRenderer();
				renderItem.renderItemIntoGUI(fr, re, sensorCardStack, 0, 0);

			}
				
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		
	}

}
