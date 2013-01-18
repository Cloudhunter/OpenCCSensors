package openccsensors.common.api;

import net.minecraft.item.ItemStack;
import openccsensors.OpenCCSensors;

public class SensorCardInterface {

	private int id;
	private String name;
	private SensorUpgradeTier upgrade;
	private Class sensorClass;

	/**
	 * 
	 * @param id
	 *            This is the icon index
	 * @param name
	 *            This is the key in the language file
	 * @param upgrade
	 *            The upgrade for this
	 * @param sensor
	 *            The sensor type
	 */
	public SensorCardInterface(int id, String name, SensorUpgradeTier upgrade,
			Class sensorClass) {
		this.id = id;
		this.name = name;
		this.upgrade = upgrade;
		this.sensorClass = sensorClass;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public SensorUpgradeTier getSensorUpgrade() {
		return this.upgrade;
	}

	public ISensor getSensor() {
		return SensorManager.getSensor(sensorClass);
	}
	
	public ItemStack newItemStack(int count) {
		return new ItemStack(OpenCCSensors.Items.sensorCard, count, this.id);
	}

}
