package openccsensors.common.sensors.vanilla;

import java.util.Map;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.src.Block;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.OpenCCSensors;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.TargetRetriever;

public class InventorySensorInterface implements ISensorInterface {

	private TargetRetriever retriever = new TargetRetriever();

	public InventorySensorInterface() {
		retriever.registerTarget(IInventory.class, new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				return new InventoryTarget((TileEntity) entity);
			}
		});

	}

	@Override
	public String getName() {
		return "openccsensors.item.inventorysensor";
	}

	@Override
	public String[] getMethods() {
		return null;
	}

	@Override
	public Object[] callMethod(int methodID, Object[] args) throws Exception {
		return null;
	}

	@Override
	public Map getBasicTarget(World world, int x, int y, int z)
			throws Exception {

		return TargetHelper.getBasicInformationForTargets(
				retriever.getAdjacentTiles(world, x, y, z, true), world);

	}

	@Override
	public Map getTargetDetails(World world, int x, int y, int z, String target)
			throws Exception {

		return TargetHelper.getDetailedInformationForTarget(target,
				retriever.getAdjacentTiles(world, x, y, z, true), world);

	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public void initRecipes() {
		GameRegistry.addRecipe(
				new ItemStack(OpenCCSensors.sensorCard, 1, this.getId()),
				"wwr", "wrr", "rrp", 'w', new ItemStack(Block.planks), 'r',
				new ItemStack(Item.redstone), 'p', new ItemStack(Item.paper));
	}

}