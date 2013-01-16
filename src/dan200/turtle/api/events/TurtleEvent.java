/*    */ package dan200.turtle.api.events;
/*    */ 
/*    */ import dan200.turtle.api.ITurtleAccess;
/*    */ import net.minecraftforge.event.Event;
/*    */ 
/*    */ public class TurtleEvent extends Event
/*    */ {
/*    */   public final ITurtleAccess turtle;
/*    */ 
/*    */   public TurtleEvent(ITurtleAccess turtle)
/*    */   {
/* 13 */     this.turtle = turtle;
/*    */   }
/*    */ }

/* Location:           C:\Users\mikeef\Documents\CC148.jar
 * Qualified Name:     dan200.turtle.api.events.TurtleEvent
 * JD-Core Version:    0.6.2
 */