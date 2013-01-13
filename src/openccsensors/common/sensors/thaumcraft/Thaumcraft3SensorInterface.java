package openccsensors.common.sensors.thaumcraft;

import ic2.api.IReactorChamber;

import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.core.OCSLog;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.SensorCard;
import openccsensors.common.sensors.TargetRetriever;
import openccsensors.common.sensors.industrialcraft.ReactorTarget;

public class Thaumcraft3SensorInterface  implements ISensorInterface {

	private TargetRetriever retriever = new TargetRetriever();
	
	public static final String BRAIN_IN_A_JAR_CLASS = "thaumcraft.common.blocks.jars.TileJarBrain";
	public static final String CRUCIBLE_CLASS = "thaumcraft.common.blocks.TileCrucible";
	public static final String FILLED_JAR_CLASS = "thaumcraft.common.blocks.jars.TileJarFillable";
	
	public Thaumcraft3SensorInterface()
	{

		retriever.registerTarget(new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				
				String className = entity.getClass().getName();
				
				if (className.equals(BRAIN_IN_A_JAR_CLASS))
				{
					return new BrainInAJarTarget((TileEntity) entity);
				}
				else if (className.equals(CRUCIBLE_CLASS))
				{
					return new CrucibleTarget((TileEntity) entity);
				}
				else if (className.equals(FILLED_JAR_CLASS))
				{
					return new FilledJarTarget((TileEntity) entity);
				}
				return null;
			}
		});
	}
	
	@Override
	public String getName() {
		return "openccsensors.item.thaumcraft3sensor";
	}

	@Override
	public int getId() {
		return 24;
	}

	@Override
	public Map getBasicTarget(ISensorAccess sensor, World world, int x, int y,
			int z) throws Exception {
		
		return TargetHelper.getBasicInformationForTargets(
				retriever.getSurroundingTileEntities(world, x, y, z), world);
		
	}

	@Override
	public Map getTargetDetails(ISensorAccess sensor, World world, int x,
			int y, int z, String target) throws Exception {

		return TargetHelper.getDetailedInformationForTarget(target,
				retriever.getSurroundingTileEntities(world, x, y, z), world);
		
	}

	@Override
	public boolean isDirectionalEnabled() {
		return false;
	}

	@Override
	public String[] getMethods() {
		return null;
	}

	@Override
	public Map callMethod(ISensorAccess sensor, World world, int x, int y,
			int z, int methodID, Object[] args) throws Exception {
		return null;
	}

	@Override
	public void initRecipes(SensorCard card) {
		// TODO Auto-generated method stub
		
	}
}
