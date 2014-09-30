package openccsensors.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelGauge extends ModelBase {
	
	public ModelRenderer box = (new ModelRenderer(this, 0, 0)).setTextureSize(
			64, 32);
	
	public ModelGauge() {
		this.box.addBox(-7.0F, -7.0F, -8.0F, 14, 14, 1, 0.0F);
	}
	
	public void render() {
		this.box.render(0.0625F);
	}
	
}
