package openccsensors.common.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IPeripheral;
import openccsensors.api.IGaugeSensor;
import openccsensors.api.IMethodCallback;
import openccsensors.common.util.CallbackEventManager;
import openccsensors.common.util.OCSLog;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityGauge extends TileEntity implements IPeripheral {
	
	
	private static ArrayList<IGaugeSensor> gaugeSensors = new ArrayList<IGaugeSensor>();
	public static void addGaugeSensor(IGaugeSensor sensor) {
		gaugeSensors.add(sensor);
	}
	public static ArrayList<IGaugeSensor> getGaugeSensors() {
		return gaugeSensors;
	}
	
	private HashMap tileProperties = new HashMap();
	private CallbackEventManager eventManager = new CallbackEventManager();
	private int percentage = 0;
	private String updatePropertyName = "";
	
	private int lastBroadcast = 1;
	
	public TileEntityGauge() {
		eventManager.registerCallback(new IMethodCallback() {

			@Override
			public String getMethodName() {
				return "getPercentage";
			}
			
			@Override
			public Object execute(IComputerAccess computer, Object[] arguments)
					throws Exception {
				return getPercentage();
			}

		});

		eventManager.registerCallback(new IMethodCallback() {

			@Override
			public String getMethodName() {
				return "setTrackedProperty";
			}
			
			@Override
			public Object execute(IComputerAccess computer, Object[] arguments)
					throws Exception {
				updatePropertyName = (String)arguments[0];
				return null;
			}

		});
	}

	@Override
	public Packet getDescriptionPacket() {
		Packet132TileEntityData packet = new Packet132TileEntityData();
		packet.actionType = 0;
		packet.xPosition = xCoord;
		packet.yPosition = yCoord;
		packet.zPosition = zCoord;
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		packet.customParam1 = nbt;
		return packet;
	}
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		readFromNBT(pkt.customParam1);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		this.percentage = nbttagcompound.getInteger("percentage");
		this.updatePropertyName = nbttagcompound.getString("property");
		super.readFromNBT(nbttagcompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("percentage", this.percentage);
		nbttagcompound.setString("property", this.updatePropertyName);
		super.writeToNBT(nbttagcompound);
	}

	public int getFacing() {
		return (worldObj == null) ? 0 : this.getBlockMetadata();
	}
	
	public int getPercentage() {
		return percentage;
	}

	@Override
	public String getType() {
		return "gauge";
	}

	@Override
	public String[] getMethodNames() {
		return eventManager.getMethodNames();
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
	public void attach(IComputerAccess computer) {
		computer.mountFixedDir("ocs", "mods/OCSLua/lua", true, 0);
	}

	@Override
	public void detach(IComputerAccess computer) {
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		tileProperties.clear();
		if (this.worldObj != null && !worldObj.isRemote) {
			ForgeDirection infront = ForgeDirection.getOrientation(this.getFacing());
			ForgeDirection behind = infront.getOpposite();
			TileEntity behindTile = worldObj.getBlockTileEntity(xCoord + behind.offsetX, yCoord, zCoord + behind.offsetZ);
			if (behindTile != null) {
				for (IGaugeSensor gaugeSensor : gaugeSensors)  {
					if (gaugeSensor.isValidTarget(behindTile)) {
						HashMap details = gaugeSensor.getDetails(worldObj, behindTile, true);
						for (String property : gaugeSensor.getGaugeProperties()) {
							if (details.containsKey(property)) {
								tileProperties.put(property, details.get(property));
							}
						}
					}
				}
			}
			percentage = 0;
			if (tileProperties.size() > 0) {
				if (updatePropertyName == "" || !tileProperties.containsKey(updatePropertyName)) {
					Entry<String, Object> entry = (Entry<String, Object>) tileProperties.entrySet().iterator().next();
					updatePropertyName = entry.getKey();
				}
				percentage = ((Double) (tileProperties.get(updatePropertyName))).intValue();
			}
			if (lastBroadcast++ % 10 == 0) {
				lastBroadcast = 1;
				worldObj.markBlockForUpdate(xCoord,  yCoord,  zCoord);
			}
			eventManager.process();
		}
	}
}
