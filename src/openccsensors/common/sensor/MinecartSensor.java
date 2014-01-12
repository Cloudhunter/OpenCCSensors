package openccsensors.common.sensor;

import java.util.HashMap;

import cpw.mods.fml.common.Loader;

import mods.railcraft.api.carts.IEnergyTransfer;
import mods.railcraft.api.carts.IExplosiveCart;
import mods.railcraft.api.carts.IRoutableCart;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidHandler;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.EntityUtils;
import openccsensors.common.util.InventoryUtils;
import openccsensors.common.util.RailcraftUtils;
import openccsensors.common.util.TankUtils;

public class MinecartSensor implements ISensor, IRequiresIconLoading {

	private Icon icon;


	@Override
	public HashMap getDetails(World world, Object obj, Vec3 sensorPos, boolean additional) {

		EntityMinecart minecart = (EntityMinecart) obj;

		HashMap response = new HashMap();
		HashMap position = new HashMap();
		
		position.put("X", minecart.posX - sensorPos.xCoord);
		position.put("Y", minecart.posY - sensorPos.yCoord);
		position.put("Z", minecart.posZ - sensorPos.zCoord);
		response.put("Position", position);
		
		response.put("Name", minecart.getEntityName());
		response.put("RawName", EntityList.getEntityString(minecart));
		
		if (minecart instanceof IInventory) {
			response.put("Slots", InventoryUtils.invToMap((IInventory)minecart));
		}

		if (minecart instanceof IFluidHandler) {
			response.put("Tanks", TankUtils.fluidHandlerToMap((IFluidHandler)minecart));
		}
		
		if (minecart.riddenByEntity != null && minecart.riddenByEntity instanceof EntityLivingBase) {
			response.put("Riding", EntityUtils.livingToMap((EntityLivingBase)minecart.riddenByEntity, sensorPos, true));
		}
		
		if (Loader.isModLoaded("Railcraft")) {
			if (minecart instanceof IEnergyTransfer) {
				response.putAll(RailcraftUtils.getEnergyDetails(minecart));
			}
			
			if (minecart instanceof IExplosiveCart) {
				response.putAll(RailcraftUtils.getExplosiveDetails(minecart));
			}
			
			if (minecart instanceof IRoutableCart) {
				response.put("RouteDestination", ((IRoutableCart)minecart).getDestination());
			}
		}
		return response;
	}

	@Override
	public HashMap getTargets(World world, Vec3 location, ISensorTier tier) {
		double radius = tier.getMultiplier() * 4;
		return (HashMap) EntityUtils.getEntities(world, location, radius, EntityMinecart.class);
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
		return "minecartCard";
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:minecart");
		
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Item.minecartEmpty);
	}

}
