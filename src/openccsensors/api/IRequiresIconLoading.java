package openccsensors.api;

import net.minecraft.client.renderer.texture.IconRegister;

public interface IRequiresIconLoading {
	public void loadIcon(IconRegister iconRegistry);
}
