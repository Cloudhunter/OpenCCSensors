package openccsensors.common.sensors.buildcraft;

import ic2.api.IReactor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;
import openccsensors.common.core.ISensorCard;
import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.core.OCSLog;
import openccsensors.common.helper.BlockTileHelper;
import openccsensors.common.sensors.GenericSensorInterface;
import openccsensors.common.sensors.TileSensorTarget;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import buildcraft.energy.TileEngine;

public class BuildCraftSensorCard extends Item implements ISensorCard
{

	public BuildCraftSensorCard(int par1)
	{
		super(par1);
		setCreativeTab(CreativeTabs.tabRedstone);
		addRecipeToGameRegistry();
	}

	@Override
	public ISensorInterface getSensorInterface(ItemStack itemstack, boolean turtle) 
	{
		return new BuildCraftSensorInterface();
	}
	
	@Override
	public String getItemNameIS(ItemStack is)
	{
		return "openccsensors.item.buildcraftsensor";
	}
	
	public void addRecipeToGameRegistry()
	{
		GameRegistry.addRecipe(new ItemStack(this), 
				"ccr",
				"crp",
				"rrp",
				'r', new ItemStack(Item.redstone),
				'c', ic2.api.Items.getItem("uraniumIngot") ,
				'p', new ItemStack(Item.paper));
	}
	

}
