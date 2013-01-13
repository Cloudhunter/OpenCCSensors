package openccsensors.common.sensors.buildcraft;

import java.util.Map;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.OpenCCSensors;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.SensorCard;
import openccsensors.common.sensors.TargetRetriever;
import buildcraft.api.power.IPowerReceptor;

public class BuildCraftSensorInterface implements ISensorInterface {

	private TargetRetriever retriever = new TargetRetriever();

	public BuildCraftSensorInterface() {
		retriever.registerTarget(new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				if (entity instanceof IPowerReceptor)
				{
					return new PowerReceptorTarget((TileEntity) entity);
				}
				return null;
			}
		});
	}

	@Override
	public String getName() {
		return "openccsensors.item.buildcraftsensor";
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
		return 19;
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
