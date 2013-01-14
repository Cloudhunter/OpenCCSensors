package openccsensors.common.sensors.vanilla;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.SensorCard;
import cpw.mods.fml.common.registry.GameRegistry;

public class WorldSensorInterface implements ISensorInterface {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public Map callMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getBasicTarget(ISensorAccess sensor, World world, int x, int y, int z)
			throws Exception {
		HashMap retMap = new HashMap();
		HashMap currentDetails = new HashMap();
		currentDetails.put("type", "World");
		retMap.put("CURRENT", currentDetails);
		return retMap;
	}

	@Override
	public int getId() {
		return 21;
	}

	@Override
	public String[] getMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "openccsensors.item.worldsensor";
	}

	@Override
	public Map getTargetDetails(ISensorAccess sensor, World world, int x, int y, int z, String target)
			throws Exception {
		
		HashMap retMap = new HashMap();
		retMap.put("Dimension", world.getWorldInfo().getDimension());
		retMap.put("Biome", world.getBiomeGenForCoords(x, z).biomeName);
		retMap.put("LightLevel", world.getBlockLightValue(x, y, z));
		retMap.put("Raining", world.isRaining());
		retMap.put("Thundering", world.isThundering());
		retMap.put("Daytime", world.isDaytime());
		
		return retMap;
	}
	
	@Override
	public ISensorTarget getRelevantTargetForGauge(World world, int x, int y,
			int z) {
		return null;
	}

	@Override
	public void initRecipes(SensorCard card) {
		GameRegistry.addRecipe(
				new ItemStack(card, 1, this.getId()),
				"rer",
				"rrr",
				"aaa",
				'r', new ItemStack(Item.redstone),
				'a', new ItemStack(Item.paper),
				'e',new ItemStack(Item.enderPearl));
	}

	@Override
	public boolean isDirectionalEnabled() {
		return false;
	}
	
}
