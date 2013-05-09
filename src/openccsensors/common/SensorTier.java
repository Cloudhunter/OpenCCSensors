package openccsensors.common;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import openccsensors.api.EnumItemRarity;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensorTier;

public class SensorTier implements ISensorTier, IRequiresIconLoading {

	private String name;
	private EnumItemRarity rarity;
	private int multiplier;
	private Icon icon;
	private String iconName;

	public SensorTier(String name, EnumItemRarity rarity, int multiplier, String iconName) {
		this.name = name;
		this.rarity = rarity;
		this.multiplier = multiplier;
		this.iconName = iconName;
	}

	@Override
	public EnumItemRarity getRarity() {
		return rarity;
	}

	@Override
	public double getMultiplier() {
		return multiplier;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.registerIcon(iconName);
	}


}
