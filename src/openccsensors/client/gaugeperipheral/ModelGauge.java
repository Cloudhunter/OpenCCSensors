package openccsensors.client.gaugeperipheral;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGauge extends ModelBase
{
    public ModelRenderer box = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 32);
	public ModelGauge()
	{
        this.box.addBox(-6.0F, -6.0F, -8.0F, 12, 12, 1, 0.0F);
        
	}
	
	public void renderGauge(float degrees)
    {
        this.box.rotateAngleY = (degrees*(float)Math.PI/180F)%360;
        this.box.render(0.0625F);
    }
}
