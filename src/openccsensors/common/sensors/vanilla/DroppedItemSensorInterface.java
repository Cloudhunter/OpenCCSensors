package openccsensors.common.sensors.vanilla;

import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.retrievers.EntityRetriever;
import openccsensors.common.retrievers.IEntityValidatorCallback;
import openccsensors.common.sensors.SensorCard;
import cpw.mods.fml.common.registry.GameRegistry;

public class DroppedItemSensorInterface implements ISensorInterface {

	private EntityRetriever retriever = new EntityRetriever();
	private final double sensingRadius = 16.0F;

	public DroppedItemSensorInterface() {
		retriever.registerTarget(new IEntityValidatorCallback() {
			@Override
			public ISensorTarget getTargetIfValid(Entity entity, double relativeX, double relativeY, double relativeZ, int sx, int sy, int sz) {
				if (entity instanceof EntityItem && ((Entity)entity).isEntityAlive())
				{
					return new DroppedItemTarget((Entity) entity, sx, sy, sz);
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
				retriever.getSphere(world, x, y, z,  sensingRadius), world);

	}

	@Override
	public int getId() {
		return 22;
	}

	@Override
	public String[] getMethods() {
		return null;
	}

	@Override
	public String getName() {
		return "openccsensors.item.droppeditemsensor";
	}

	@Override
	public Map getTargetDetails(ISensorAccess sensor, World world, int x, int y, int z, String target)
			throws Exception {
		return TargetHelper.getDetailedInformationForTarget(target,
				retriever.getSphere(world, x, y, z, sensingRadius), world);

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
				'p',new ItemStack(Item.slimeBall));
	}

	@Override
	public boolean isDirectionalEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}