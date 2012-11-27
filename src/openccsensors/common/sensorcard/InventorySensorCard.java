package openccsensors.common.sensorcard;

import ic2.api.IEnergyStorage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.core.GenericSensorInterface;
import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.ISensorCard;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.core.OCSLog;
import openccsensors.common.helper.BlockTileHelper;
import openccsensors.common.helper.InventoryHelper;
import openccsensors.common.helper.SensorHelper;
import openccsensors.common.sensortargets.TileSensorTarget;

public class InventorySensorCard extends Item implements ISensorCard {

	public InventorySensorCard(int par1) {
		super(par1);
		setCreativeTab(CreativeTabs.tabRedstone);
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

	public class InventorySensorInterface extends GenericSensorInterface
			implements ISensorInterface {

		Class[] relevantClassTypes = { 
				IInventory.class
		};

		@Override
		public String getName() {
			return "inventory";
		}

		@Override
		public HashMap<String, ISensorTarget> getAvailableTargets(World world,
				int x, int y, int z) {
			HashMap<String, ISensorTarget> targets = new HashMap<String, ISensorTarget>();

			HashMap<String, TileEntity> entities = BlockTileHelper.getAdjacentTile(world, x, y, z, relevantClassTypes);

			BlockTileHelper.addToHashMap(
								world.getBlockTileEntity(x, y, z),
								entities,
								relevantClassTypes,
								"SELF"
			);

			Iterator it = entities.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<String, TileEntity> pairs = (Entry<String, TileEntity>) it.next();
				targets.put(pairs.getKey(), new InventoryTarget(pairs.getValue()));
			}

			return targets;
		}

		@Override
		public String[] getMethods() {
			return null;
		}

		@Override
		public Object[] callMethod(int methodID, Object[] args)
				throws Exception {
			return null;
		}

	}

	protected class InventoryTarget extends TileSensorTarget implements
			ISensorTarget {

		protected InventoryTarget(TileEntity targetEntity) {
			super(targetEntity);
		}

		@Override
		public Map getDetailInformation(World world) {
			HashMap retMap = new HashMap();
			TileEntity tile = world.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (tile == null || !(tile instanceof IInventory)) {
				return null;
			}

			return InventoryHelper.invToMap((IInventory) tile);
		}

	}

}
