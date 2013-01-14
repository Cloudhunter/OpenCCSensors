package openccsensors.common.gaugeperipheral;

import java.util.ArrayList;
import java.util.Map.Entry;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import openccsensors.common.SensorInterfaceManager;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.core.OCSLog;
import openccsensors.common.sensors.industrialcraft.IC2SensorInterface;

public class TileEntityGauge extends TileEntity {
	
    private double percentage = 0;
    
    public TileEntityGauge()
    {
    }
    
    private void updatePercentage()
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
    	
		for (Entry<Integer, ISensorInterface> entry : SensorInterfaceManager.Interfaces.entrySet())
		{
			ISensorTarget target = entry.getValue().getRelevantTargetForGauge(this.worldObj, this.xCoord + x, this.yCoord, this.zCoord + z);
			if (target != null)
			{
		    	this.percentage = target.getGaugePercentage(this.worldObj);
		    	return;
			}
		}
    	this.percentage = 0.0;
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
    
    public int getFacing()
    {
    	return (worldObj == null) ? 0 : this.getBlockMetadata();
    }
    
    public double getPercentage()
    {
    	return percentage;
    }
    
    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
    	readFromNBT(pkt.customParam1);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
    	this.percentage = nbttagcompound.getDouble("percentage");
        super.readFromNBT(nbttagcompound);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
    	nbttagcompound.setDouble("percentage", this.percentage);
        super.writeToNBT(nbttagcompound);
    }
    
    @Override
    public void updateEntity()
    {
    	if (!this.getWorldObj().isRemote)
    	{
        	updatePercentage();
    	}
        super.updateEntity();
    	this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    
    
}