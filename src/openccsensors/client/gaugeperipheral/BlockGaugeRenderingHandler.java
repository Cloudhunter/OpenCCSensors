package openccsensors.client.gaugeperipheral;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import openccsensors.OpenCCSensors;
import openccsensors.common.gaugeperipheral.TileEntityGauge;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockGaugeRenderingHandler implements ISimpleBlockRenderingHandler {

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return 2700;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {

		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		if (block == OpenCCSensors.Blocks.gaugeBlock)
        {
        	TileEntityRenderer.instance.renderTileEntityAt(new TileEntityGauge(), 0.0D, 0.0D, 0.0D, 0.0F);
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
