package openccsensors.common.tileentities;

import openccsensors.common.ISensorEnvironment;
import openccsensors.common.core.SensorPeripheral;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IPeripheral;
import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class TileEntitySensor extends TileEntity
implements ISensorEnvironment, IPeripheral
{
	private IPeripheral peripheral;
	
	// IPeripheral interface - basically a proxy to the SensorPeripheral, allowing us to reuse code for the turtle peripheral
	
	public TileEntitySensor()
	{
		peripheral = new SensorPeripheral(this);
		
	}

	@Override
	public String getType() 
	{
		return peripheral.getType();
	}

	@Override
	public String[] getMethodNames() 
	{
		return peripheral.getMethodNames();
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, int method, Object[] arguments) throws Exception 
	{
		return peripheral.callMethod(computer, method, arguments);
	}

	@Override
	public boolean canAttachToSide(int side) 
	{
		return peripheral.canAttachToSide(side);
	}

	@Override
	public void attach(IComputerAccess computer, String computerSide) 
	{
		peripheral.attach(computer, computerSide);
		
	}

	@Override
	public void detach(IComputerAccess computer) 
	{
		peripheral.detach(computer);
		
	}

	// ISensorEnvironment interface implementation - again will allow us to work on both turtles and our own block
	@Override
	public World getWorld()
	{
		return worldObj;
	}

	@Override
	public Vec3 getLocation()
	{
		return Vec3.createVectorHelper(xCoord, yCoord, zCoord);
	}
}
