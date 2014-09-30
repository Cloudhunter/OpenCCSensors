package openccsensors.common.tileentity.basic;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import openccsensors.OpenCCSensors;
import openccsensors.api.IBasicSensor;
import openccsensors.common.sensor.ProximitySensor;

public class TileEntityBasicProximitySensor extends TileEntity implements IBasicSensor {

	private Vec3 blockPos = null;

	private double distance = Double.MIN_VALUE;
	private int previousOutput = Integer.MIN_VALUE;
	private int output = 0;

	private String owner = "NO OWNER";

	private boolean sentFirstChange = false;

	private int entityMode = ProximitySensor.MODE_ALL;

	@Override
	public void updateEntity() {

		super.updateEntity();

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

			if (output != previousOutput || !sentFirstChange) {
				sentFirstChange = true;
				flag = true;
			}

			previousOutput = output;

			if (flag) {

				Block blockId = OpenCCSensors.Blocks.basicSensorBlock;

				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, output, 3);
				worldObj.notifyBlockOfNeighborChange(xCoord, yCoord, zCoord, blockId);
				worldObj.notifyBlockOfNeighborChange(xCoord, yCoord - 1, zCoord, blockId);
				worldObj.notifyBlockOfNeighborChange(xCoord, yCoord + 1, zCoord, blockId);
				worldObj.notifyBlockOfNeighborChange(xCoord - 1, yCoord, zCoord, blockId);
				worldObj.notifyBlockOfNeighborChange(xCoord + 1, yCoord, zCoord, blockId);
				worldObj.notifyBlockOfNeighborChange(xCoord, yCoord, zCoord - 1, blockId);
				worldObj.notifyBlockOfNeighborChange(xCoord, yCoord, zCoord + 1, blockId);

			}
		}
	}

	public int getEntityMode() {
		return entityMode;
	}

	public void onBlockClicked(EntityPlayer player) {
		if (player.getCommandSenderName().equals(owner)) {
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
			player.addChatMessage(new ChatComponentText(String.format("Changing sensor mode to \"%s\"", modeMsg)));
		}
	}

	@Override
	public int getPowerOutput() {
		return output;
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
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		this.entityMode = nbttagcompound.getInteger("entityMode");
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
