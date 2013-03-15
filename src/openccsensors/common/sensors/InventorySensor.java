package openccsensors.common.sensors;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.api.SensorUpgradeTier;
import openccsensors.common.sensors.targets.InventoryTarget;

public class InventorySensor extends BaseTileEntitySensor implements ISensor {

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
	
	public InventorySensor() {
		registerCallback(new ITileEntityValidatorCallback() {

			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity,
					int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof IInventory) {
					return new InventoryTarget(entity, relativeX, relativeY,
							relativeZ);
				}
				return null;
			}

		});
	}

	@Override
	public String[] getCustomMethods(SensorUpgradeTier upgrade) {
		return new String[] { "getMapData" };
	}

	@Override
	public Object callCustomMethod(ISensorAccess sensor, World world, int x,
			int y, int z, int methodID, Object[] args, SensorUpgradeTier upgrade)
			throws Exception {
		switch (methodID) {
		case 0:
			if (args.length != 2) {
				throw new Exception("This method expects two parameters");
			}
			
			if (args[1] instanceof Double) {
				args[1] = ((Double)args[1]).intValue();
			}
			
			if (!(args[0] instanceof String) || !(args[1] instanceof Integer)) {
				throw new Exception("Incorrect parameters.");
			}
			String targetName = (String)args[0];
			int slot = ((Integer)args[1])-1;
			HashMap<String, ArrayList<ISensorTarget>> allSurroundingTargets = getSurroundingTargets(world, x, y, z, upgrade);
			if (allSurroundingTargets.containsKey(targetName)) {

				ArrayList<ISensorTarget> targets = allSurroundingTargets.get(targetName);
				
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
							throw new Exception("The slot you specified doesn't seem to exist!");
						}

						// get the stack
						ItemStack stack = inventory.getStackInSlot(slot);

						if (stack == null)
						{
							throw new Exception("Whoopies. Are sure sure there's a map in that slot?");
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

							// put all the colors in
							for (int b = 0; b < data.colors.length; b++)
							{
								colors.put(b + 1,  mapColors[data.colors[b] / 4]);
							}
							ret.put("Colors", colors);
							return ret;
						}
						throw new Exception("Are you sure its a map in the specified slot?");
						
					}

				}
				
			}
			
			throw new Exception("Something went wrong!");
			
			
		}
		throw new Exception("That method was not found!");

	}

	@Override
	public String getIconName() {
		return "openccsensors:inventory";
	}

}
