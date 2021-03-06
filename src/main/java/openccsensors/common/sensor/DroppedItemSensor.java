package openccsensors.common.sensor;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.EntityUtils;
import openccsensors.common.util.InventoryUtils;

public class DroppedItemSensor implements ISensor, IRequiresIconLoading {

	private IIcon icon;
	
	@Override
	public HashMap getDetails(World world, Object obj, ChunkCoordinates sensorLocation, boolean additional) {
		
		EntityItem item = (EntityItem) obj;

		HashMap response = new HashMap();
		HashMap position = new HashMap();
		
		position.put("X", item.posX - sensorLocation.posX);
		position.put("Y", item.posY - sensorLocation.posY);
		position.put("Z", item.posZ - sensorLocation.posZ);
		
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
	public HashMap getTargets(World world, ChunkCoordinates location, ISensorTier tier) {
		double radius = tier.getMultiplier() * 4;
		return (HashMap) EntityUtils.getEntities(world, location, radius, EntityItem.class);
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
		return "droppedItemCard";
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IIconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:droppedItem");
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack((Item)Item.itemRegistry.getObject("slime_ball"));
	}

}
