package openccsensors.common.core;

import dan200.computer.api.IComputerAccess;
import openccsensors.common.api.IMethodCallback;

public class CallbackEventManager {
	
	public String[] getMethodNames()
	{
		return new String[] {};
	}
	
	public void registerCallback(IMethodCallback callback){
		
	}
	
	public int queueMethodCall(IComputerAccess computer, int method,
			Object[] arguments)
	{
		return 1;	
	}
	
	public void process(String successEventName, String errorEventName)
	{
		
	}
}
