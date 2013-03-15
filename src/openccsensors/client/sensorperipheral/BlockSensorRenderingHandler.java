package openccsensors.client.sensorperipheral;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import openccsensors.OpenCCSensors;
import openccsensors.common.blocks.tileentity.TileEntitySensor;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockSensorRenderingHandler implements
		ISimpleBlockRenderingHandler {

	@Override
	public int getRenderId() {
		return OpenCCSensors.RenderIds.sensorRenderId;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// rotate the model 90 degrees
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		// move it to the center
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		// if it's a sensor block, render it like the tile entity
		if (block == OpenCCSensors.Blocks.sensorBlock) {
			TileEntityRenderer.instance.renderTileEntityAt(
					new TileEntitySensor(), 0.0D, 0.0D, 0.0D, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}
}
