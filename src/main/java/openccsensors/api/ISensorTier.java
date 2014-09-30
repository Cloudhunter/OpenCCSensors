package openccsensors.api;

import net.minecraft.util.IIcon;

public interface ISensorTier {
	
	public EnumItemRarity getRarity();
	public double getMultiplier();
	public String getName();
	public IIcon getIcon();
	
}
