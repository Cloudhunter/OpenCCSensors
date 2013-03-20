package openccsensors.common.turtle;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dan200.computer.api.IHostedPeripheral;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.ITurtleUpgrade;
import dan200.turtle.api.TurtleSide;
import dan200.turtle.api.TurtleUpgradeType;
import dan200.turtle.api.TurtleVerb;
import openccsensors.OpenCCSensors;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensorEnvironment;
import openccsensors.common.block.BlockSensor;
import openccsensors.common.peripheral.PeripheralSensor;
import openccsensors.common.util.OCSLog;


public class TurtleUpgradeSensor implements ITurtleUpgrade {

	@Override
	public IHostedPeripheral createPeripheral(ITurtleAccess turtle,
			TurtleSide side) {
		return new PeripheralSensor(new TurtleSensorEnvironment(turtle), true);
	}

	@Override
	public String getAdjective() {
		String translation = LanguageRegistry.instance().getStringLocalization(
				"turtle.openccsensors.sensor.adjective");
		return translation == "" ? LanguageRegistry.instance()
				.getStringLocalization("turtle.openccsensors.sensor.adjective",
						"en_US") : translation;
	}

	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(OpenCCSensors.Blocks.sensorBlock);
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

	@Override
	public Icon getIcon(ITurtleAccess turtle, TurtleSide side) {
		return OpenCCSensors.Blocks.sensorBlock.turtleIcon;
	}

}