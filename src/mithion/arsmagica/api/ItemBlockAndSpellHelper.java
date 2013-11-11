package mithion.arsmagica.api;

import java.lang.reflect.Method;

import com.google.common.base.Optional;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Allows item block and spell lookups in the base mod.
 * You need to get the item name correct, however.
 * @author Mithion
 *
 */
public class ItemBlockAndSpellHelper {
	
	private Method powerLookup;
	
	public static Optional<Item> blankRune = Optional.absent();
	public static Optional<Item> redRune = Optional.absent();
	public static Optional<Item> blueRune = Optional.absent();
	public static Optional<Item> greenRune = Optional.absent();
	public static Optional<Item> yellowRune = Optional.absent();
	public static Optional<Item> orangeRune = Optional.absent();
	public static Optional<Item> whiteRune = Optional.absent();
	public static Optional<Item> blackRune = Optional.absent();
	public static Optional<Item> purpleRune = Optional.absent();
	
	public static Optional<Item> arcaneEssence = Optional.absent();
	public static Optional<Item> airEssence = Optional.absent();
	public static Optional<Item> waterEssence = Optional.absent();
	public static Optional<Item> earthEssence = Optional.absent();
	public static Optional<Item> fireEssence = Optional.absent();
	public static Optional<Item> magmaEssence = Optional.absent();
	public static Optional<Item> lightningEssence = Optional.absent();
	public static Optional<Item> plantEssence = Optional.absent();
	public static Optional<Item> iceEssence = Optional.absent();
	public static Optional<Item> lifeEssence = Optional.absent();
	public static Optional<Item> enderEssence = Optional.absent();

	
	public ItemBlockAndSpellHelper(){
		powerLookup = ApiHelper.initializeStaticMethodLookup("mithion.arsmagica.AMPowerLibrary", "GetPowerValueOf", ItemStack.class);		
	}	
	
	/**
	 * Lookup an item by name
	 * @param identifier The item's exact name
	 */
	public ItemStack getItem(String identifier){
		try{
			String itemClass = "mithion.arsmagica.items.ArsMagicaItemsCommonProxy";
			Object retrieved = Class.forName(itemClass).getField(identifier).get(null);
			if (retrieved instanceof Item){
				return new ItemStack((Item)retrieved);
			}
		}catch(Throwable t){
			FMLLog.warning("Ars Magica API >> Error locating item identified by " + identifier);
		}
		return null;
	}
	
	/**
	 * Lookup a block by name.
	 * @param identifier The exact name of the block.
	 */
	public ItemStack getBlock(String identifier){
		try{
			String itemClass = "mithion.arsmagica.blocks.ArsMagicaBlocksCommonProxy";
			Object retrieved = Class.forName(itemClass).getField(identifier).get(null);
			if (retrieved instanceof Block){
				return new ItemStack((Block)retrieved);
			}
		}catch(Throwable t){
			FMLLog.warning("Ars Magica API >> Error locating block identified by " + identifier);
		}
		return null;
	}

	/**
	 * Lookup a spell by name.
	 * @param identifier The spell's exact name.
	 */
	public ItemStack getSpell(String identifier){
		try{
			String itemClass = "mithion.arsmagica.spells.SpellList";
			Object retrieved = Class.forName(itemClass).getField(identifier).get(null);
			if (retrieved instanceof Item){
				return new ItemStack((Item)retrieved);
			}
		}catch(Throwable t){
			FMLLog.warning("Ars Magica API >> Error locating block identified by " + identifier);
		}
		return null;
	}
	
	/**
	 * Get the power value for the specified item.
	 * @param item The itemstack representing the item and quantity to look up.
	 */
	public int getPowerValueFor(ItemStack item){
		try{			
			if (powerLookup != null){
				return (Integer)powerLookup.invoke(null, item);
			}
		}catch(Throwable t){
			FMLLog.warning("Ars Magica API >> Error retrieving power value of " + item.getDisplayName());
		}
		return 0;
	}
}
