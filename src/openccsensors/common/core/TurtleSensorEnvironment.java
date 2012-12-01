package openccsensors.common.core;

import dan200.turtle.api.ITurtleAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class TurtleSensorEnvironment
implements ISensorEnvironment
{
	
	ITurtleAccess turtle;

	public TurtleSensorEnvironment(ITurtleAccess _turtle)
	{
		turtle = _turtle;
	}
	
	@Override
	public World getWorld()
	{
		return turtle.getWorld();
	}

	@Override
	public Vec3 getLocation()
	{
		return turtle.getPosition();
	}
	
	public int getFacing()
	{
		return (turtle.getFacingDir()+1)%4;
	}

	@Override
	public ItemStack getSensorCard()
	{
		return turtle.getSlotContents(15);
	}
	
	

}
