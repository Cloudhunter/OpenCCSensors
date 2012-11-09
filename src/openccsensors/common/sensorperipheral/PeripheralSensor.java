package openccsensors.common.sensorperipheral;

import openccsensors.common.core.ISensorEnvironment;
import net.minecraft.src.Vec3;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IPeripheral;
import dan200.turtle.api.ITurtlePeripheral;

public class PeripheralSensor
implements ITurtlePeripheral
{
	
	private ISensorEnvironment env;

	public PeripheralSensor(ISensorEnvironment _env) 
	{
		env = _env;
	}

	@Override
	public String getType() 
	{
		return "sensor";
	}

	@Override
	public String[] getMethodNames() 
	{
		return new String[] { "test", "getPos", "printSlot" };
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, int method, Object[] arguments) throws Exception 
	{
		switch (method)
		{
			case 0:
				return new Object[]{ "Test" };
			case 1:
				Vec3 vec = env.getLocation();
				return new Object[]{vec.xCoord, vec.yCoord, vec.zCoord};
			case 2:
				return new Object[]{env.getSensorCard().toString()};
		}
		
		return null;
	}

	@Override
	public boolean canAttachToSide(int side) 
	{
		return true;
	}

	@Override
	public void attach(IComputerAccess computer, String computerSide) 
	{

	}

	@Override
	public void detach(IComputerAccess computer) 
	{
		
	}

	@Override
	public void update() {
	}

}
