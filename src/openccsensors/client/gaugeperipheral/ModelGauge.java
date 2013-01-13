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
        this.box.addBox(-12.0F, -14.0F, -1.0F, 24, 12, 2, 0.0F);
	}
	
	public void renderGauge()
    {
        this.box.render(0.0625F);
    }
}
