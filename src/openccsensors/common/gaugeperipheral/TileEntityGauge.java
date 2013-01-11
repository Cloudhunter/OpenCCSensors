package openccsensors.common.gaugeperipheral;

import java.util.HashMap;
import java.util.Map;

import openccsensors.common.core.OCSLog;
import openccsensors.common.sensors.industrialcraft.IC2SensorInterface;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGauge extends TileEntity {
	
    public static final String IC2_MASS_FAB_CLASSNAME = "ic2.core.block.machine.tileentity.TileEntityMatter";
    public static final String IC2_MFSU_CLASSNAME = "ic2.core.block.wiring.TileEntityElectricBlock";
    
    private int orientation;
    private double percentage = 0;
    
    private HashMap<Class, IGaugeCallback> monitors = null;
    
    public TileEntityGauge()
    {
    	//monitors = new HashMap<Class, IGaugeCallback>();
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
    	this.orientation = nbttagcompound.getInteger("orientation");
    	this.percentage = nbttagcompound.getDouble("percentage");
        super.readFromNBT(nbttagcompound);
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
    	nbttagcompound.setInteger("orientation", this.orientation);
    	nbttagcompound.setDouble("percentage", this.percentage);
        super.writeToNBT(nbttagcompound);
    }
        
    @Override
    public void updateEntity()
    {
    	if (monitors == null) addMonitors();
    	
    	TileEntity attachedTo = getAttachedTile();
    	if (attachedTo != null)
    	{
        	IGaugeCallback callback = getAvailableCallbackForTileEntity(attachedTo);
	    	if (callback != null)
	    	{
	    		percentage = monitors.get(attachedTo.getClass()).getPercentage(attachedTo);	
	    	}
    	}
        super.updateEntity();
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
    
    private void addTileEntityMonitor(String className, IGaugeCallback callback)
    {
    	try
    	{
    		Class klazz = Class.forName(className);
    		if (klazz != null)
    		{
    			monitors.put(klazz, callback);
    		}
    	}
    	catch(ClassNotFoundException exception)
    	{
    	}
    }
    
    private TileEntity getAttachedTile()
    {
    	return this.getWorldObj().getBlockTileEntity(xCoord, yCoord-1, zCoord);
    }
    
    private IGaugeCallback getAvailableCallbackForTileEntity(TileEntity entity)
    {
		for (Map.Entry<Class, IGaugeCallback> entry : monitors.entrySet()) {
    		if (entry.getKey().isAssignableFrom(entity.getClass()))
    		{
    			return entry.getValue();
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
    	monitors = new HashMap<Class, IGaugeCallback>();
    	
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
    }
}