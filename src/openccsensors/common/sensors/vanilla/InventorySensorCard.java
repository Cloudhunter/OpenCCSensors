package openccsensors.common.sensors.vanilla;

import ic2.api.IEnergyStorage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.ISensorCard;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.core.OCSLog;
import openccsensors.common.helper.BlockTileHelper;
import openccsensors.common.helper.InventoryHelper;
import openccsensors.common.helper.SensorHelper;
import openccsensors.common.sensors.GenericSensorInterface;
import openccsensors.common.sensors.TileSensorTarget;

public class InventorySensorCard extends Item implements ISensorCard {

	public InventorySensorCard(int par1) {
		super(par1);
		setCreativeTab(CreativeTabs.tabRedstone);
		addRecipeToGameRegistry();
	}

	@Override
	public ISensorInterface getSensorInterface(ItemStack itemstack,
			boolean turtle) {
		return new InventorySensorInterface();
	}

	@Override
	public String getItemNameIS(ItemStack is) {
		return "openccsensors.item.inventorysensor";
	}
	
	public void addRecipeToGameRegistry()
	{
		
		GameRegistry.addRecipe(new ItemStack(this),
				"wwr",
				"wrr",
				"rrp",
				'w', new ItemStack(Block.planks),
				'r', new ItemStack(Item.redstone),
				'p', new ItemStack(Item.paper));
	}

}
