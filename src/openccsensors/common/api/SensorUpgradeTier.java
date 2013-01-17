package openccsensors.common.api;

import net.minecraft.item.EnumRarity;

public class SensorUpgradeTier {

	public static final SensorUpgradeTier TIER1 = new SensorUpgradeTier(1, 1,
			EnumRarity.common);
	public static final SensorUpgradeTier TIER2 = new SensorUpgradeTier(2, 2,
			EnumRarity.uncommon);
	public static final SensorUpgradeTier TIER3 = new SensorUpgradeTier(3, 3,
			EnumRarity.rare);
	public static final SensorUpgradeTier TIER4 = new SensorUpgradeTier(4, 4,
			EnumRarity.epic);

	private int level;
	private double multiplier;
	private EnumRarity rarity;

	public SensorUpgradeTier(int level, double multiplier, EnumRarity rarity) {
		this.level = level;
		this.multiplier = multiplier;
		this.rarity = rarity;
	}

	public double getMultiplier() {
		return this.multiplier;
	}

	public EnumRarity getRarity() {
		return this.rarity;
	}

	public int getLevel() {
		return this.level;
	}

}
