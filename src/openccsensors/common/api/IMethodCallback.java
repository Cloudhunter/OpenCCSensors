package openccsensors.common.api;

import dan200.computer.api.IComputerAccess;

public interface IMethodCallback {
	public String getMethodName();
	public void execute(int id, IComputerAccess computer,
				int method, Object[] arguments);
}
