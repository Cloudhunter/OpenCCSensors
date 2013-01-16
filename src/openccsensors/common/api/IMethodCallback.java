package openccsensors.common.api;

import dan200.computer.api.IComputerAccess;

public interface IMethodCallback {
	public String getMethodName();

	public Object execute(IComputerAccess item, Object[] args) throws Exception;
}
