package universalelectricity.components.common.tileentity;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import universalelectricity.components.common.BasicComponents;
import universalelectricity.components.common.block.BlockBasicMachine;
import universalelectricity.core.block.IElectricityStorage;
import universalelectricity.core.electricity.ElectricityNetworkHelper;
import universalelectricity.core.electricity.IElectricityNetwork;
import universalelectricity.core.item.ElectricItemHelper;
import universalelectricity.core.vector.Vector3;
import universalelectricity.core.vector.VectorHelper;
import universalelectricity.prefab.network.IPacketReceiver;
import universalelectricity.prefab.network.PacketManager;
import universalelectricity.prefab.tile.TileEntityElectricityStorage;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class TileEntityBatteryBox extends TileEntityElectricityStorage implements IElectricityStorage, IPacketReceiver, ISidedInventory
{
	private ItemStack[] containingItems = new ItemStack[2];

	private int playersUsing = 0;

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (!this.isDisabled())
		{
			if (!this.worldObj.isRemote)
			{
				/**
				 * Recharges electric item.
				 */
				this.setJoules(this.getJoules() - ElectricItemHelper.chargeItem(this.containingItems[0], this.getJoules(), this.getVoltage()));

				/**
				 * Decharge electric item.
				 */
				this.setJoules(this.getJoules() + ElectricItemHelper.dechargeItem(this.containingItems[1], this.getMaxJoules() - this.getJoules(), this.getVoltage()));

				ForgeDirection outputDirection = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockBasicMachine.BATTERY_BOX_METADATA + 2);
				TileEntity inputTile = VectorHelper.getConnectorFromSide(this.worldObj, new Vector3(this), outputDirection.getOpposite());
				TileEntity outputTile = VectorHelper.getConnectorFromSide(this.worldObj, new Vector3(this), outputDirection);

				IElectricityNetwork inputNetwork = ElectricityNetworkHelper.getNetworkFromTileEntity(inputTile, outputDirection.getOpposite());
				IElectricityNetwork outputNetwork = ElectricityNetworkHelper.getNetworkFromTileEntity(outputTile, outputDirection);

				if (outputNetwork != null && inputNetwork != outputNetwork)
				{
					double outputWatts = Math.min(outputNetwork.getRequest(this).getWatts(), Math.min(this.getJoules(), 10000));

					if (this.getJoules() > 0 && outputWatts > 0)
					{
						outputNetwork.startProducing(this, outputWatts / this.getVoltage(), this.getVoltage());
						this.setJoules(this.getJoules() - outputWatts);
					}
					else
					{
						outputNetwork.stopProducing(this);
					}
				}
			}
		}

		/**
		 * Gradually lose energy.
		 */
		this.setJoules(this.getJoules() - 0.00005);

		if (!this.worldObj.isRemote)
		{
			if (this.ticks % 3 == 0 && this.playersUsing > 0)
			{
				PacketManager.sendPacketToClients(getDescriptionPacket(), this.worldObj, new Vector3(this), 12);
			}
		}
	}

	@Override
	public boolean canConnect(ForgeDirection direction)
	{
		return direction == ForgeDirection.getOrientation(this.getBlockMetadata() - BlockBasicMachine.BATTERY_BOX_METADATA + 2) || direction == ForgeDirection.getOrientation(this.getBlockMetadata() - BlockBasicMachine.BATTERY_BOX_METADATA + 2).getOpposite();
	}

	@Override
	protected EnumSet<ForgeDirection> getConsumingSides()
	{
		return EnumSet.of(ForgeDirection.getOrientation(this.getBlockMetadata() - BlockBasicMachine.BATTERY_BOX_METADATA + 2).getOpposite());
	}

	@Override
	public Packet getDescriptionPacket()
	{
		return PacketManager.getPacket(BasicComponents.CHANNEL, this, this.getJoules(), this.disabledTicks);
	}

	@Override
	public void handlePacketData(INetworkManager network, int type, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream)
	{
		try
		{
			this.setJoules(dataStream.readDouble());
			this.disabledTicks = dataStream.readInt();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void openChest()
	{
		this.playersUsing++;
	}

	@Override
	public void closeChest()
	{
		this.playersUsing--;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);

		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		this.containingItems = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");

			if (var5 >= 0 && var5 < this.containingItems.length)
			{
				this.containingItems[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.containingItems.length; ++var3)
		{
			if (this.containingItems[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				this.containingItems[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		par1NBTTagCompound.setTag("Items", var2);
	}

	@Override
	public int getStartInventorySide(ForgeDirection side)
	{
		if (side == side.DOWN)
		{
			return 1;
		}

		if (side == side.UP)
		{
			return 1;
		}

		return 0;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 1;
	}

	@Override
	public int getSizeInventory()
	{
		return this.containingItems.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return this.containingItems[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.containingItems[par1] != null)
		{
			ItemStack var3;

			if (this.containingItems[par1].stackSize <= par2)
			{
				var3 = this.containingItems[par1];
				this.containingItems[par1] = null;
				return var3;
			}
			else
			{
				var3 = this.containingItems[par1].splitStack(par2);

				if (this.containingItems[par1].stackSize == 0)
				{
					this.containingItems[par1] = null;
				}

				return var3;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (this.containingItems[par1] != null)
		{
			ItemStack var2 = this.containingItems[par1];
			this.containingItems[par1] = null;
			return var2;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.containingItems[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName()
	{
		return LanguageRegistry.instance().getStringLocalization("tile.bcMachine.1.name");
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public double getMaxJoules()
	{
		return 5000000;
	}

	@Override
	public boolean func_94042_c()
	{
		return false;
	}

	@Override
	public boolean func_94041_b(int i, ItemStack itemstack)
	{
		return false;
	}
}
