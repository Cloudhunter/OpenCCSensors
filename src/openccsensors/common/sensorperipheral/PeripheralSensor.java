package openccsensors.common.sensorperipheral;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorCard;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.core.ISensorEnvironment;
import openccsensors.common.helper.SensorHelper;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IHostedPeripheral;

public class PeripheralSensor implements IHostedPeripheral, ISensorAccess {

	public class MethodCallQueueItem {
		public IComputerAccess computer;
		public int method;
		public Object[] arguments;
		public int methodCallId;

		public MethodCallQueueItem(int methodCallId, IComputerAccess computer,
				int method, Object[] arguments) {
			this.methodCallId = methodCallId;
			this.computer = computer;
			this.method = method;
			this.arguments = arguments;
		}
	}

	private ISensorEnvironment env;

	private ISensorInterface sensorCard;

	private ItemStack sensorItemStack;

	private boolean turtle;

	private boolean directional;

	public ConcurrentLinkedQueue<MethodCallQueueItem> callQueue = new ConcurrentLinkedQueue<MethodCallQueueItem>();

	private int methodCallId = 0;

	public PeripheralSensor(ISensorEnvironment _env, boolean _turtle) {
		env = _env;
		turtle = _turtle;
		directional = false;

	}

	@Override
	public void attach(IComputerAccess computer) {
		computer.mountFixedDir("ocs", "mods/OCSLua/lua", true, 0);
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, int method,
			Object[] arguments) throws Exception {
		methodCallId++;
		callQueue.add(new MethodCallQueueItem(methodCallId, computer, method,
				arguments));
		return new Object[] { methodCallId };
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
		return new String[] { "getTargets", "getTargetDetails",
				"getSensorName", "getSensorMethods", "sensorCardCall",
				"isDirectional", "setDirectional" };
	}

	private ISensorInterface getSensor(ItemStack itemstack) throws Exception {

		Item item = itemstack.getItem();
		if (item instanceof ISensorCard) {
			ISensorInterface sensor = ((ISensorCard) item).getSensorInterface(
					itemstack, turtle);

			if (sensor != null) {
				return sensor;
			}

		}

		return null;
	}

	@Override
	public ISensorEnvironment getSensorEnvironment() {
		return env;
	}

	@Override
	public String getType() {
		return "sensor";
	}

	public boolean isDirectional() {
		return directional;
	}

	@Override
	public boolean isTurtle() {
		return turtle;
	}

	private Object[] processQueueItem(MethodCallQueueItem item)
			throws Exception {
		IComputerAccess computer = item.computer;
		int method = item.method;
		Object[] arguments = item.arguments;
		if (sensorItemStack != env.getSensorCard()) {
			sensorItemStack = env.getSensorCard();
			sensorCard = null;
		}

		if (sensorItemStack == null) {
			return new Object[] { item.methodCallId, null };
		}
		
		if (sensorCard == null) {
			sensorCard = getSensor(sensorItemStack);
		}

		if (sensorCard == null) {
			return new Object[] { item.methodCallId, null };
		}

		Vec3 vec = env.getLocation();

		int cardMark = SensorHelper.getMark(
			sensorItemStack.getItemDamage()
		);
		
		switch (method) {
		case 0:
			return new Object[] {
					item.methodCallId,
					sensorCard.getBasicTarget(this, env.getWorld(),
							(int) vec.xCoord, (int) vec.yCoord,
							(int) vec.zCoord, cardMark) };
		case 1:
			if (arguments.length > 0) {
				if (arguments[0] instanceof String) {
					return new Object[] {
							methodCallId,
							sensorCard.getTargetDetails(this, env.getWorld(),
									(int) vec.xCoord, (int) vec.yCoord,
									(int) vec.zCoord, cardMark, arguments[0].toString()) };
				}
			}
			throw new Exception("Invalid arguments. Expected String.");
		case 2:
			return new Object[] { methodCallId, sensorCard.getName() };
		case 3:
			Map hash = new HashMap();  
			String[] methods = sensorCard.getMethods();
			for(int i = 0 ; i < methods.length; i++)
			{
			   hash.put(i, methods[i]);
			}
			return new Object[] { item.methodCallId, hash };
		case 4:
			if (arguments.length > 0) {
				if (arguments[0] instanceof String) {
					String[] sensorMethods = sensorCard.getMethods();
					int newMethod = Arrays.asList(sensorMethods).indexOf(
							arguments[0].toString());

					if (method < 0)
						throw new Exception("Invalid sensor command");

					Object[] newArray = new Object[arguments.length - 1];
					System.arraycopy(arguments, 1, newArray, 0,
							arguments.length - 1);

					return new Object[] { item.methodCallId,
							sensorCard.callMethod(this, env.getWorld(),
									(int) vec.xCoord, (int) vec.yCoord,
									(int) vec.zCoord, newMethod, newArray, cardMark) };
				}
			}
			throw new Exception("Invalid arguments. Expected String.");
		case 5:
			return new Object[] { this.isDirectional() };
		case 6:
			if (!sensorCard.isDirectionalEnabled())
				return new Object[] { item.methodCallId, false };
			if (arguments.length > 0) {
				if (arguments[0] instanceof Boolean) {
					this.setDirectional((Boolean) arguments[0]);
					return new Object[] { true };
				}
			}
			throw new Exception("Invalid arguments. Expected Boolean.");
		}

		return null;
	}

	@Override
	public void readFromNBT(NBTTagCompound paramNBTTagCompound) {
	}

	public void setDirectional(boolean isDirectional) {
		directional = isDirectional;
		Vec3 loc = env.getLocation();
		World world = env.getWorld();
		if (world != null) // can happen during loading
			world.markBlockForUpdate((int) loc.xCoord, (int) loc.yCoord,
					(int) loc.zCoord);
	}

	@Override
	public void update() {
		MethodCallQueueItem item = callQueue.poll();
		while (item != null) {
			try {

				Object[] response = processQueueItem(item);
				item.computer.queueEvent("ocs_response", response);
			} catch (Exception e) {

				e.printStackTrace();

			}
			item = callQueue.poll();
		}

	}

	@Override
	public void writeToNBT(NBTTagCompound paramNBTTagCompound) {
	}

}