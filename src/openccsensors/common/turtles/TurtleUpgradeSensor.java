package openccsensors.common.turtles;

import net.minecraft.item.ItemStack;
import openccsensors.OpenCCSensors;
import openccsensors.common.core.TurtleSensorEnvironment;
import openccsensors.common.peripherals.PeripheralSensor;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dan200.computer.api.IHostedPeripheral;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.ITurtleUpgrade;
import dan200.turtle.api.TurtleSide;
import dan200.turtle.api.TurtleUpgradeType;
import dan200.turtle.api.TurtleVerb;

public class TurtleUpgradeSensor implements ITurtleUpgrade {

	@Override
	public IHostedPeripheral createPeripheral(ITurtleAccess turtle,
			TurtleSide side) {
		return new PeripheralSensor(new TurtleSensorEnvironment(turtle), true);
	}

	@Override
	public String getAdjective() {
		String translation = LanguageRegistry.instance().getStringLocalization(
				"openccsensors.upgrade.adjective");
		return translation == "" ? LanguageRegistry.instance()
				.getStringLocalization("openccsensors.upgrade.adjective",
						"en_US") : translation;
	}

	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(OpenCCSensors.Blocks.sensorBlock);
	}

	@Override
	public int getIconIndex(ITurtleAccess turtle, TurtleSide side) {
		return 0;
	}

	@Override
	public String getIconTexture(ITurtleAccess turtle, TurtleSide side) {
		return "/openccsensors/resources/images/terrain.png";
	}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Peripheral;
	}

	@Override
	public int getUpgradeID() {
		return 180;
	}

	@Override
	public boolean isSecret() {
		return false;
	}

	@Override
	public boolean useTool(ITurtleAccess turtle, TurtleSide side,
			TurtleVerb verb, int direction) {
		return false;
	}

}
