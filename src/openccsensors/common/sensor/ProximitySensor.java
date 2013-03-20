package openccsensors.common.sensor;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorCardRegistry;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.EntityUtils;

public class ProximitySensor implements ISensor, IRequiresIconLoading {
	
	private Icon icon;

	@Override
	public HashMap getDetails(World world, Object obj, boolean additional) {
		return EntityUtils.livingToMap((EntityLiving)obj, additional);
	}

	@Override
	public HashMap getTargets(World world, Vec3 location,
			ISensorTier tier) {
		double radius = tier.getMultiplier() * 4;
		return (HashMap) EntityUtils.getEntities(world, location, radius, EntityLiving.class);
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
		return "openccsensors.proximitycard";
	}
	
	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.func_94245_a("openccsensors:proximity");
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Block.pressurePlateStone);
	}


}
