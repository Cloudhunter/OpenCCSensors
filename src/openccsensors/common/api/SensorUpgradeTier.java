package openccsensors.common.api;

public class SensorUpgradeTier {

	public static final SensorUpgradeTier TIER1 = new SensorUpgradeTier(1, 1);
	public static final SensorUpgradeTier TIER2 = new SensorUpgradeTier(2, 3);
	public static final SensorUpgradeTier TIER3 = new SensorUpgradeTier(3, 5);
	public static final SensorUpgradeTier TIER4 = new SensorUpgradeTier(4, 7);

	private int level;
	private double multiplier;

	public SensorUpgradeTier(int level, double multiplier) {
		this.level = level;
		this.multiplier = multiplier;
	}

	public double getMultiplier() {
		return this.multiplier;
	}


	public int getLevel() {
		return this.level;
	}

}
