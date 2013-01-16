package openccsensors.common.blocks.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import openccsensors.common.api.IMethodCallback;
import openccsensors.common.core.CallbackEventManager;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IPeripheral;

public class TileEntityGauge extends TileEntity implements IPeripheral {

	private int percentage = 0;

	private CallbackEventManager eventManager = new CallbackEventManager();

	public TileEntityGauge() {
		eventManager.registerCallback(new IMethodCallback() {

			@Override
			public String getMethodName() {
				return "getPercentage";
			}

			@Override
			public Object execute(IComputerAccess computer, Object[] arguments)
					throws Exception {
				return percentage;
			}

		});
	}

	private void updatePercentage() {
		int x = 0;
		int z = 0;
		switch (getFacing()) {
		case 2:
			z = 1;
			break;
		case 5:
			x = -1;
			break;
		case 3:
			z = -1;
			break;
		case 4:
			x = 1;
			break;
		}
		this.percentage = 0;
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

	public int getFacing() {
		return (worldObj == null) ? 0 : this.getBlockMetadata();
	}

	public int getPercentage() {
		return percentage;
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		readFromNBT(pkt.customParam1);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		this.percentage = nbttagcompound.getInteger("percentage");
		super.readFromNBT(nbttagcompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("percentage", this.percentage);
		super.writeToNBT(nbttagcompound);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (!this.getWorldObj().isRemote) {
			updatePercentage();
		}

		eventManager.process();

		this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord,
				this.zCoord);

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

		return new Object[] { eventManager.queueMethodCall(computer, method,
				arguments) };
	}

	@Override
	public boolean canAttachToSide(int paramInt) {
		return true;
	}

	@Override
	public void attach(IComputerAccess paramIComputerAccess) {
	}

	@Override
	public void detach(IComputerAccess paramIComputerAccess) {
	}

}