package openccsensors.common.helper;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;

public class BeeHelper {

	public static boolean isBee(ItemStack itemstack) {
		try {
			return BeeManager.beeInterface.isBee(itemstack);
		} catch (Exception e) {
			return false;
		}
	}

	// make sure you run isBee first
	public static HashMap beeMap(ItemStack itemstack, HashMap map) {
		try {
			if (BeeManager.beeInterface.isBee(itemstack)) {

				if (BeeManager.beeInterface.isDrone(itemstack))
					map.put("Type", "Drone");
				else if (BeeManager.beeInterface.isMated(itemstack))
					map.put("Type", "Queen");
				else
					map.put("Type", "Prinsess");
				try {

					IBee thisBee = BeeManager.beeInterface.getBee(itemstack);

					if (thisBee.isAnalyzed()) {
						map.put("IsAnalysed", true);
						map.put("IsNatural", thisBee.isNatural());
						map.put("Generation", thisBee.getGeneration());
						map.put("Health", thisBee.getHealth());
						map.put("MaxHealth", thisBee.getMaxHealth());
						map.put("HasEffect", thisBee.hasEffect());

						IBeeGenome genome = thisBee.getGenome();
						map.put("Speed", genome.getSpeed());
						map.put("Lifespan", genome.getLifespan());
						map.put("TolerantFlyer", genome.getTolerantFlyer());
						map.put("CaveDwelling", genome.getCaveDwelling());
						map.put("Fertility", genome.getFertility());
						map.put("Nocturnal", genome.getNocturnal());
						map.put("Fertility", genome.getFertility());
						map.put("Flowering", genome.getFlowering());
					} else {
						map.put("IsAnalysed", false);

					}
				} catch (Exception ibee) {
				}
			}
		} catch (Exception e) {

		}
		return map;
	}

	// TODO fit this for the gauge
	public static int beeHp(ItemStack itemstack) {
		IBee thisBee = BeeManager.beeInterface.getBee(itemstack);
		int hp = thisBee.getHealth();
		int maxhp = thisBee.getMaxHealth();// use this for the gauge
		try {
			return (int) ((hp / maxhp) * 100);
		} catch (Exception e) {
			return 0;
		}
	}

}
