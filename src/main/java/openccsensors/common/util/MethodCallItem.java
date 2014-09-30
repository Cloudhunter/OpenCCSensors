package openccsensors.common.util;

import dan200.computercraft.api.peripheral.IComputerAccess;

public class MethodCallItem {

	private IComputerAccess computer;
	private int methodId;
	private int callId;
	private Object[] arguments;

	public MethodCallItem(int callId, IComputerAccess computer, int methodId,
			Object[] arguments) {
		this.computer = computer;
		this.methodId = methodId;
		this.callId = callId;
		this.arguments = arguments;
	}

	public IComputerAccess getComputer() {
		return computer;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public int getCallId() {
		return this.callId;
	}

	public int getMethodId() {
		return methodId;
	}
}
