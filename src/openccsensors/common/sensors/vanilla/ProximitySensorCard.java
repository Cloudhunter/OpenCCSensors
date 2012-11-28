package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.ISensorCard;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.helper.BlockTileHelper;
import openccsensors.common.helper.LivingEntityHelper;
import openccsensors.common.helper.SensorHelper;
import openccsensors.common.sensors.GenericSensorInterface;

/**
 * Item which allows sensing of living entities within the defined radius
 */
public class ProximitySensorCard extends Item implements ISensorCard
{

	public ProximitySensorCard(int par1)
	{
		super(par1);
		setCreativeTab(CreativeTabs.tabRedstone);
		addRecipeToGameRegistry();
	}
	
	// Temporary! To differentiate this sensor card from the Inventory one. 
	@Override
	public int getIconFromDamage(int par1) {
		return 1;
	}

	@Override
	public ISensorInterface getSensorInterface(ItemStack itemstack, boolean turtle) 
	{
		return new InventorySensorInterface();
	}
	
	@Override
	public String getItemNameIS(ItemStack is)
	{
		return "openccsensors.item.proximitysensor";
	}
	
	public void addRecipeToGameRegistry()
	{
		GameRegistry.addRecipe(new ItemStack(this),
				"rpr",
				"rrr",
				"rrr",
				'r', new ItemStack(Item.redstone),
				'p', new ItemStack(Block.pressurePlateStone));
	}
	
}
