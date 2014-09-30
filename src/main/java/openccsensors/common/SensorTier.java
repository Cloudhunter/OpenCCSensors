package openccsensors.common;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import openccsensors.api.EnumItemRarity;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensorTier;

public class SensorTier implements ISensorTier, IRequiresIconLoading {

	private String name;
	private EnumItemRarity rarity;
	private int multiplier;
	private IIcon icon;
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
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IIconRegister iconRegistry) {
		icon = iconRegistry.registerIcon(iconName);
	}


}
