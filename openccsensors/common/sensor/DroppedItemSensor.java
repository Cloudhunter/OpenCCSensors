package openccsensors.common.sensor;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
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

public class DroppedItemSensor implements ISensor, IRequiresIconLoading {

	private Icon icon;
	
	@Override
	public HashMap getDetails(World world, Object obj, boolean additional) {
		
		EntityItem item = (EntityItem) obj;
		
		HashMap response = new HashMap();
		
		HashMap position = new HashMap();
		position.put("X", item.posX);
		position.put("Y", item.posY);
		position.put("Z", item.posZ);
		
		response.put("Position", position);

		ItemStack stack = item.getEntityItem();
		
		response.put("Name", InventoryUtils.getNameForItemStack(stack));
		response.put("RawName", InventoryUtils.getRawNameForStack(stack));
		
		if (additional) {
			
			response.putAll(InventoryUtils.itemstackToMap(stack));
			response.put("IsBurning", item.isBurning());
			
		}
		
		return response;
	}

	@Override
	public HashMap getTargets(World world, Vec3 location, ISensorTier tier) {
		double radius = tier.getMultiplier() * 4;
		return (HashMap) EntityUtils.getEntities(world, location, radius, EntityItem.class);
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
		return "OpenCCSensors.proximitycard";
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.func_94245_a("OpenCCSensors:droppedItem");
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Item.slimeBall);
	}

}
