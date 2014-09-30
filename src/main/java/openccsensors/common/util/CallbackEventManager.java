package openccsensors.common.util;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import openccsensors.api.IMethodCallback;
import dan200.computercraft.api.peripheral.IComputerAccess;

public class CallbackEventManager {

	private int callId = 0;

	public ConcurrentLinkedQueue<MethodCallItem> callQueue = new ConcurrentLinkedQueue<MethodCallItem>();

	public ArrayList<IMethodCallback> callbacks = new ArrayList<IMethodCallback>();

	/**
	 * Grab all the method names of all the callbacks in the system. These are
	 * the methods exposed to the public LUA api
	 */
	public String[] getMethodNames() {
		int len = callbacks.size();
		String[] names = new String[len];
		for (int i = 0; i < len; i++) {
			names[i] = callbacks.get(i).getMethodName();
		}
		return names;
	}

	/**
	 * Register a new callback. This should only be called at the start when
	 * your setting up the peripheral callbacks
	 * 
	 * @TODO: add a thing to deny more callbacks
	 */
	public void registerCallback(IMethodCallback callback) {
		callbacks.add(callback);
	}

	/**
	 * This adds a method call to the queue ready for the next tick. This should
	 * generally by called directly from callMethod in IHostedPeripheral. It
	 * assigns an id to the MethodCallId which lets us keep track of the event
	 * as it passes through
	 */
	public int queueMethodCall(IComputerAccess computer, int method,
			Object[] arguments) {

		callId++;
		if (callId > 1000) {
			callId = 0;
		}
		callQueue.add(new MethodCallItem(callId, computer, method, arguments));
		return callId;
	}

	/**
	 * Loop through all of the items in the event method call queue and if
	 * they're successful we throw a happy event, if they fail we throw a sad
	 * event!
	 */
	public void process() {

		MethodCallItem item = callQueue.poll();

		while (item != null) {

			try {

				IMethodCallback callback = callbacks.get(item.getMethodId());

				Object[] response = new Object[] {
						item.getCallId(),
						callback.execute(item.getComputer(),
								item.getArguments()) };

				item.getComputer().queueEvent("ocs_success", response);

			} catch (Exception e) {
				try {
					item.getComputer().queueEvent("ocs_error",
							new Object[] { item.getCallId(), e.getMessage() });
				}catch(Exception errorerror) {
					// the computer got destroyed?
				}

			}

			item = callQueue.poll();
		}
	}
}
