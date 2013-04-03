package openccsensors.common.tileentity;

import openccsensors.api.ISensorEnvironment;
import openccsensors.common.peripheral.PeripheralSensor;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IPeripheral;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntitySensor extends TileEntity implements ISensorEnvironment,
IPeripheral, IInventory {

	private PeripheralSensor peripheral;

	private IInventory inventory;

	private float rotation;
	private final static float rotationSpeed = 3.0F;
	
	public TileEntitySensor() {
		peripheral = new PeripheralSensor(this, false);
		inventory = new InventoryBasic("Sensor", true, 1);
		rotation = 0;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	/* Tile entity overrides */
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		peripheral.update();
		rotation = (rotation + rotationSpeed) % 360;
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
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagCompound item = compound.getCompoundTag("item");
		inventory.setInventorySlotContents(0, ItemStack.loadItemStackFromNBT(item));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		NBTTagCompound item = new NBTTagCompound();
		ItemStack sensorStack = inventory.getStackInSlot(0);
		if (sensorStack != null) {
			sensorStack.writeToNBT(item);
		}
		nbttagcompound.setTag("item", item);
	}
	
	/* Inventory proxy methods */
	
	@Override
	public int getSizeInventory() {
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory.getStackInSlot(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return inventory.decrStackSize(i, j);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return inventory.getStackInSlotOnClosing(i);

	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory.setInventorySlotContents(i, itemstack);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public String getInvName() {
		return inventory.getInvName();
	}
	
	@Override
	public int getInventoryStackLimit() {
		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return inventory.isUseableByPlayer(entityplayer);
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}


	/* Peripheral proxy methods */
	
	@Override
	public String getType() {
		return peripheral.getType();
	}

	@Override
	public String[] getMethodNames() {
		return peripheral.getMethodNames();
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, int method,
			Object[] arguments) throws Exception {
		return peripheral.callMethod(computer, method, arguments);
	}

	@Override
	public boolean canAttachToSide(int side) {
		return peripheral.canAttachToSide(side);
	}

	@Override
	public void attach(IComputerAccess computer) {
		peripheral.attach(computer);
	}

	@Override
	public void detach(IComputerAccess computer) {
		peripheral.detach(computer);
	}

	@Override
	public int getFacing() {
		return (worldObj == null) ? 0 : this.getBlockMetadata();
	}

	@Override
	public Vec3 getLocation() {
		return Vec3.createVectorHelper(xCoord, yCoord, zCoord);
	}

	@Override
	public ItemStack getSensorCardStack() {
		return getStackInSlot(0);
	}

	@Override
	public World getWorld() {
		return worldObj;
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return true;
	}
}
