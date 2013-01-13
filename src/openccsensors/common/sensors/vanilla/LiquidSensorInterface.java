package openccsensors.common.sensors.vanilla;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.liquids.ITankContainer;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.retrievers.ITileEntityValidatorCallback;
import openccsensors.common.retrievers.TileEntityRetriever;
import openccsensors.common.sensors.SensorCard;
import cpw.mods.fml.common.registry.GameRegistry;

public class LiquidSensorInterface implements ISensorInterface {

	private TileEntityRetriever retriever = new TileEntityRetriever();
	
	public LiquidSensorInterface ()
	{
		retriever.registerTarget(new ITileEntityValidatorCallback() {
			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity, int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof ITankContainer)
				{
					return new LiquidTankTarget(entity, relativeX, relativeY, relativeZ);
				}
				return null;
			}
		});
	}
	
	@Override
	public Map callMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args) throws Exception {
		// TODO Auto-generated method stub
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
		return 20;
	}

	@Override
	public String[] getMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "openccsensors.item.liquidsensor";
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
				"rwr",
				"rrr",
				"aaa",
				'r', new ItemStack(Item.redstone),
				'a', new ItemStack(Item.paper),
				'w',new ItemStack(Item.bucketEmpty));
	}

	@Override
	public boolean isDirectionalEnabled() {
		return false;
	}
}
