package openccsensors.common.peripherals;

import java.util.ArrayList;
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
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.MethodCallItem;
import openccsensors.common.api.SensorCardInterface;
import openccsensors.common.core.CallbackEventManager;
import openccsensors.common.core.ISensorEnvironment;
import openccsensors.common.core.OCSLog;
import openccsensors.common.exceptions.CardNotFoundException;
import openccsensors.common.exceptions.UnknownSensorCardException;
import openccsensors.common.helper.TargetHelper;
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
			public String getMethodName()
			{
				return "getTargets";
			}

			@Override
			public Object execute(IComputerAccess computer, Object[] arguments) throws Exception {
				
				SensorCardInterface sensorInterface = getSensorCardInterface();
				if (sensorInterface != null)
				{
					Vec3 vec = env.getLocation();

					ISensor sensor = sensorInterface.getSensor();
					
					if (sensor != null) {
					
						HashMap<String, ArrayList<ISensorTarget>> targets = sensor.getSurroundingTargets(
								env.getWorld(),
								(int) vec.xCoord,
								(int) vec.yCoord,
								(int) vec.zCoord,
								sensorInterface.getSensorUpgrade()
						);
						
						return TargetHelper.mergeSensorTargets(targets, env.getWorld());
					
					}
					
					throw new Exception("There was a problem with your sensor card. Please report details on the OpenCCSensors bug tracker");
				}
				throw new Exception("Could not find a valid sensor card");
			}
			
		});
		
		eventManager.registerCallback(new IMethodCallback() {
			
			@Override
			public String getMethodName()
			{
				return "getTargetDetails";
			}

			@Override
			public Object execute(IComputerAccess computer, Object[] arguments) throws Exception {
				
				if (arguments.length != 1 || !(arguments[0] instanceof String))
				{
					throw new Exception("getTargetDetails takes just one argument, which should be the name of the target you're trying to retrieve");
				}
				
				String targetName = (String)arguments[0];
				
				SensorCardInterface sensorInterface = getSensorCardInterface();
				if (sensorInterface != null)
				{
					Vec3 vec = env.getLocation();

					ISensor sensor = sensorInterface.getSensor();
					
					if (sensor != null) {
					
						HashMap<String, ArrayList<ISensorTarget>> targets = sensor.getSurroundingTargets(
								env.getWorld(),
								(int) vec.xCoord,
								(int) vec.yCoord,
								(int) vec.zCoord,
								sensorInterface.getSensorUpgrade()
						);
						
						if (!targets.containsKey(targetName)) {
							throw new Exception("Please specify a valid target name");
						}
						
						return TargetHelper.mergeTargetDetails(targets.get(targetName), env.getWorld());
					
					}
					
					throw new UnknownSensorCardException();
				}
				
				throw new CardNotFoundException(isTurtle);
			}
			
		});
		

		eventManager.registerCallback(new IMethodCallback() {
			
			@Override
			public String getMethodName()
			{
				return "getSensorName";
			}

			@Override
			public Object execute(IComputerAccess computer, Object[] arguments) throws Exception {
				
				if (arguments.length > 0)
				{
					throw new Exception("getSensorName does not take any arguments");
				}

				
				SensorCardInterface sensorInterface = getSensorCardInterface();
				
				if (sensorInterface != null)
				{

					return sensorInterface.getName();
				}
				
				throw new CardNotFoundException(isTurtle);
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