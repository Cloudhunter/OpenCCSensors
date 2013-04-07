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
import openccsensors.api.IGaugeSensor;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.Ic2Utils;

public class MachineSensor extends TileSensor implements ISensor, IRequiresIconLoading, IGaugeSensor {

	private Icon icon;
	
	@Override
	public String[] getGaugeProperties() {
		return null;
	}
	
	@Override
	public boolean isValidTarget(Object target) {
		return (ModLoader.isModLoaded("IC2") && Ic2Utils.isValidMachineTarget(target));
	}

	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:machine");
	}

	@Override
	public HashMap getDetails(World world, Object obj, boolean additional) {
		TileEntity tile = (TileEntity) obj;
		HashMap response = super.getDetails(tile);
		if (ModLoader.isModLoaded("IC2")) {
			response.putAll(Ic2Utils.getMachineDetails(world, obj, additional));
		}
		return response;
	}

	@Override
	public String[] getCustomMethods(ISensorTier tier) {
		return null;
	}

	@Override
	public Object callCustomMethod(World world, Vec3 location, int methodID,
			Object[] args, ISensorTier tier) throws Exception {
		return null;
	}

	@Override
	public String getName() {
		return "machine";
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Item.redstone);
	}

}
