package openccsensors.common.gaugeperipheral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import openccsensors.common.core.OCSLog;
import openccsensors.common.sensors.industrialcraft.IC2SensorInterface;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.inventory.IInventory;

public class TileEntityGauge extends TileEntity {
	
	public class GaugeCallbackPair {
		public Class klazz;
		public IGaugeCallback callback;
		public GaugeCallbackPair(Class klazz, IGaugeCallback callback)
		{
			this.klazz = klazz;
			this.callback = callback;
		}
	}
	
    public static final String IC2_MASS_FAB_CLASSNAME = "ic2.core.block.machine.tileentity.TileEntityMatter";
    public static final String IC2_MFSU_CLASSNAME = "ic2.core.block.wiring.TileEntityElectricBlock";
    
    private double percentage = 0;
    
    private ArrayList<GaugeCallbackPair> monitors = null;
    
    public TileEntityGauge()
    {
    	//monitors = new HashMap<Class, IGaugeCallback>();
    }
    
    public double getPercentage()
    {
    	return percentage;
    }
    
    @Override
    public int getBlockMetadata()
    {
        if (this.blockMetadata == -1)
        {
            return 4;
        }

        return this.blockMetadata;
    }
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
    	this.percentage = nbttagcompound.getDouble("percentage");
        super.readFromNBT(nbttagcompound);
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {

    	nbttagcompound.setDouble("percentage", this.percentage);
        super.writeToNBT(nbttagcompound);
    }
        
    @Override
    public void updateEntity()
    {
    	updatePercentage();
        super.updateEntity();
    }
    
    private void updatePercentage()
    {

    	if (!this.worldObj.isRemote)
    	{

        	if (monitors == null) addMonitors();
        	
	    	TileEntity attachedTo = getAttachedTile();
	    	if (attachedTo != null)
	    	{
	        	IGaugeCallback callback = getAvailableCallbackForTileEntity(attachedTo);
		    	if (callback != null)
		    	{
		    		percentage = callback.getPercentage(attachedTo);
		    	}
	    	}
    	}
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
    private void addTileEntityMonitor(Class klazz, IGaugeCallback callback)
    {
    	monitors.add(new GaugeCallbackPair(klazz, callback));
    }
    private void addTileEntityMonitor(String className, IGaugeCallback callback)
    {
    	try
    	{
    		Class klazz = Class.forName(className);
    		if (klazz != null)
    		{
    			addTileEntityMonitor(klazz, callback);
    		}
    	}
    	catch(ClassNotFoundException exception)
    	{
    	}
    }
    
    private TileEntity getAttachedTile()
    {
    	int x = 0;
    	int z = 0;
    	switch (getFacing())
    	{
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
    	return this.getWorldObj().getBlockTileEntity(xCoord+x, yCoord, zCoord+z);
    }
    
    private IGaugeCallback getAvailableCallbackForTileEntity(TileEntity entity)
    {
    	for (int i=0; i<monitors.size(); i++)
    	{
    		GaugeCallbackPair monitor = monitors.get(i);
    		if (monitor.klazz.isAssignableFrom(entity.getClass()))
    		{
    			return monitor.callback;
    		}
    	}
    	return null;
    }

    
    public int getFacing()
    {
    	return (worldObj == null) ? 0 : this.getBlockMetadata();
    }
    
    private void addMonitors()
    {
    	monitors = new ArrayList<GaugeCallbackPair>();
    	
    	addTileEntityMonitor(IC2_MASS_FAB_CLASSNAME, new IGaugeCallback() {
    		public double getPercentage(TileEntity attachedTo) {
    			NBTTagCompound compound = new NBTTagCompound();
    			attachedTo.writeToNBT(compound);
    			if (compound.hasKey("energy"))
    			{
        			return (100.0 / IC2SensorInterface.MASSFAB_MAX_ENERGY) * compound.getInteger("energy");
    			}
    			return 0;
    		}
    	});
    	

    	addTileEntityMonitor(IInventory.class, new IGaugeCallback() {
    		public double getPercentage(TileEntity attachedTo) {
    			IInventory invent = (IInventory) attachedTo;
    			return Math.random() * 100;
    		}
    	});
    }
}