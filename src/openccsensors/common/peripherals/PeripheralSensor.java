package openccsensors.common.peripherals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.common.api.IMethodCallback;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.SensorCardInterface;
import openccsensors.common.core.CallbackEventManager;
import openccsensors.common.core.ISensorEnvironment;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IHostedPeripheral;

public class PeripheralSensor implements IHostedPeripheral, ISensorAccess {

	private ISensorEnvironment env;

	private boolean isTurtle;
	
	private CallbackEventManager eventManager = new CallbackEventManager();

	public PeripheralSensor(ISensorEnvironment _env, boolean _turtle) {
		
		env = _env;
		isTurtle = _turtle;
		
		eventManager.registerCallback(new IMethodCallback() {
			@Override
			public String getMethodName() {
				return "getTargets";
			}
			@Override
			public void execute(int id, IComputerAccess computer, int method,
					Object[] arguments) {

				
			}
		});

	}

	@Override
	public void attach(IComputerAccess computer) {
		computer.mountFixedDir("ocs", "mods/OCSLua/lua", true, 0);
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, int method,
			Object[] arguments) throws Exception {
		
		return new Object[] { 
				eventManager.queueMethodCall(computer, method, arguments)
		};
	}

	@Override
	public boolean canAttachToSide(int side) {
		return true;
	}

	@Override
	public void detach(IComputerAccess computer) {

	}

	@Override
	public String[] getMethodNames() {
		return eventManager.getMethodNames();
	}


	@Override
	public ISensorEnvironment getSensorEnvironment() {
		return env;
	}

	@Override
	public String getType() {
		return "sensor";
	}

	@Override
	public boolean isTurtle() {
		return isTurtle;
	}

	@Override
	public void readFromNBT(NBTTagCompound paramNBTTagCompound) {
	}


	@Override
	public void writeToNBT(NBTTagCompound paramNBTTagCompound) {
	}

	@Override
	public void update() {
		
		eventManager.process("ocs_response", "ocs_error");
		
	}


}