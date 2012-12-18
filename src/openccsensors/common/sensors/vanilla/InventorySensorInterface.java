package openccsensors.common.sensors.vanilla;

import java.util.Map;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import openccsensors.OpenCCSensors;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.SensorCard;
import openccsensors.common.sensors.TargetRetriever;

public class InventorySensorInterface implements ISensorInterface {

	private TargetRetriever retriever = new TargetRetriever();

	public InventorySensorInterface() {
		retriever.registerTarget(new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				if (entity instanceof IInventory)
				{
					return new InventoryTarget((TileEntity) entity);
				}
				return null;
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
	public Object[] callMethod(ISensorAccess sensor, int methodID, Object[] args) throws Exception {
		return null;
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
	public int getId() {
		return 16;
	}

	@Override
	public void initRecipes(SensorCard card) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
			new ItemStack(card, 1, this.getId()),
			new Object[] {
				"rpr",
				"rrr",
				"aaa",
				Character.valueOf('r'), new ItemStack(Item.redstone),
				Character.valueOf('a'), new ItemStack(Item.paper),
				Character.valueOf('p'), "plankWood"					
			}
		));
	}

	@Override
	public boolean isDirectionalEnabled() {
		return false;
	}

}