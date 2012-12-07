package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

import openccsensors.OpenCCSensors;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.SensorCard;
import openccsensors.common.sensors.TargetRetriever;

public class ProximitySensorInterface implements ISensorInterface {

	private TargetRetriever retriever = new TargetRetriever();
	private final double sensingRadius = 16.0F;

	public ProximitySensorInterface() {
		retriever.registerTarget(EntityLiving.class, new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				return new LivingTarget((EntityLiving) entity, sx, sy, sz);
			}
		});
	}

	@Override
	public String getName() {
		return "openccsensors.item.proximitysensor";
	}

	@Override
	public String[] getMethods() {
		return new String[] { "getDirectional", "setDirectional" };
	}

	@Override
	public Object[] callMethod(ISensorAccess sensor, int methodID, Object[] args) throws Exception {
		switch(methodID)
		{
		case 0:
			return new Object[]{ sensor.isDirectional() };
		case 1:
			if ((args.length > 0) && (args[0] instanceof Boolean))
				sensor.setDirectional((Boolean)args[0]);
			break;
		default:
			break;
		}
		return new Object[]{};
	}

	@Override
	public Map getBasicTarget(World world, int x, int y, int z)
			throws Exception {

		HashMap targets;
		targets = retriever.getLivingEntities(world, x, y, z, sensingRadius);
		
		return TargetHelper.getBasicInformationForTargets(
				targets,
				world);

	}

	@Override
	public Map getTargetDetails(World world, int x, int y, int z, String target)
			throws Exception {

		return TargetHelper.getDetailedInformationForTarget(target,
				retriever.getLivingEntities(world, x, y, z, sensingRadius),
				world);

	}

	@Override
	public int getId() {
		return 17;
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
				'p',new ItemStack(Block.pressurePlateStone));
	}

}
