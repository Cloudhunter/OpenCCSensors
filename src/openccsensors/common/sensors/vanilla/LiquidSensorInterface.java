package openccsensors.common.sensors.vanilla;

import java.util.Map;

import net.minecraft.src.IInventory;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.liquids.ITankContainer;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.TargetRetriever;

public class LiquidSensorInterface implements ISensorInterface {

	private TargetRetriever retriever = new TargetRetriever();
	
	public LiquidSensorInterface ()
	{
		retriever.registerTarget(ITankContainer.class, new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				return new LiquidTankTarget((TileEntity) entity);
			}
		});
	}
	
	@Override
	public String getName() {
		return "openccsensors.item.liquidsensor";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 25;
	}

	@Override
	public Map getBasicTarget(World world, int x, int y, int z)
			throws Exception {

		return TargetHelper.getBasicInformationForTargets(
				retriever.getAdjacentTiles(world, x, y, z), world);

	}

	@Override
	public Map getTargetDetails(World world, int x, int y, int z, String target)
			throws Exception {
		return TargetHelper.getDetailedInformationForTarget(target,
				retriever.getAdjacentTiles(world, x, y, z), world);
	}

	@Override
	public String[] getMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] callMethod(int methodID, Object[] args) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initRecipes() {
		// TODO Auto-generated method stub
		
	}

}
