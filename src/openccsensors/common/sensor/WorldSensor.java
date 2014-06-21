package openccsensors.common.sensor;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;

public class WorldSensor implements ISensor, IRequiresIconLoading {
	
	private Icon icon;
	
	@Override
	public HashMap getDetails(World world, Object obj, ChunkCoordinates sensorLocation, boolean additional) {
		
		HashMap response = new HashMap();
		
		int x = (int) sensorLocation.posX;
		int y = (int) sensorLocation.posY;
		int z = (int) sensorLocation.posZ;
		
		response.put("Dimension", world.getWorldInfo().getVanillaDimension());
		response.put("Biome", world.getBiomeGenForCoords(x, z).biomeName);
		response.put("LightLevel", world.getBlockLightValue(x, y, z));
		response.put("Raining", world.isRaining());
		response.put("Thundering", world.isThundering());
		response.put("Daytime", world.isDaytime());
		response.put("MoonPhase", world.getCurrentMoonPhaseFactor());
		response.put("CelestialAngle", world.getCelestialAngle(1.0F));
		
		return response;
	}

	@Override
	public HashMap getTargets(World world, ChunkCoordinates location, ISensorTier tier) {
		HashMap targets = new HashMap();
		targets.put("CURRENT", location);
		return targets;
	}

	@Override
	public String[] getCustomMethods(ISensorTier tier) {
		return null;
	}

	@Override
	public Object callCustomMethod(World world, ChunkCoordinates location, int methodID,
			Object[] args, ISensorTier tier) {
		return null;
	}

	@Override
	public String getName() {
		return "worldCard";
	}

	@Override
	public Icon getIcon() {
		return icon;
	}
	
	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:world");
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Item.enderPearl);
	}

}
