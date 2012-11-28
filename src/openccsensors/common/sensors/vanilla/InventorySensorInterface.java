package openccsensors.common.sensors.vanilla;

import ic2.api.IEnergyStorage;
import ic2.api.IReactor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.src.IInventory;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.core.ITargetWrapper;
import openccsensors.common.helper.BlockTileHelper;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.TargetRetriever;
import openccsensors.common.sensors.industrialcraft.EnergyStorageTarget;
import openccsensors.common.sensors.industrialcraft.ReactorTarget;

public class InventorySensorInterface implements ISensorInterface {

	private TargetRetriever retriever = new TargetRetriever();
	
	public InventorySensorInterface()
	{
		retriever.registerTarget(IInventory.class, new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				return new InventoryTarget((TileEntity)entity);
			}
		});
		
	}

	@Override
	public String getName() {
		return "inventory";
	}

	@Override
	public String[] getMethods() {
		return null;
	}

	@Override
	public Object[] callMethod(int methodID, Object[] args) throws Exception {
		return null;
	}

	@Override
	public Map getBasicTarget(World world, int x, int y, int z)
			throws Exception {
		
		return TargetHelper.getBasicInformationForTargets(
				retriever.getAdjacentTiles(world, x, y, z, true),
				world
		);
		
	}

	@Override
	public Map getTargetDetails(World world, int x, int y, int z, String target)
			throws Exception {

		return TargetHelper.getDetailedInformationForTarget(
				target,
				retriever.getAdjacentTiles(world, x, y, z, true),
				world
		);
		
	}

}