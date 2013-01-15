package openccsensors.common.api;

import dan200.computer.api.IComputerAccess;

public class MethodCallItem {
	
	private IComputerAccess computer;
	private int methodId;
	private int callId;
	
	public MethodCallItem(int callId, IComputerAccess computer, int methodId,
			Object[] arguments)
	{
		this.computer = computer;
		this.methodId = methodId;
		this.callId = callId;
	}
	public IComputerAccess getComputer()
	{
		return computer;
	}
	
	public int getCallId()
	{
		return this.callId;
	}
	
	public int getMethodId()
	{
		return methodId;
	}
}
