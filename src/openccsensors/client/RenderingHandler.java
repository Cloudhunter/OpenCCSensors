package openccsensors.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import openccsensors.OpenCCSensors;
import openccsensors.common.tileentity.TileEntityGauge;
import openccsensors.common.tileentity.TileEntitySensor;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderingHandler implements ISimpleBlockRenderingHandler {

	@Override
	public int getRenderId() {
		return OpenCCSensors.renderId;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		if (block == OpenCCSensors.Blocks.sensorBlock) {
			TileEntityRenderer.instance.renderTileEntityAt(
					new TileEntitySensor(), 0.0D, 0.0D, 0.0D, 0.0F
			);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
		if (block == OpenCCSensors.Blocks.gaugeBlock) {
			TileEntityRenderer.instance.renderTileEntityAt(
					new TileEntityGauge(), 0.0D, 0.0D, 0.0D, 0.0F
			);
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
