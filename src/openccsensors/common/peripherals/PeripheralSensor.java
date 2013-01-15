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
import openccsensors.common.api.MethodCallItem;
import openccsensors.common.api.SensorCardInterface;
import openccsensors.common.core.CallbackEventManager;
import openccsensors.common.core.ISensorEnvironment;
import openccsensors.common.items.ItemSensorCard;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IHostedPeripheral;

public class PeripheralSensor implements IHostedPeripheral, ISensorAccess {

	private ISensorEnvironment env;

	private boolean isTurtle;
	
	private CallbackEventManager eventManager = new CallbackEventManager();
	
	public PeripheralSensor(ISensorEnvironment _env, boolean _turtle) {
		
		env = _env;
		isTurtle = _turtle;
		
		/**
		 * These are the methods exposed to LUA
		 */
		eventManager.registerCallback(new IMethodCallback() {
			@Override
			public String getMethodName() {
				return "getTargets";
			}

			@Override
			public Object execute(IComputerAccess computer, Object[] arguments) throws Exception {
				
				SensorCardInterface sensorInterface = getSensorCardInterface();
				if (sensorInterface != null)
				{
					Vec3 vec = env.getLocation();
					return sensorInterface.getSensor().getSurroundingTargets(
						env.getWorld(),
						(int) vec.xCoord,
						(int) vec.yCoord,
						(int) vec.zCoord,
						sensorInterface.getSensorUpgrade()
					);
				}
				throw new Exception("Could not find a valid sensor card");
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
				//. returns queue id
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

	/*
	 * On every game tick we process any waiting events
	 */
	@Override
	public void update() {
		eventManager.process();
	}

	/**
	 * Get the sensor card interface for the current card
	 * @return
	 */
	public SensorCardInterface getSensorCardInterface()
	{
		if (env != null)
		{
			
			/*
			 * Get the stack (on turtles this'll be slot 16, on blocks
			 * it'll be the only slot
			 */
			ItemStack stack = env.getSensorCardStack();
			
			if (stack == null) return null;
			
			Item card = stack.getItem();
			
			/* If there's a card there and it's a sensor card.. */
			if (card != null && card instanceof ItemSensorCard)
			{
				/* Get the interface for the current damage value */
				return ((ItemSensorCard)card).getInterfaceForDamageValue(stack.getItemDamage());
			}
		}
		return null;
	}

}