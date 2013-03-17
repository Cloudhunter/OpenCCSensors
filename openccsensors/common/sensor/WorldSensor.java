package openccsensors.common.sensor;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;

public class WorldSensor implements ISensor, IRequiresIconLoading {
	
	private Icon icon;

	@Override
	public HashMap getDetails(World world, Object obj, boolean additional) {
		
		Vec3 location = (Vec3) obj;
		
		HashMap response = new HashMap();
		
		int x = (int) location.xCoord;
		int y = (int) location.yCoord;
		int z = (int) location.zCoord;
		
		response.put("Dimension", world.getWorldInfo().getDimension());
		response.put("Biome", world.getBiomeGenForCoords(x, z).biomeName);
		response.put("LightLevel", world.getBlockLightValue(x, y, z));
		response.put("Raining", world.isRaining());
		response.put("Thundering", world.isThundering());
		response.put("Daytime", world.isDaytime());
		response.put("MoonPhase", world.getMoonPhase());
		response.put("CelestialAngle", world.getCelestialAngle(1.0F));
		
		return response;
	}

	@Override
	public HashMap getTargets(World world, Vec3 location, ISensorTier tier) {
		HashMap targets = new HashMap();
		targets.put("CURRENT", location);
		return targets;
	}

	@Override
	public String[] getCustomMethods(ISensorTier tier) {
		return null;
	}

	@Override
	public Object callCustomMethod(World world, Vec3 location, int methodID,
			Object[] args, ISensorTier tier) {
		return null;
	}

	@Override
	public String getName() {
		return "OpenCCSensors.worldcard";
	}

	@Override
	public Icon getIcon() {
		return icon;
	}
	
	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.func_94245_a("OpenCCSensors:world");
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Item.enderPearl);
	}

}
