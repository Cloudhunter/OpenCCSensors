package openccsensors.common.sensors.industrialcraft;

import java.util.Map;

import ic2.api.IEnergyConductor;
import ic2.api.IEnergySink;
import ic2.api.IEnergySource;
import ic2.api.IEnergyStorage;
import ic2.api.IReactor;
import ic2.api.IReactorChamber;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.TargetRetriever;
import openccsensors.common.sensors.industrialcraft.ReactorTarget;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class IC2SensorInterface implements ISensorInterface 
{
	
	private TargetRetriever retriever = new TargetRetriever();
	
	public IC2SensorInterface ()
	{
		retriever.registerTarget(IReactor.class, new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				return new ReactorTarget((TileEntity)entity);
			}
		});
		
		retriever.registerTarget(IEnergyStorage.class, new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				return new EnergyStorageTarget((TileEntity)entity);
			}
		});
		
		retriever.registerTarget(IReactorChamber.class, new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				return new ReactorTarget((TileEntity)((IReactorChamber)entity).getReactor());
			}
		});
		
		retriever.registerTargets(new Class[] {
					IEnergySink.class,
					IEnergySource.class,
					IEnergyConductor.class
			}, new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				return new EnergyConductorTarget((TileEntity)entity);
			}
		});
	}

	@Override
	public String getName() 
	{
		return "ic2sensor";
	}

	@Override
	public String[] getMethods() 
	{
		return null;
	}

	@Override
	public Object[] callMethod(int methodID, Object[] args) throws Exception 
	{
		return null;
	}
	
	
	public Map getBasicTarget(World world, int x, int y, int z)
	{
		return TargetHelper.getBasicInformationForTargets(
				retriever.getAdjacentTiles(world, x, y, z),
				world
		);
	}

	@Override
	public Map getTargetDetails(World world, int x, int y, int z, String target)
			throws Exception {

		return TargetHelper.getDetailedInformationForTarget(
				target,
				retriever.getAdjacentTiles(world, x, y, z),
				world
		);
		
	}
	
}
