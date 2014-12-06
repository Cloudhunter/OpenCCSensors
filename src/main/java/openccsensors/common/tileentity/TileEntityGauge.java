package openccsensors.common.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import openccsensors.OpenCCSensors;
import openccsensors.api.IGaugeSensor;
import openccsensors.api.IMethodCallback;
import openccsensors.common.util.CallbackEventManager;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.filesystem.IMount;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

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
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
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
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
		return new Object[] {
				eventManager.queueMethodCall(computer, method, arguments)
		};
	}

	@Override
	public void attach(IComputerAccess computer) {
		IMount mount = ComputerCraftAPI.createResourceMount(OpenCCSensors.class, "openccsensors", "openccsensors/mods/OCSLua/lua");
		computer.mount("ocs", mount);
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
			TileEntity behindTile = worldObj.getTileEntity(xCoord + behind.offsetX, yCoord, zCoord + behind.offsetZ);
			if (behindTile != null) {
				for (IGaugeSensor gaugeSensor : gaugeSensors)  {
					if (gaugeSensor.isValidTarget(behindTile)) {
						HashMap details = gaugeSensor.getDetails(worldObj, behindTile, new ChunkCoordinates(behindTile.xCoord, behindTile.yCoord, behindTile.zCoord), true);
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
					updatePropertyName = "";
					for (String property : new String[] {
							"HeatPercentage",
							"Progress",
							"StoredPercentage",
							"InventoryPercentFull"
					}) {
						if (updatePropertyName == "" && tileProperties.containsKey(property)) {
							updatePropertyName = property;
						}
					}
					if (updatePropertyName == "") {
						Entry<String, Object> entry = (Entry<String, Object>) tileProperties.entrySet().iterator().next();
						updatePropertyName = entry.getKey();
					}
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
	@Override
	public boolean equals(IPeripheral other) {
		// TODO Auto-generated method stub
		return false;
	}
}
