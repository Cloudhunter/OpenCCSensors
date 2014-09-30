package openccsensors.common.sensor;

import java.util.HashMap;

import cpw.mods.fml.common.Loader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.MagicUtils;
import openccsensors.common.util.Mods;

public class MagicSensor extends TileSensor implements ISensor, IRequiresIconLoading {

	private IIcon icon;

	@Override
	public boolean isValidTarget(Object tile) {
		return (Mods.TC && MagicUtils.isValidAspectTarget(tile)) ||
		       (Mods.AM && MagicUtils.isValidAspectTarget(tile));
	}

	@Override
	public HashMap getDetails(World world, Object obj, ChunkCoordinates sensorPos, boolean additional) {
		TileEntity tile = (TileEntity) obj;
		HashMap response = super.getDetails(tile, sensorPos);
		if (Mods.TC) {
			response.put("Aspects", MagicUtils.getMapOfAspects(world, obj, additional));
		}
		if (Mods.AM) {
			response.putAll(MagicUtils.getMapOfArsMagicaPower(world, obj, additional));
		}
		return response;
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
		return "magicCard";
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IIconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:magic");
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack((Item)Item.itemRegistry.getObject("gold_ingot"));
	}

}
