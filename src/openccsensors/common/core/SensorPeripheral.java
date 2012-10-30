package openccsensors.common.core;

import openccsensors.common.ISensorEnvironment;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IPeripheral;

public class SensorPeripheral
implements IPeripheral
{
	
	private ISensorEnvironment env;

	public SensorPeripheral(ISensorEnvironment _env) 
	{
		env = _env;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getMethodNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, int method,
			Object[] arguments) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canAttachToSide(int side) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void attach(IComputerAccess computer, String computerSide) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detach(IComputerAccess computer) {
		// TODO Auto-generated method stub
		
	}

}
