package openccsensors.common.sensor;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.InventoryUtils;
import net.minecraft.inventory.IInventory;

public class InventorySensor extends TileSensor implements ISensor, IRequiresIconLoading {

	private Icon icon;

	@Override
	public boolean isValidTarget(TileEntity target) {
		return target instanceof IInventory;
	}
	
	@Override
	public HashMap getDetails(World world, Object obj, boolean additional) {
		
		TileEntity tile = (TileEntity) obj;
		
		HashMap response = super.getDetails(tile);
		
		if (additional) {
			response.put("Slots", InventoryUtils.invToMap((IInventory) tile));
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
		return "OpenCCSensors.inventorycard";
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.func_94245_a("OpenCCSensors:inventory");
	}

}
