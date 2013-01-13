package openccsensors.client.gaugeperipheral;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import openccsensors.common.core.OCSLog;
import openccsensors.common.gaugeperipheral.TileEntityGauge;

import org.lwjgl.opengl.GL11;

public class TileEntityGaugeRenderer extends TileEntitySpecialRenderer {
	private ModelGauge modelGauge = new ModelGauge();
	public void render(TileEntityGauge tile, double x, double y, double z,
			float partialTick) {

        int var16 = tile.getBlockMetadata();
        
        GL11.glPushMatrix();
        float var10 = 0.6666667F;
        float var12;
        var12 = 0.0F;

        if (var16 == 2)
        {
            var12 = 180.0F;
        }

        if (var16 == 4)
        {
            var12 = 90.0F;
        }

        if (var16 == 5)
        {
            var12 = -90.0F;
        }

        GL11.glTranslatef((float)x + 0.5F, (float)y + 0.75F * var10, (float)z + 0.5F);
        GL11.glRotatef(-var12, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);

        this.bindTextureByName("/item/sign.png");
        GL11.glPushMatrix();
        GL11.glScalef(var10, -var10, -var10);
        this.modelGauge.renderGauge();
        GL11.glPopMatrix();
        FontRenderer fontRenderer = this.getFontRenderer();
        var12 = 0.016666668F * var10;
        GL11.glTranslatef(0.0F, 0.5F * var10, 0.07F * var10);
        GL11.glScalef(var12, -var12, var12);
        GL11.glNormal3f(0.0F, 0.0F, -1.0F * var12);
        GL11.glDepthMask(false);
        byte var13 = 0;

        if (fontRenderer != null){
        	String stringPercentage = String.format("%.2g", tile.getPercentage());
        	OCSLog.info("Double = " + tile.getPercentage() + "");
        	OCSLog.info("String = " + stringPercentage);
        	fontRenderer.drawString(stringPercentage, -fontRenderer.getStringWidth(stringPercentage) / 2, 5, 0);
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
