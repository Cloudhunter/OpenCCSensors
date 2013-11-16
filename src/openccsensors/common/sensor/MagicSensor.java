package openccsensors.common.sensor;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.MagicUtils;

public class MagicSensor extends TileSensor implements ISensor, IRequiresIconLoading {

	private Icon icon;

	@Override
	public boolean isValidTarget(Object tile) {
		return (ModLoader.isModLoaded("Thaumcraft") && MagicUtils.isValidAspectTarget(tile)) ||
		       (ModLoader.isModLoaded("ArsMagica") && MagicUtils.isValidAspectTarget(tile));
	}

	@Override
	public HashMap getDetails(World world, Object obj, Vec3 sensorPos, boolean additional) {
		TileEntity tile = (TileEntity) obj;
		HashMap response = super.getDetails(tile, sensorPos);
		if (ModLoader.isModLoaded("Thaumcraft")) {
			response.put("Aspects", MagicUtils.getMapOfAspects(world, obj, additional));
		}
		if (ModLoader.isModLoaded("ArsMagica")) {
			response.putAll(MagicUtils.getMapOfArsMagicaPower(world, obj, additional));
		}
		return response;
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
		return "magicCard";
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:magic");
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Item.bucketEmpty);
	}

}
