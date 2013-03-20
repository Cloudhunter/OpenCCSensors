package openccsensors.common.sensor;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.InventoryUtils;
import openccsensors.common.util.RailcraftUtils;

public class TankSensor extends TileSensor implements ISensor, IRequiresIconLoading {
	
	private Icon icon;

	@Override
	public boolean isValidTarget(Object tile) {
		if (tile instanceof ITankContainer) {
			ILiquidTank[] tanks = ((ITankContainer)tile).getTanks(ForgeDirection.UNKNOWN);
			return tanks.length > 0;
		} else if (ModLoader.isModLoaded("Railcraft") && tile instanceof TileEntity) {
			return RailcraftUtils.isTankTile((TileEntity)tile);
		}
		return false;
	}

	@Override
	public HashMap getDetails(World world, Object obj, boolean additional) {
		
		TileEntity tile = (TileEntity) obj;

		HashMap response = super.getDetails(tile);
		ILiquidTank[] tanks = null;
		
		if (tile instanceof ITankContainer) {
			tanks = ((ITankContainer)tile).getTanks(ForgeDirection.UNKNOWN);
		}else {
			ILiquidTank tank = RailcraftUtils.getTankIfTankTile(tile);
			if (tank != null) {
				tanks = new ILiquidTank[] { tank };
			}
		}
		
		if (additional && tanks != null) {
			HashMap allTanks = new HashMap();
			int i = 0;
			try {
				if (tanks != null) {
					for (ILiquidTank tank : tanks) {
						
						HashMap tankMap = new HashMap();
						tankMap.put("Capacity", tank.getCapacity());
						tankMap.put("Amount", 0);
						
						LiquidStack stack = tank.getLiquid();

						if (stack != null) {
							ItemStack istack = stack.asItemStack();
							if (istack != null) {
								if (istack.getItem() != null) {
									tankMap.put("Name", InventoryUtils.getNameForItemStack(istack));
									tankMap.put("RawName", InventoryUtils.getRawNameForStack(istack));
									tankMap.put("Amount", stack.amount);
								}
							}
						}

						allTanks.put(++i, tankMap);
					}
				}
			}catch(Exception e) {}
			
			response.put("Tanks", allTanks);
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
		return "tankCard";
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.func_94245_a("openccsensors:tank");
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Item.bucketEmpty);
	}

}
