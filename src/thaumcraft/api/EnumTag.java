/*    */ package thaumcraft.api;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ public enum EnumTag
/*    */ {
/*  8 */   UNKNOWN(63, "Obscurus", "Unknown, Obscured", 1, false, 2631720), 
/*    */ 
/* 10 */   WIND(0, "Aura", "Air, Wind, Breath", 1, false, 12632279), 
/* 11 */   MOTION(1, "Motus", "Motion, Movement, Speed", 1, false, 13487348), 
/* 12 */   VOID(2, "Vacuos", "Empty, Void, Insubstantial", 1, true, 12632279), 
/* 13 */   VISION(3, "Visum", "Sight, Vision, Appearance", 3, false, 14013676), 
/* 14 */   KNOWLEDGE(4, "Cognitio", "Learning, Knowledge, Inquiry", 1, false, 8421614), 
/* 15 */   FLIGHT(5, "Volito", "Flight, Leap", 1, false, 15198167), 
/* 16 */   FIRE(6, "Ignis", "Fire, Heat, Burn", 2, true, 16734721), 
/* 17 */   DESTRUCTION(7, "Fractus", "Destruction, Fragmented, Shattered", 2, true, 5267536), 
/* 18 */   LIGHT(8, "Lux", "Light, Brightness, Day", 2, false, 16774755), 
/* 19 */   POWER(9, "Potentia", "Power, Energy, Strength", 2, true, 12648447), 
/* 20 */   MECHANISM(10, "Machina", "Mechanism, Machine, Device", 2, false, 8421536), 
/* 21 */   ROCK(11, "Saxum", "Stone, Rock", 4, false, 6047810), 
/* 22 */   METAL(12, "Metallum", "Metal, Mine, Ore", 4, false, 14211288), 
/*    */ 
/* 24 */   EXCHANGE(14, "Permutatio", "Exchange, Change, Barter", 4, false, 5735255), 
/* 25 */   CLOTH(15, "Pannus", "Cloth, Fabric, Garment, Thread", 3, false, 15395522), 
/* 26 */   EARTH(16, "Solum", "Earth, Soil, Ground, Foundation", 4, false, 7421741), 
/* 27 */   ARMOR(17, "Tutamen", "Defense, Protection, Security", 4, false, 49344), 
/* 28 */   WEAPON(18, "Telum", "Arrow, Sword, Weapon", 2, true, 12603472), 
/* 29 */   TOOL(19, "Instrumentum", "Instrument, Tool, Implement", 4, false, 4210926), 
/* 30 */   CRYSTAL(20, "Vitreus", "Glass, Crystal, Gem, Transparent", 1, false, 8454143), 
/* 31 */   WATER(21, "Aqua", "Water, Fluid", 3, false, 3986684), 
/* 32 */   WEATHER(22, "Aer", "Weather, Mist, Climate", 3, false, 12648447), 
/* 33 */   COLD(23, "Gelum", "Cold, Ice, Frost", 3, true, 14811135), 
/* 34 */   SOUND(24, "Sonus", "Sound, Noise, Din", 3, false, 1100224), 
/* 35 */   HEAL(25, "Sano", "Heal, Repair, Make Sound", 3, false, 16744836), 
/* 36 */   LIFE(26, "Victus", "Life force, Food, Sustenance", 3, false, 14548997), 
/* 37 */   DEATH(27, "Mortuus", "Death, Decay, Undead", 6, true, 4210752), 
/* 38 */   TRAP(28, "Vinculum", "Bind, Imprison, Trap", 4, true, 10125440), 
/* 39 */   POISON(29, "Venenum", "Poison, Drug, Impure", 3, true, 9039872), 
/* 40 */   SPIRIT(30, "Animus", "Soul, Spirit", 1, false, 14737632), 
/* 41 */   VALUABLE(31, "Carus", "Expensive, Precious, Valuable", 4, false, 15121988), 
/* 42 */   WOOD(32, "Lignum", "Wood, Forest, Tree", 4, false, 360709), 
/* 43 */   FLOWER(33, "Flos", "Flower, Bloom, Blossom", 4, false, 16777024), 
/* 44 */   FUNGUS(34, "Fungus", "Mushroom, Toadstool, Fungi", 4, false, 16246215), 
/* 45 */   CROP(35, "Messis", "Crops, Harvest", 4, false, 14942080), 
/* 46 */   PLANT(36, "Herba", "Herb, Plant, Grass", 4, false, 109568), 
/* 47 */   PURE(37, "Purus", "Pure, Clean, Stainless", 3, false, 10878973), 
/*    */ 
/* 49 */   MAGIC(40, "Praecantatio", "Magic, Sorcery", 5, false, 9896128), 
/*    */ 
/* 51 */   CONTROL(48, "Imperito", "Control, Command, Dominate", 5, false, 10000715), 
/* 52 */   DARK(49, "Tenebris", "Dark, Night, Blindness", 5, true, 2434341), 
/* 53 */   CRAFT(50, "Fabrico", "Create, Construct, Work", 2, false, 8428928), 
/*    */ 
/* 55 */   BEAST(53, "Bestia", "Animal, Beast", 3, true, 10445833), 
/* 56 */   FLESH(54, "Corpus", "Body, Flesh, Physique", 3, false, 15615885), 
/* 57 */   INSECT(55, "Bestiola", "Spider, Web, Insects", 3, false, 8423552), 
/* 58 */   EVIL(56, "Malum", "Evil, The Nether, Malice", 5, true, 7340032), 
/* 59 */   FLUX(57, "Mutatio", "Flux, Chaos", 5, true, 12061625), 
/* 60 */   ELDRITCH(58, "Alienis", "Eldritch, The End, Strange, Alien", 5, true, 8409216);
/*    */ 
/*    */   public final int id;
/*    */   public final String name;
/*    */   public final String meaning;
/*    */   public final int element;
/*    */   public final boolean aggro;
/*    */   public final int color;
/*    */   private static final Map lookup;
/*    */ 
/*    */   private EnumTag(int id, String name, String meaning, int element, boolean aggro, int color) {
/* 77 */     this.id = id;
/* 78 */     this.name = name;
/* 79 */     this.meaning = meaning;
/* 80 */     this.element = element;
/* 81 */     this.color = color;
/* 82 */     this.aggro = aggro;
/*    */   }
/*    */ 
/*    */   public int getId()
/*    */   {
/* 89 */     return this.id;
/*    */   }
/* 91 */   public static EnumTag get(int id) { if ((lookup.get(Integer.valueOf(id)) == null) || (((EnumTag)lookup.get(Integer.valueOf(id))).element == 999)) {
/* 92 */       return UNKNOWN;
/*    */     }
/* 94 */     return (EnumTag)lookup.get(Integer.valueOf(id));
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 87 */     lookup = new HashMap();
/*    */     EnumTag s;
/* 88 */     for (Iterator i$ = EnumSet.allOf(EnumTag.class).iterator(); i$.hasNext(); lookup.put(Integer.valueOf(s.getId()), s)) s = (EnumTag)i$.next();
/*    */   }
/*    */ }

/* Location:           C:\Users\mikeef\Documents\thaumcraft3_deobf.jar
 * Qualified Name:     thaumcraft.api.EnumTag
 * JD-Core Version:    0.6.2
 */