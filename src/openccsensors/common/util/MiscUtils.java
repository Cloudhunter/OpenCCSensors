package openccsensors.common.util;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.ForgeDirection;

public class MiscUtils {
	public static ForgeDirection get2dOrientation(Vec3 pos1, Vec3 pos2) {
		double Dx = pos1.xCoord - pos2.xCoord;
		double Dz = pos1.zCoord - pos2.zCoord;
		double angle = Math.atan2(Dz, Dx) / Math.PI * 180 + 180;
		if (angle < 45 || angle > 315)
			return ForgeDirection.EAST;
		else if (angle < 135)
			return ForgeDirection.SOUTH;
		else if (angle < 225)
			return ForgeDirection.WEST;
		else
			return ForgeDirection.NORTH;
	}
	
	public static void dropInventoryItems(TileEntity tileEntity) {
		
		if (tileEntity != null && tileEntity instanceof IInventory) {
			IInventory inventory = (IInventory) tileEntity;
			Random rand = tileEntity.worldObj.rand;
			for (int i = 0; i < inventory.getSizeInventory(); ++i) {
				ItemStack itemStack = inventory.getStackInSlot(i);
				if (itemStack != null) {
					float var10 = rand.nextFloat() * 0.8F + 0.1F;
					float var11 = rand.nextFloat() * 0.8F + 0.1F;
					EntityItem item;
					for (float j = rand.nextFloat() * 0.8F + 0.1F; itemStack.stackSize > 0; tileEntity.worldObj.spawnEntityInWorld(item)) {
						int var13 = rand.nextInt(21) + 10;

						if (var13 > itemStack.stackSize) {
							var13 = itemStack.stackSize;
						}

						itemStack.stackSize -= var13;
						item = new EntityItem(tileEntity.worldObj,
								tileEntity.xCoord + var10,
								tileEntity.yCoord + var11,
								tileEntity.zCoord + j, new ItemStack(
										itemStack.itemID, var13,
										itemStack.getItemDamage()));
						float var15 = 0.05F;
						item.motionX = (float) rand.nextGaussian() * var15;
						item.motionY = (float) rand.nextGaussian() * var15 + 0.2F;
						item.motionZ = (float) rand.nextGaussian() * var15;

						if (itemStack.hasTagCompound()) {
							item.getEntityItem().setTagCompound(
									(NBTTagCompound) itemStack.getTagCompound().copy()
							);
						}
					}
				}
			}
		}
	}
}
