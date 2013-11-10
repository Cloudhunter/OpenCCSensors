package openccsensors.common.turtle;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import openccsensors.OpenCCSensors;
import openccsensors.common.peripheral.PeripheralSensor;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dan200.computer.api.IHostedPeripheral;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.ITurtleUpgrade;
import dan200.turtle.api.TurtleSide;
import dan200.turtle.api.TurtleUpgradeType;
import dan200.turtle.api.TurtleVerb;


public class TurtleUpgradeSensor implements ITurtleUpgrade {

	public TurtleUpgradeSensor() {
	}
	
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
	
	public void addTurtlesToCreative(List subItems) {
		for (int i = 0; i <= 7; i++) {
			ItemStack turtle = GameRegistry.findItemStack("CCTurtle", "CC-TurtleExpanded", 1);
			if (turtle != null)
			{
					NBTTagCompound tag = turtle.getTagCompound();
					if (tag == null)
					{
						tag = new NBTTagCompound();
						turtle.writeToNBT(tag);
					}
					tag.setShort("leftUpgrade", (short) getUpgradeID());
					tag.setShort("rightUpgrade", (short) i);
					turtle.setTagCompound(tag);
					subItems.add(turtle);
				}
		}
	}

}