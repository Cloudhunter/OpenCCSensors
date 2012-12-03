package openccsensors.common.sensors.vanilla;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import openccsensors.OpenCCSensors;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;

public class WorldSensorInterface implements ISensorInterface {

	@Override
	public String getName() {
		return "openccsensors.item.worldsensor";
	}

	@Override
	public int getId() {
		return 21;
	}

	@Override
	public Map getBasicTarget(World world, int x, int y, int z)
			throws Exception {
		HashMap retMap = new HashMap();
		HashMap currentDetails = new HashMap();
		currentDetails.put("type", "World");
		retMap.put("CURRENT", currentDetails);
		return retMap;
	}

	@Override
	public Map getTargetDetails(World world, int x, int y, int z, String target)
			throws Exception {
		
		HashMap retMap = new HashMap();
		retMap.put("Dimension", world.getWorldInfo().getDimension());
		retMap.put("Biome", world.getBiomeGenForCoords(x, z).biomeName);
		retMap.put("LightLevel", world.getBlockLightValue(x, y, z));
		retMap.put("Raining", world.isRaining());
		
		return retMap;
	}

	@Override
	public String[] getMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] callMethod(ISensorAccess sensor, int methodID, Object[] args) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initRecipes() {
		GameRegistry.addRecipe(
				new ItemStack(OpenCCSensors.sensorCard, 1, this.getId()),
				"rer",
				"rrr",
				"aaa",
				'r', new ItemStack(Item.redstone),
				'a', new ItemStack(Item.paper),
				'e',new ItemStack(Item.enderPearl));
	}

}
