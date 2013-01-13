package openccsensors.common.sensors.buildcraft;

import java.util.Map;

import net.minecraft.block.Block;
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
import buildcraft.api.power.IPowerReceptor;
import cpw.mods.fml.common.registry.GameRegistry;

public class BuildCraftSensorInterface implements ISensorInterface {

	private TileEntityRetriever retriever = new TileEntityRetriever();

	public BuildCraftSensorInterface() {
		retriever.registerTarget(new ITileEntityValidatorCallback() {
			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity, int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof IPowerReceptor)
				{
					return new PowerReceptorTarget(entity, relativeX, relativeY, relativeZ);
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
	public Map getBasicTarget(ISensorAccess sensor, World world, int x, int y, int z)
			throws Exception {

		return TargetHelper.getBasicInformationForTargets(
				retriever.getCube(world, x, y, z), world);

	}

	@Override
	public int getId() {
		return 19;
	}

	@Override
	public String[] getMethods() {
		return null;
	}

	@Override
	public String getName() {
		return "openccsensors.item.buildcraftsensor";
	}

	@Override
	public Map getTargetDetails(ISensorAccess sensor, World world, int x, int y, int z, String target)
			throws Exception {

		return TargetHelper.getDetailedInformationForTarget(target,
				retriever.getCube(world, x, y, z), world);

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
				'p',new ItemStack(Block.lever));
	}

	@Override
	public boolean isDirectionalEnabled() {
		return false;
	}	
}
