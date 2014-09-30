package openccsensors.common.sensor;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.TankUtils;

public class TankSensor extends TileSensor implements ISensor, IRequiresIconLoading {
	
	private IIcon icon;

	@Override
	public boolean isValidTarget(Object tile) {
		if (tile instanceof IFluidHandler) {
			FluidTankInfo[] tanks = ((IFluidHandler)tile).getTankInfo(ForgeDirection.UNKNOWN);
			if (tanks != null) {
				return tanks.length > 0;
			}
		}
		return false;
	}

	@Override
	public HashMap getDetails(World world, Object obj, ChunkCoordinates sensorPos, boolean additional) {
		TileEntity tile = (TileEntity) obj;
		HashMap response = super.getDetails(tile, sensorPos);
		response.put("Tanks", TankUtils.fluidHandlerToMap((IFluidHandler)tile));
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
		return "tankCard";
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IIconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:tank");
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack((Item)Item.itemRegistry.getObject("bucket"));
	}

}
