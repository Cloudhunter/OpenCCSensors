package openccsensors.common.sensors.industrialcraft;

import ic2.api.IEnergyStorage;
import ic2.api.IReactor;
import ic2.api.IReactorChamber;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.retrievers.ITileEntityValidatorCallback;
import openccsensors.common.retrievers.TileEntityRetriever;
import openccsensors.common.sensors.SensorCard;
import cpw.mods.fml.common.registry.GameRegistry;

public class IC2SensorInterface implements ISensorInterface {

	
	private TileEntityRetriever retriever = new TileEntityRetriever();

	public final static String MASS_FAB_CLASS = "ic2.core.block.machine.tileentity.TileEntityMatter";
	
	public IC2SensorInterface() {
		retriever.registerCallback(new ITileEntityValidatorCallback() {
			
			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity, int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof IReactor)
				{
					return new ReactorTarget(entity, relativeX, relativeY, relativeZ);
				}
				else if (entity instanceof IReactorChamber)
				{
					return new ReactorTarget(
							(TileEntity) ((IReactorChamber) entity).getReactor(), relativeX, relativeY, relativeZ);
				}
				return null;
			}
		});

		retriever.registerCallback(new ITileEntityValidatorCallback() {

			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity, int relativeX, int relativeY, int relativeZ) {
				
				if (entity instanceof TileEntity && entity.getClass().getName() == MASS_FAB_CLASS)
				{
					return new MassFabTarget(entity, relativeX, relativeY, relativeZ);
				}
				return null;
			}
			
		});
		
		retriever.registerCallback(new ITileEntityValidatorCallback() {
			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity, int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof IEnergyStorage)
				{
					return new EnergyStorageTarget(entity, relativeX, relativeY, relativeZ);
				}
				return null;
			}
		});


		retriever.registerCallback(new ITileEntityValidatorCallback() {
			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity, int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof IEnergySink ||
					entity instanceof IEnergySource ||
					entity instanceof IEnergyConductor)
				{
					return new EnergyConductorTarget(entity, relativeX, relativeY, relativeZ);
				}
				return null;
			}
		});
	}

	@Override
	public Map callMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args) throws Exception {
		return null;
	}

	@Override
	public Map getBasicTarget(ISensorAccess sensor, World world, int x, int y, int z) {
		return TargetHelper.getBasicInformationForTargets(
				retriever.getCube(world, x, y, z), world);
	}

	@Override
	public Map getTargetDetails(ISensorAccess sensor, World world, int x, int y, int z, String target)
			throws Exception {

		return TargetHelper.getDetailedInformationForTarget(target,
				retriever.getCube(world, x, y, z), world);

	}
	
	@Override
	public ISensorTarget getRelevantTargetForGauge(World world, int x, int y,
			int z) {
		return retriever.getFirstValidTargetForTile(world, x, y, z);
	}

	@Override
	public int getId() {
		return 18;
	}

	@Override
	public String[] getMethods() {
		return null;
	}

	@Override
	public String getName() {
		return "openccsensors.item.ic2sensor";
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
