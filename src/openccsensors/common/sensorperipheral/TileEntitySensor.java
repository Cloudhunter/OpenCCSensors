package openccsensors.common.sensorperipheral;

import openccsensors.common.core.ISensorEnvironment;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IPeripheral;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.InventoryBasic;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet132TileEntityData;
import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class TileEntitySensor extends TileEntity
implements ISensorEnvironment, IPeripheral, IInventory
{
	private PeripheralSensor peripheral;

	private IInventory inventory;
	
	private float rotation;
	private final static float rotationSpeed = 3.0F; //degrees per tick
	
	public TileEntitySensor()
	{
		peripheral = new PeripheralSensor(this, false);
		inventory = new InventoryBasic("Sensor", 1);
		rotation = 0;
	}
	
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);

		NBTTagCompound item = nbttagcompound.getCompoundTag("item");
		inventory.setInventorySlotContents(0, ItemStack.loadItemStackFromNBT(item));
		setDirectional(nbttagcompound.getBoolean("directional"));
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);

        NBTTagCompound item = new NBTTagCompound();
        
        ItemStack sensorStack = inventory.getStackInSlot(0);
        
		if( sensorStack != null )
		{
			sensorStack.writeToNBT(item);
        }
        nbttagcompound.setTag("item", item);
        
        nbttagcompound.setBoolean("directional", peripheral.isDirectional());
    }
    
    public int getFacing()
    {
    	return (worldObj == null) ? 0 : this.getBlockMetadata();
    }
    
    public float getRotation()
    {
    	return rotation;
    }
    
    @Override
    public void updateEntity()
    {
    	super.updateEntity();
    	rotation = (rotation+rotationSpeed)%360;
    }
    
    @Override 
    public Packet getDescriptionPacket()
    {
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
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
    	readFromNBT(pkt.customParam1);
    }
    
    // IPeripheral interface - basically a proxy to the SensorPeripheral, allowing us to reuse code for the turtle peripheral

    public boolean isDirectional()
    {
    	return peripheral.isDirectional();
    }
    
    public void setDirectional(boolean isDirectional)
    {
    	peripheral.setDirectional(isDirectional);    	
    }
    
	@Override
	public String getType() 
	{
		return peripheral.getType();
	}

	@Override
	public String[] getMethodNames() 
	{
		return peripheral.getMethodNames();
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, int method, Object[] arguments) throws Exception 
	{
		return peripheral.callMethod(computer, method, arguments);
	}

	@Override
	public boolean canAttachToSide(int side) 
	{
		return peripheral.canAttachToSide(side);
	}

	@Override
	public void attach(IComputerAccess computer, String computerSide) 
	{
		peripheral.attach(computer, computerSide);
		
	}

	@Override
	public void detach(IComputerAccess computer) 
	{
		peripheral.detach(computer);
		
	}

	// ISensorEnvironment interface implementation - again will allow us to work on both turtles and our own block
	@Override
	public World getWorld()
	{
		return worldObj;
	}

	@Override
	public Vec3 getLocation()
	{
		return Vec3.createVectorHelper( xCoord, yCoord, zCoord );
	}
	
	public ItemStack getSensorCard()
	{
		return getStackInSlot(0);
	}

	// IInventory implementation - but we delegate to the BasicInventory we created
	@Override
	public int getSizeInventory()
	{
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int var1)
	{
		return inventory.getStackInSlot(var1);
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2)
	{
		return inventory.decrStackSize(var1, var2);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return inventory.getStackInSlotOnClosing(var1);
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2)
	{
		inventory.setInventorySlotContents(var1, var2);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		
	}

	@Override
	public String getInvName() {
		return inventory.getInvName();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return inventory.isUseableByPlayer(var1);
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}
	
}
