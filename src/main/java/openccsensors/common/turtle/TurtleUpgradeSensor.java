package openccsensors.common.turtle;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import openccsensors.OpenCCSensors;
import openccsensors.common.peripheral.PeripheralSensor;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.turtle.TurtleCommandResult;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.api.turtle.TurtleUpgradeType;
import dan200.computercraft.api.turtle.TurtleVerb;

public class TurtleUpgradeSensor implements ITurtleUpgrade {

	private ArrayList<PeripheralSensor> peripheral = new ArrayList<PeripheralSensor>();
	
	public TurtleUpgradeSensor() {
	}
	
	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle,
			TurtleSide side) {
		this.peripheral.add(new PeripheralSensor(new TurtleSensorEnvironment(turtle), true));
		return this.peripheral.get(this.peripheral.size() - 1);
	}

	@Override
	public String getUnlocalisedAdjective() {
		return "turtle.openccsensors.sensor.adjective";
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
	public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side,
			TurtleVerb verb, int direction) {
		return TurtleCommandResult.failure();
	}

	@Override
	public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
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

	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {
		if (this.peripheral != null) {
			for (PeripheralSensor peripheral : this.peripheral) {
				if (peripheral != null) {
					peripheral.update();
				}
			}		
		} 	
	}

}