package openccsensors.client.sensorperipheral;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import openccsensors.OpenCCSensors;
import openccsensors.client.sensorperipheral.ModelSensor;
import openccsensors.common.core.ISensorCard;
import openccsensors.common.sensorperipheral.TileEntitySensor;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModelRenderer;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.minecraftforge.client.ForgeHooksClient;


public class TileEntitySensorRenderer extends TileEntitySpecialRenderer {

	/** The ModelSign instance used by the TileEntitySignRenderer */
    private ModelSensor modelSensor = new ModelSensor();
	
	public void render(TileEntitySensor tile, double x, double y, double z, float partialTick) {
		boolean renderIcon = false;
		String iconTexture="";
		int iconIndex=0;
		ItemStack itemStack = tile.getStackInSlot(0);
		if ((itemStack != null) && (itemStack.getItem() instanceof ISensorCard)) {
			renderIcon = true;
			iconIndex = itemStack.getIconIndex();
			iconTexture = tile.getStackInSlot(0).getItem().getTextureFile();
		}
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
		this.bindTextureByName("/openccsensors/resources/images/sensorblock.png");
        GL11.glPushMatrix();
        this.modelSensor.renderSensor();
        GL11.glPopMatrix();
        if (renderIcon) {
        	this.bindTextureByName(iconTexture);
        	GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
        	GL11.glScalef(0.5F, 0.5F, 1.05F);
        	GL11.glDepthMask(false);
        	this.modelSensor.renderIcon(iconIndex);
        	GL11.glDepthMask(true);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTick) {
		render((TileEntitySensor) tileentity, x, y, z, partialTick);
	}
}
