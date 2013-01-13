package openccsensors.common.sensors.vanilla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import openccsensors.OpenCCSensors;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.core.OCSLog;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.SensorCard;
import openccsensors.common.sensors.TargetRetriever;

public class InventorySensorInterface implements ISensorInterface {

	private TargetRetriever retriever = new TargetRetriever();
	
	public int[] mapColors = new int[] {
			32768, 	// black
			32, 	// lime
			16, 	// yellow
			256, 	// light gray
			16384, 	// red
			2048, 	// blue
			128, 	// gray
			8192, 	// green
			1, 		// white
			512, 	// cyan
			4096, 	// brown
			128, 	// gray
			2048, 	// blue
			4096 	// brown	
	};

	public InventorySensorInterface() {
		retriever.registerTarget(new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				if (entity instanceof IInventory)
				{
					return new InventoryTarget((TileEntity) entity);
				}
				return null;
			}
		});

	}

	@Override
	public String getName() {
		return "openccsensors.item.inventorysensor";
	}

	@Override
	public String[] getMethods() {
		return new String[] { "getMapImageData" };
	}

	@Override
	public Map callMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args) throws Exception {
		
		switch(methodID)
		{
			case 0:
				return getMapData(world, x, y, z, args);
		}
		return null;
	}

	@Override
	public Map getBasicTarget(ISensorAccess sensor, World world, int x, int y, int z)
			throws Exception {

		return TargetHelper.getBasicInformationForTargets(
				retriever.getSurroundingTileEntities(world, x, y, z), world);

	}

	@Override
	public Map getTargetDetails(ISensorAccess sensor, World world, int x, int y, int z, String target)
			throws Exception {

		return TargetHelper.getDetailedInformationForTarget(target,
				retriever.getSurroundingTileEntities(world, x, y, z), world);

	}

	@Override
	public int getId() {
		return 16;
	}

	@Override
	public void initRecipes(SensorCard card) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
			new ItemStack(card, 1, this.getId()),
			new Object[] {
				"rpr",
				"rrr",
				"aaa",
				Character.valueOf('r'), new ItemStack(Item.redstone),
				Character.valueOf('a'), new ItemStack(Item.paper),
				Character.valueOf('p'), "plankWood"					
			}
		));
	}

	@Override
	public boolean isDirectionalEnabled() {
		return false;
	}
	
	private Map getMapData(World world, int x, int y, int z, Object[] args) {
		
		if (args.length != 2)
		{
			return null;
		}
		
		if (!(args[0] instanceof String))
		{
			return null;
		}
		if (!(args[1] instanceof Double))
		{
			return null;
		}
		String targetName = (String)args[0];
		int slot = (int)((Double)args[1]).intValue() - 1;

		if (slot < 0)
		{
			return null;
		}
		
		// grab all the targets the retriever managed to get
		HashMap<String, ArrayList<ISensorTarget>> possibleTargets = retriever.getSurroundingTileEntities(world, x, y, z);
		
		// if their target exists
		if (possibleTargets.containsKey(targetName))
		{
			ArrayList<ISensorTarget> targets = (ArrayList<ISensorTarget>) possibleTargets.get(targetName);
			
			// loop through the targets
			for (int i = 0; i < targets.size(); i++)
			{
				ISensorTarget target = targets.get(i);
				
				// if its a sensor
				if (target instanceof InventoryTarget)
				{
					// grab the target from world
					IInventory inventory = (IInventory) world.getBlockTileEntity(((InventoryTarget)target).xCoord,
							((InventoryTarget)target).yCoord, ((InventoryTarget)target).zCoord);
					
					// check the slot is valid
					if (slot >= inventory.getSizeInventory())
					{
						return null;
					}
					
					// get the stack
					ItemStack stack = inventory.getStackInSlot(slot);
					
					if (stack == null)
					{
						return null;
					}
					
					Item item = stack.getItem();
					if (item != null && item instanceof ItemMap)
					{
						// Create a new map
						MapData data = ((ItemMap)item).getMapData(stack, world);
						
						// prepare the return data
						HashMap ret = new HashMap();
						ret.put("MapName", data.mapName);
						ret.put("Scale", (int)data.scale);
						ret.put("CenterX", data.xCenter);
						ret.put("CenterZ", data.zCenter);
						HashMap colors = new HashMap();
						
						// put all th colors in
						for (int b = 0; b < data.colors.length; b++)
						{
							colors.put(b + 1,  mapColors[data.colors[b] / 4]);
						}
						ret.put("Colors", colors);
						return ret;
					}
					return null;
				}
			}
		}
		return null;
	}

}