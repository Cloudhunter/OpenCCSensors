package openccsensors.common.sensorperipheral;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;
import openccsensors.OpenCCSensors;
import openccsensors.common.core.OCSLog;
import openccsensors.common.core.TurtleSensorEnvironment;
import dan200.computer.api.IHostedPeripheral;
import dan200.computer.api.IPeripheral;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.ITurtleUpgrade;
import dan200.turtle.api.TurtleSide;
import dan200.turtle.api.TurtleUpgradeType;
import dan200.turtle.api.TurtleVerb;

public class TurtleUpgradeSensor implements ITurtleUpgrade
{

	@Override
	public int getUpgradeID() 
	{
		return 180;
	}

	@Override
	public String getAdjective() 
	{
		String translation = LanguageRegistry.instance().getStringLocalization("openccsensors.upgrade.adjective");
		return translation == "" ? LanguageRegistry.instance().getStringLocalization("openccsensors.upgrade.adjective", "en_US") : translation;
	}

	@Override
	public TurtleUpgradeType getType() 
	{
		return TurtleUpgradeType.Peripheral;
	}

	@Override
	public ItemStack getCraftingItem() 
	{
		return new ItemStack(OpenCCSensors.Blocks.sensorBlock);
	}

	@Override
	public boolean isSecret() 
	{
		return false;
	}

	@Override
	public String getIconTexture(ITurtleAccess turtle, TurtleSide side) 
	{
		return "/openccsensors/resources/images/terrain.png";
	}

	@Override
	public int getIconIndex(ITurtleAccess turtle, TurtleSide side)
	{
		if (turtle != null && side != null) {
			IPeripheral peripheral = turtle.getPeripheral(side);
			if (peripheral != null) {
				PeripheralSensor sensor = (PeripheralSensor)turtle.getPeripheral(side);
				if (sensor != null)
				{
					return sensor.isDirectional() ? 1 : 0;
				}
			}
		}
		return 0;
	}

	@Override
	public IHostedPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) 
	{
		return new PeripheralSensor(new TurtleSensorEnvironment(turtle), true);
	}

	@Override
	public boolean useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction)
	{
		return false;
	}

}
