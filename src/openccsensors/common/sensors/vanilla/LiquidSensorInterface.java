package openccsensors.common.sensors.vanilla;

import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.liquids.ITankContainer;
import openccsensors.OpenCCSensors;
import openccsensors.common.api.ISensorAccess;
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
		return 20;
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
	public Object[] callMethod(ISensorAccess sensor, int methodID, Object[] args) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initRecipes() {
		GameRegistry.addRecipe(
				new ItemStack(OpenCCSensors.sensorCard, 1, this.getId()),
				"rwr",
				"rrr",
				"aaa",
				'r', new ItemStack(Item.redstone),
				'a', new ItemStack(Item.paper),
				'w',new ItemStack(Item.bucketWater));
	}

}
