package openccsensors.common.core;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import dan200.turtle.api.ITurtleAccess;

public class TurtleSensorEnvironment implements ISensorEnvironment {

	ITurtleAccess turtle;

	public TurtleSensorEnvironment(ITurtleAccess _turtle) {
		turtle = _turtle;
	}

	@Override
	public int getFacing() {
		return (turtle.getFacingDir() + 1) % 4;
	}

	@Override
	public Vec3 getLocation() {
		return turtle.getPosition();
	}

	@Override
	public ItemStack getSensorCardStack() {
		return turtle.getSlotContents(15);
	}

	@Override
	public World getWorld() {
		return turtle.getWorld();
	}

}
