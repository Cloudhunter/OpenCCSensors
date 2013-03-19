package openccsensors.api;

import net.minecraft.util.Icon;

public interface ISensorTier {
	
	public EnumItemRarity getRarity();
	public double getMultiplier();
	public String getName();
	public Icon getIcon();
	
}
