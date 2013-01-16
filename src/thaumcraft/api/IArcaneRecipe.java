package thaumcraft.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public abstract interface IArcaneRecipe
{
  public abstract boolean matches(IInventory paramIInventory, EntityPlayer paramEntityPlayer);

  public abstract ItemStack getCraftingResult(IInventory paramIInventory);

  public abstract int getRecipeSize();

  public abstract ItemStack getRecipeOutput();

  public abstract int getCost();

  public abstract String getKey();
}

/* Location:           C:\Users\mikeef\Documents\thaumcraft3_deobf.jar
 * Qualified Name:     thaumcraft.api.IArcaneRecipe
 * JD-Core Version:    0.6.2
 */