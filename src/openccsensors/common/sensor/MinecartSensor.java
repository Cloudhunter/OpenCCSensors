package openccsensors.common.sensor;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.EntityUtils;
import openccsensors.common.util.InventoryUtils;

public class MinecartSensor implements ISensor, IRequiresIconLoading {

	private Icon icon;


	@Override
	public HashMap getDetails(World world, Object obj, boolean additional) {

		EntityMinecart minecart = (EntityMinecart) obj;

		HashMap response = new HashMap();
		HashMap position = new HashMap();
		
		position.put("X", minecart.posX);
		position.put("Y", minecart.posY);
		position.put("Z", minecart.posZ);
		response.put("Position", position);
		
		response.put("Name", minecart.getEntityName());
		response.put("RawName", EntityList.getEntityString(minecart));
		
		if (minecart instanceof IInventory) {
			response.put("Slots", InventoryUtils.invToMap((IInventory)minecart));
		}
		
		if (minecart.riddenByEntity != null && minecart.riddenByEntity instanceof EntityLiving) {
			response.put("Riding", EntityUtils.livingToMap((EntityLiving)minecart.riddenByEntity, true));
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
		return "openccsensors.minecartcard";
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.func_94245_a("openccsensors:minecart");
		
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Item.minecartEmpty);
	}

}
