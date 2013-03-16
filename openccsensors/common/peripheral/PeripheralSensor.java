package openccsensors.common.peripheral;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import openccsensors.api.IMethodCallback;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorAccess;
import openccsensors.api.ISensorEnvironment;
import openccsensors.api.SensorCard;
import openccsensors.common.item.ItemSensorCard;
import openccsensors.common.util.CallbackEventManager;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IHostedPeripheral;

public class PeripheralSensor implements IHostedPeripheral, ISensorAccess {

	private ISensorEnvironment env;

	private boolean isTurtle;

	private CallbackEventManager eventManager = new CallbackEventManager();

	// forgive me.
	public ISensorAccess getThis() {
		return this;
	}

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
			public Object execute(IComputerAccess computer, Object[] arguments)
					throws Exception {
	
				SensorCard card = getSensorCard();
				if (card != null) {
					
					Vec3 location = env.getLocation();

					ISensor sensor = card.getSensor();

					if (sensor != null) {

						HashMap<String, Object> targets = sensor.getTargets(env.getWorld(), location, card.getTier());
						
						for(Entry entry : targets.entrySet()) {
							entry.setValue(sensor.getDetails(env.getWorld(), entry.getValue(), false));
						}
						
						return targets;

					}
					

					throw new Exception(
							"There was a problem with your sensor card. Please report details on the OpenCCSensors bug tracker");
				}
				throw new Exception("Could not find a valid sensor card");

			}

		});

		eventManager.registerCallback(new IMethodCallback() {

			@Override
			public String getMethodName() {
				return "getTargetDetails";
			}

			@Override
			public Object execute(IComputerAccess computer, Object[] arguments)
					throws Exception {

				if (arguments.length != 1 || !(arguments[0] instanceof String)) {
					throw new Exception(
							"getTargetDetails takes just one argument, which should be the name of the target you're trying to retrieve");
				}

				String targetName = (String) arguments[0];
				
				SensorCard card = getSensorCard();
				
				if (card == null) {
					throw new Exception("Could not find a valid sensor card");
				}
				
				Vec3 location = env.getLocation();
				ISensor sensor = card.getSensor();
				HashMap<String, Object> targets = sensor.getTargets(env.getWorld(), location, card.getTier());
				
				if (!targets.containsKey(targetName)) {
					return null;
				}
				
				return sensor.getDetails(env.getWorld(), targets.get(targetName), true);
			}
		});

		eventManager.registerCallback(new IMethodCallback() {

			@Override
			public String getMethodName() {
				return "getSensorName";
			}

			@Override
			public Object execute(IComputerAccess computer, Object[] arguments)
					throws Exception {
				
				if (arguments.length > 0) {
					throw new Exception(
							"getSensorName does not take any arguments");
				}
				
				SensorCard sensorCard = getSensorCard();
				if (sensorCard == null) {
					return null;
				}
				
				return sensorCard.getSensor().getName();
			}
		});

		eventManager.registerCallback(new IMethodCallback() {

			@Override
			public String getMethodName() {
				return "getSensorMethods";
			}

			@Override
			public Object execute(IComputerAccess item, Object[] args)
					throws Exception {

				SensorCard sensorCard = getSensorCard();
				
				HashMap methods = new HashMap();

				if (sensorCard == null) {
					throw new Exception("Card not found");
				}

				ISensor sensor = sensorCard.getSensor();
				String[] customMethods = sensor.getCustomMethods(sensorCard.getTier());
				
				if (customMethods == null) {
					return null;
				}
				
				for(int i = 0 ; i < customMethods.length; i++)
				{
				   methods.put(i + 1, customMethods[i]);
				}
				return methods;
			}
		});

		eventManager.registerCallback(new IMethodCallback() {

			@Override
			public String getMethodName() {
				return "sensorCardCall";
			}

			@Override
			public Object execute(IComputerAccess item, Object[] args)
					throws Exception {
				
				SensorCard sensorCard = getSensorCard();

				if (sensorCard == null) {
					throw new Exception("Card not found");
				}

				ISensor sensor = sensorCard.getSensor();

				if (args.length < 1 || !(args[0] instanceof String)) {
					throw new Exception("Invalid arguments. Expected String.");
				}
				
				String[] sensorMethods = sensor.getCustomMethods(sensorCard.getTier());
				
				if (sensorMethods == null) {
					return null;
				}
				
				int methodId = Arrays.asList(sensorMethods).indexOf(args[0].toString());

				if (methodId < 0)
					throw new Exception("Invalid sensor command");

				Object[] arguments = new Object[args.length - 1];
				System.arraycopy(args, 1, arguments, 0, args.length - 1);
				Vec3 vec = env.getLocation();

				return sensor.callCustomMethod(
					env.getWorld(),
					env.getLocation(),
					methodId,
					arguments, sensorCard.getTier()
				);
				
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
		// . returns queue id
		eventManager.queueMethodCall(computer, method, arguments) };
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
	 * 
	 * @return
	 */
	public SensorCard getSensorCard() {
		if (env != null) {
			ItemStack stack = env.getSensorCardStack();

			if (stack == null)
				return null;

			Item card = stack.getItem();
			if (card != null && card instanceof ItemSensorCard) {
				return ((ItemSensorCard)card).getSensorCard(stack);
			}
		}
		return null;
	}
}