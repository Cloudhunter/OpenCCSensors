/*    */ package dan200.turtle.api;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ public class TurtleAPI
/*    */ {
/* 70 */   private static boolean ccTurtleSearched = false;
/* 71 */   private static Class ccTurtle = null;
/* 72 */   private static Method ccTurtle_registerTurtleUpgrade = null;
/*    */ 
/*    */   public static void registerUpgrade(ITurtleUpgrade upgrade)
/*    */   {
/* 21 */     if (upgrade != null)
/*    */     {
/* 23 */       findCCTurtle();
/* 24 */       if (ccTurtle_registerTurtleUpgrade != null)
/*    */         try
/*    */         {
/* 27 */           ccTurtle_registerTurtleUpgrade.invoke(null, new Object[] { upgrade });
/*    */         }
/*    */         catch (Exception e)
/*    */         {
/*    */         }
/*    */     }
/*    */   }
/*    */ 
/*    */   private static void findCCTurtle()
/*    */   {
/* 41 */     if (!ccTurtleSearched)
/*    */       try
/*    */       {
/* 44 */         ccTurtle = Class.forName("dan200.CCTurtle");
/* 45 */         ccTurtle_registerTurtleUpgrade = findCCTurtleMethod("registerTurtleUpgrade", new Class[] { ITurtleUpgrade.class });
/*    */       }
/*    */       catch (ClassNotFoundException e)
/*    */       {
/* 50 */         System.out.println("ComputerCraftAPI: CCTurtle not found.");
/*    */       }
/*    */       finally {
/* 53 */         ccTurtleSearched = true;
/*    */       }
/*    */   }
/*    */ 
/*    */   private static Method findCCTurtleMethod(String name, Class[] args)
/*    */   {
/*    */     try
/*    */     {
/* 62 */       return ccTurtle.getMethod(name, args);
/*    */     }
/*    */     catch (NoSuchMethodException e) {
/* 65 */       System.out.println("ComputerCraftAPI: CCTurtle method " + name + " not found.");
/* 66 */     }return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\mikeef\Documents\CC148.jar
 * Qualified Name:     dan200.turtle.api.TurtleAPI
 * JD-Core Version:    0.6.2
 */