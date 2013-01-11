package openccsensors.common.sensors.industrialcraft;

import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.IEnergyStorage;
import ic2.api.IReactor;
import ic2.api.IReactorChamber;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import openccsensors.OpenCCSensors;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.core.OCSLog;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.SensorCard;
import openccsensors.common.sensors.TargetRetriever;
import openccsensors.common.sensors.industrialcraft.ReactorTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class IC2SensorInterface implements ISensorInterface {

	public final static int MASSFAB_MAX_ENERGY = 1100000;
	
	private TargetRetriever retriever = new TargetRetriever();

	public final static String MASS_FAB_CLASS = "ic2.core.block.machine.tileentity.TileEntityMatter";
	
	public IC2SensorInterface() {
		retriever.registerTarget(new ITargetWrapper() {
			
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				if (entity instanceof IReactor)
				{
					return new ReactorTarget((TileEntity) entity);
				}
				else if (entity instanceof IReactorChamber)
				{
					return new ReactorTarget(
							(TileEntity) ((IReactorChamber) entity).getReactor());
				}
				return null;
			}
		});

		retriever.registerTarget(new ITargetWrapper() {

			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy,
					int sz) {
				
				if (entity instanceof TileEntity && entity.getClass().getName() == MASS_FAB_CLASS)
				{
					return new MassFabTarget((TileEntity) entity);
					
				}
				return null;
			}
			
		});
		
		retriever.registerTarget(new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				if (entity instanceof IEnergyStorage)
				{
					return new EnergyStorageTarget((TileEntity) entity);
				}
				return null;
			}
		});


		retriever.registerTarget(new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx,
					int sy, int sz) {
				if (entity instanceof IEnergySink ||
					entity instanceof IEnergySource ||
					entity instanceof IEnergyConductor)
				{
					return new EnergyConductorTarget((TileEntity) entity);
				}
				return null;
			}
		});
	}

	@Override
	public String getName() {
		return "openccsensors.item.ic2sensor";
	}

	@Override
	public String[] getMethods() {
		return null;
	}

	@Override
	public Map callMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args) throws Exception {
		return null;
	}

	@Override
	public Map getBasicTarget(ISensorAccess sensor, World world, int x, int y, int z) {
		return TargetHelper.getBasicInformationForTargets(
				retriever.getAdjacentTiles(world, x, y, z), world);
	}

	@Override
	public Map getTargetDetails(ISensorAccess sensor, World world, int x, int y, int z, String target)
			throws Exception {

		return TargetHelper.getDetailedInformationForTarget(target,
				retriever.getAdjacentTiles(world, x, y, z), world);

	}

	@Override
	public int getId() {
		return 18;
	}

	@Override
	public void initRecipes(SensorCard card) {
		GameRegistry.addRecipe(
			new ItemStack(card, 1, this.getId()),
				"rpr",
				"rrr",
				"aaa",
				'r', new ItemStack(Item.redstone),
				'a', new ItemStack(Item.paper),
				'p',ic2.api.Items.getItem("copperCableItem"));
	}

	@Override
	public boolean isDirectionalEnabled() {
		return false;
	}
	
}
