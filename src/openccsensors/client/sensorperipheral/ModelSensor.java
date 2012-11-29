package openccsensors.client.sensorperipheral;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSensor extends ModelBase
{
    /** The board on a sign that has the writing on it. */
    public ModelRenderer sensorBase = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
    public ModelRenderer sensorIcon;

    public ModelSensor()
    {
        this.sensorBase.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
    }

    /**
     * Renders the sign model through TileEntitySignRenderer
     */
    public void renderSensor()
    {
        this.sensorBase.render(0.0625F);
    }
    
    public void renderIcon(int iconIndex)
    {
    	this.sensorIcon = new ModelRenderer(this, 16*(iconIndex%16), 16*((int)Math.floor(iconIndex/16))).setTextureSize(256, 256);
        this.sensorIcon.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 0, 0.0F);
    	this.sensorIcon.render(0.0625F);
    }
}
