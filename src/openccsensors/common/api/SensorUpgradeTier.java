package openccsensors.common.api;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class SensorUpgradeTier {

	public static final SensorUpgradeTier TIER1 = new SensorUpgradeTier(1, 1, "openccsensors:tier1");
	public static final SensorUpgradeTier TIER2 = new SensorUpgradeTier(2, 3, "openccsensors:tier2");
	public static final SensorUpgradeTier TIER3 = new SensorUpgradeTier(3, 5, "openccsensors:tier3");
	public static final SensorUpgradeTier TIER4 = new SensorUpgradeTier(4, 7, "openccsensors:tier4");

	private int level;
	private double multiplier;
	private String strOverlayIcon;
	private Icon overlayIcon;

	public SensorUpgradeTier(int level, double multiplier, String overlay) {
		this.level = level;
		this.multiplier = multiplier;
		this.strOverlayIcon = overlay;
	}

	public double getMultiplier() {
		return this.multiplier;
	}

	public void setOverlayIcon(Icon overlay) {
		overlayIcon = overlay;
	}
	
	public Icon getOverlayIcon() {
		return overlayIcon;
	}

	public int getLevel() {
		return this.level;
	}

	public void registerIcon(IconRegister iconRegister) {
		setOverlayIcon(iconRegister.func_94245_a(strOverlayIcon));
	}

}
