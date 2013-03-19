package openccsensors.api;

import net.minecraft.util.Icon;


public class SensorCard {

	private ISensor sensor;
	private ISensorTier tier;
	
	public SensorCard(ISensor sensor, ISensorTier tier) {
		this.sensor = sensor;
		this.tier = tier;
	}
	
	public ISensorTier getTier() {
		return tier;
	}
	
	public ISensor getSensor() {
		return sensor;
	}
	
	public Icon getIconForRenderPass(int pass) {
		if (pass == 0) {
			return getSensor().getIcon();
		}
		return getTier().getIcon();
	}

}
