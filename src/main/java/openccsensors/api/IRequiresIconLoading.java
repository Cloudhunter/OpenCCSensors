package openccsensors.api;

import net.minecraft.client.renderer.texture.IIconRegister;

public interface IRequiresIconLoading {
	public void loadIcon(IIconRegister iconRegistry);
}
