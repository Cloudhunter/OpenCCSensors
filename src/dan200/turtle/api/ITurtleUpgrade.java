package dan200.turtle.api;

import dan200.computer.api.IHostedPeripheral;
import net.minecraft.item.ItemStack;

public abstract interface ITurtleUpgrade
{
  public abstract int getUpgradeID();

  public abstract String getAdjective();

  public abstract TurtleUpgradeType getType();

  public abstract ItemStack getCraftingItem();

  public abstract boolean isSecret();

  public abstract String getIconTexture(ITurtleAccess paramITurtleAccess, TurtleSide paramTurtleSide);

  public abstract int getIconIndex(ITurtleAccess paramITurtleAccess, TurtleSide paramTurtleSide);

  public abstract IHostedPeripheral createPeripheral(ITurtleAccess paramITurtleAccess, TurtleSide paramTurtleSide);

  public abstract boolean useTool(ITurtleAccess paramITurtleAccess, TurtleSide paramTurtleSide, TurtleVerb paramTurtleVerb, int paramInt);
}

/* Location:           C:\Users\mikeef\Documents\CC148.jar
 * Qualified Name:     dan200.turtle.api.ITurtleUpgrade
 * JD-Core Version:    0.6.2
 */