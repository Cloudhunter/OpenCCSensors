package openccsensors.api;

import dan200.computercraft.api.peripheral.IComputerAccess;

public interface IMethodCallback {
	public String getMethodName();
	public Object execute(IComputerAccess item, Object[] args) throws Exception;
}