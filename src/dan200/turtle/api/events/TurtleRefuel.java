/*    */ package dan200.turtle.api.events;

import dan200.turtle.api.ITurtleAccess;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.Cancelable;

/*    */ 
/*    */ @Cancelable
/*    */ public class TurtleRefuel extends TurtleEvent
/*    */ {
/*    */   public ItemStack itemstack;
/*    */   public int refuelAmount;
/* 12 */   private boolean handled = false;
/*    */ 
/*    */   public TurtleRefuel(ITurtleAccess turtle, ItemStack itemstack, int fuelToGive)
/*    */   {
/* 16 */     super(turtle);
/* 17 */     this.itemstack = itemstack;
/* 18 */     this.refuelAmount = fuelToGive;
/*    */   }
/*    */ 
/*    */   public boolean isHandled()
/*    */   {
/* 23 */     return this.handled;
/*    */   }
/*    */ 
/*    */   public void setHandled()
/*    */   {
/* 28 */     this.handled = true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mikeef\Documents\CC148.jar
 * Qualified Name:     dan200.turtle.api.events.TurtleRefuel
 * JD-Core Version:    0.6.2
 */