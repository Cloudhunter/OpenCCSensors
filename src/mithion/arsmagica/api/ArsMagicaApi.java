package mithion.arsmagica.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import mithion.arsmagica.api.spells.creation.BadThingTypes;
import mithion.arsmagica.api.spells.creation.IIllEffect;
import mithion.arsmagica.api.spells.creation.IllEffectBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;

import com.google.common.base.Optional;

import cpw.mods.fml.common.FMLLog;

public class ArsMagicaApi {
	public static final ArsMagicaApi instance = new ArsMagicaApi();
	
	private static final ItemBlockAndSpellHelper resources = new ItemBlockAndSpellHelper();
	private static final APIEntityHelper entityHelper = new APIEntityHelper();
	private static ISpellScrollHelper spellScrollHelper = null;
	private static Method exPropLookup;
	private static Method spellRegister;
	private static Method illEffectRegister;
	
	public static final int castingModeDiminished = 0;
	public static final int castingModeNormal = 1;
	public static final int castingModeAugmented = 2;
	
	public static final ProjectileInformationList projectiles = new ProjectileInformationList(); 
	
	private ArsMagicaApi(){
		exPropLookup = ApiHelper.initializeStaticMethodLookup("mithion.arsmagica.AMCore", "ExtendedPropertiesFor", EntityLiving.class);
		try {
			spellRegister = ApiHelper.initializeStaticMethodLookup("mithion.arsmagica.spells.SpellList", "RegisterSpell", Class.forName("mithion.arsmagica.api.spells.SpellScrollBase"));
		} catch (ClassNotFoundException e) {
			FMLLog.warning("Ars Magica API >> Class not found when trying to register mithion.arsmagica.spells.SpellList.RegisterSpell(ItemSpellScroll).");
		}
		illEffectRegister = ApiHelper.initializeStaticMethodLookup("mithion.arsmagica.spells.custom.IllEffectsManager", "RegisterIllEffect", IIllEffect.class, int.class, BadThingTypes.class);
	}
	
	public ISpellScrollHelper getSpellScrollHelper(){
		return spellScrollHelper;
	}
	
	public void setSpellScrollHelper(ISpellScrollHelper helper){
		if (this.spellScrollHelper == null)
			this.spellScrollHelper = helper;
	}
	
	/**
	 * Retrieves the reference to the Api's Block, Item, and Spell helper.
	 */
	public ItemBlockAndSpellHelper Resources(){
		return resources;
	}
	
	/**
	 * Retrieves the reference to the Api's entity helper.
	 */
	public APIEntityHelper EntityHelper(){
		return this.entityHelper;
	}
	
	/**
	 * Gets the extended properties for an entity.  Use this to get mana, magic level, casting mode, etc.
	 * @param entity
	 * @return
	 */
	public IExtendedProperties GetExtendedPropertiesFor(EntityLiving entity){
		if (exPropLookup == null) return null;
		
		try {
			return (IExtendedProperties)exPropLookup.invoke(null, entity);
		} catch (Throwable t) {
			FMLLog.info("Ars Magica API >> Error looking up entity extended properties");
		}
		return null;
	}
	
	/**
	 * Register your spell into the mod.
	 */
	public void RegisterSpell(Item scroll){
		if (spellRegister == null){
			FMLLog.info("Ars Magica API >> could not register spell as call hook was NULL.");
			return;
		}
		
		try {
			spellRegister.invoke(null, scroll);
			FMLLog.info("Ars Magica API >> Registered Spell Successfully.");
		} catch (Throwable t) {
			FMLLog.info("Ars Magica API >> Error registering spell - make sure it's an instance of AMAPISpellScroll!");
		}
	}

	/**
	 * Register your ill effect into the mod
	 * @param effect The effect to register
	 * @param weight The weighted probability that the effect will occur
	 */
	public void RegisterIllEffect(IllEffectBase effect, int weight){
		if (illEffectRegister == null) return;
		
		try {
			illEffectRegister.invoke(null, effect, weight);
		} catch (Throwable t) {
			FMLLog.info("Ars Magica API >> Error registering Ill Effect.");
		}
	}
}
