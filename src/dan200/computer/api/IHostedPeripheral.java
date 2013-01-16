package dan200.computer.api;

import net.minecraft.nbt.NBTTagCompound;


public abstract interface IHostedPeripheral extends IPeripheral
{
  public abstract void update();

  public abstract void readFromNBT(NBTTagCompound paramNBTTagCompound);

  public abstract void writeToNBT(NBTTagCompound paramNBTTagCompound);
}

/* Location:           C:\Users\mikeef\Documents\CC148.jar
 * Qualified Name:     dan200.computer.api.IHostedPeripheral
 * JD-Core Version:    0.6.2
 */