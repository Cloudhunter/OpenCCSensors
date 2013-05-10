package openccsensors.common.tileentity.basic;

import openccsensors.OpenCCSensors;
import openccsensors.api.IBasicSensor;
import openccsensors.common.sensor.ProximitySensor;
import openccsensors.common.util.OCSLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityBasicProximitySensor extends TileEntity implements IBasicSensor {

	private Vec3 blockPos = null;

	private double distance = Double.MIN_VALUE;
	private int previousOutput = Integer.MIN_VALUE;
	private int output = 0;

	private String owner = "NO OWNER";

	private int entityMode = ProximitySensor.MODE_ALL;

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			boolean flag = false;

			if (blockPos == null) {
				blockPos = Vec3.createVectorHelper(xCoord, yCoord, zCoord);
			}

			distance = OpenCCSensors.Sensors.proximitySensor.getDistanceToNearestEntity(
					worldObj,
					blockPos,
					entityMode,
					owner
			);

			output = MathHelper.clamp_int(15 - ((Double) distance).intValue(), 0, 15);

			if (output != previousOutput) {
				flag = true;
			}

			previousOutput = output;

			if (flag) {

				int blockId = OpenCCSensors.Config.basicSensorBlockID;
				worldObj.notifyBlockChange(xCoord, yCoord, zCoord, blockId);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, blockId);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord + 1, zCoord, blockId);
				worldObj.notifyBlocksOfNeighborChange(xCoord - 1, yCoord, zCoord, blockId);
				worldObj.notifyBlocksOfNeighborChange(xCoord + 1, yCoord, zCoord, blockId);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord - 1, blockId);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord + 1, blockId);

			}
		}
	}
	
	public int getEntityMode() {
		return entityMode;
	}
	
	public void onBlockClicked(EntityPlayer player) {
		if (player.getEntityName().equals(owner)) {
			entityMode++;
			if (entityMode > 2) {
				entityMode = 0;
			}
			String modeMsg = "";
			switch(entityMode) {
			case ProximitySensor.MODE_ALL:
				modeMsg = "Any Living Entity";
				break;
			case ProximitySensor.MODE_PLAYERS:
				modeMsg = "Any Player";
				break;
			case ProximitySensor.MODE_OWNER:
				modeMsg = "Owner Only";
				break;
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			player.sendChatToPlayer(String.format("Changing sensor mode to \"%s\"", modeMsg));
		}
	}

	@Override
	public int getPowerOutput() {
		return output;
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
		this.entityMode = nbttagcompound.getInteger("entityMode");
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
		this.owner = nbttagcompound.getString("owner");
		super.readFromNBT(nbttagcompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("entityMode", this.entityMode);
		nbttagcompound.setString("owner", this.owner);
		super.writeToNBT(nbttagcompound);
	}

	public void setOwner(String _owner) {
		this.owner = _owner;
	}
}
