package openccsensors.client.sensorperipheral;

import org.lwjgl.opengl.GL11;

import openccsensors.OpenCCSensors;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockSensorRenderingHandler implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glPushMatrix();
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		// renderer.renderStandardBlock(OpenCCSensors.Blocks.sensorBlock, x, y, z);
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return OpenCCSensors.Config.sensorBlockRenderID;
	}

}
