package openccsensors.common.sensors.vanilla;

import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.liquids.ITankContainer;
import openccsensors.OpenCCSensors;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.SensorCard;
import openccsensors.common.sensors.TargetRetriever;

public class LiquidSensorInterface implements ISensorInterface {

	private TargetRetriever retriever = new TargetRetriever();
	
	public LiquidSensorInterface ()
	{
		retriever.registerTarget(new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				if (entity instanceof ITankContainer)
				{
					return new LiquidTankTarget((TileEntity) entity);
				}
				return null;
			}
		});
	}
	
	@Override
	public String getName() {
		return "openccsensors.item.liquidsensor";
	}

	@Override
	public int getId() {
		return 20;
	}

	@Override
	public Map getBasicTarget(ISensorAccess sensor, World world, int x, int y, int z)
			throws Exception {

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
	public String[] getMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] callMethod(ISensorAccess sensor, int methodID, Object[] args) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
				'w',new ItemStack(Item.bucketWater));
	}

	@Override
	public boolean isDirectionalEnabled() {
		return false;
	}
}
