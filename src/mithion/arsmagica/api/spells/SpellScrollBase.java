package mithion.arsmagica.api.spells;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import mithion.arsmagica.api.*;
import mithion.arsmagica.api.spells.interfaces.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class SpellScrollBase extends Item implements Comparable<SpellScrollBase>{
	
	private Icon icon;
	protected Random rand;
	
	public static final int maxChargeTime = 0x11940;
	
	public SpellScrollBase(int par1) {
		super(par1);
		rand = new Random();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		String textureFile = this.spellScrollTexture(null);
		if (!textureFile.contains(":")){
			textureFile = "ArsMagica:" + textureFile;
		}
		if (textureFile.endsWith(".png")){
			textureFile = textureFile.substring(0, textureFile.lastIndexOf(".png"));
		}
		this.icon = par1IconRegister.registerIcon(textureFile);
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return icon;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamageForRenderPass(int par1, int par2) {
		return icon;
	}
	
	/**
	 * Can this spell be cast by NPCs if given to them?
	 */
	public abstract boolean NPCCastable();

	/**
	 * Base mana cost of the spell.  All other mana costs are derived from this, unless overridden.
	 * @param stack The itemstack representing the spell being cast
	 */
	public abstract float spellManaCost(ItemStack stack);	

	/**
	 * Diminished mana cost of the spell.  Returns 1/4 of normal mana cost unless overridden.
	 * @param stack The itemstack representing the spell being cast
	 * @return
	 */
	public float spellManaCostDiminished(ItemStack stack){
		return spellManaCost(stack) / 4;	
	}

	/**
	 * Augmented mana cost of the spell.  Returns 5 times the normal mana cost unless overridden.
	 * @param stack The itemstack representing the spell being cast
	 */
	public float spellManaCostAugmented(ItemStack stack){
		return MathHelper.floor_float(spellManaCost(stack) * 5);				
	}

	/**
	 * How much fatigue casting the spell generates.
	 * @param stack The stack representing the spell
	 * @param caster The caster of the spell (for affinity, etc.)
	 * @param castingMode The casting mode for the spell
	 * @return The amount of fatigue to add
	 */
	public double spellFatigue(ItemStack stack, EntityLiving caster, int castingMode){
		double manaCost = ArsMagicaApi.instance.getSpellScrollHelper().GetModifiedSpellManaCost(this, stack, caster);
		double baseFatigue = manaCost / ArsMagicaApi.instance.GetExtendedPropertiesFor(caster).getMagicLevel();
		ArsMagicaSpellAffinity affinity = ArsMagicaApi.instance.GetExtendedPropertiesFor(caster).getAffinity();
		if (this.getAffinity(stack).contains(affinity)){
			baseFatigue *= 0.5;
		}
		if (this.getAffinity(stack).contains(affinity.getOpposingAffinity())){
			baseFatigue *= 1.5;
		}
		return baseFatigue;
	}
	
	/**
	 * The name of the spell scroll.
	 */
	public abstract String spellScrollName();

	/**
	 * Apply the spell's effect.
	 * @param stack The ItemStack for the specified spell being cast
	 * @param caster
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param l
	 * @param castingMode
	 * @return
	 */
	public abstract boolean applyEffectEx(ItemStack stack, EntityLiving caster, World world, int x, int y, int z, int l, int castingMode);

	 /** Apply the spell's effect.
	 * @param stack The ItemStack for the specified spell being cast.  Convenient overload.
	 * @param caster
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param l
	 * @param castingMode
	 * @return
	 */
	public boolean applyEffectEx(ItemStack stack, EntityLiving caster, World world, double x, double y, double z, int l, int castingMode){
		return applyEffectEx(stack, caster, world, (int)x, (int)y, (int)z, l, castingMode);
	}
	
	/***
	 * Apply the spell's effect when an entity is targeted
	 * @param stack The itemstack representing the spell being cast
	 * @param caster
	 * @param world
	 * @param target
	 * @param castingMode
	 * @return
	 */
	public abstract boolean applyEffectToEntityEx(ItemStack stack, EntityLiving caster, World world, EntityLiving target, int castingMode);	

	/**
	 * The runes required to make this spell scroll when random recipes are off.
	 * @return
	 */
	public abstract Item[] requiredRunes();

	/**
	 * Reagent(s) required in the player's inventory in order to cast the spell.
	 */
	public abstract ItemStack[] requiredReagents(ItemStack stack, int castingMode);

	/**
	 * The name of the sound clip to play on successful spell cast.
	 * @param stack The itemstack representing the spell being cast
	 * @return
	 */
	public abstract String soundFileName(ItemStack stack);

	/**
	 * The description of the spell, used in flavor text.
	 */
	public abstract String spellDescription();

	/**
	 * Can the spell be crafted at an inscription table?
	 */
	public boolean craftable(){
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return this.icon;
	}

	/**
	 * Special logic to be used when attacking an entity (handles multipart entities such as the Ender Dragon, and Twilight Forest's Hydra.
	 * USE THIS FOR SPELLS THAT DEAL DAMAGE
	 * The proper method body will be injected with Ars Magica loaded
	 * @param target  The target to attack
	 * @param damagesource  The type of damage
	 * @param damage  The amount of damage
	 */
	protected final void attackTargetSpecial(EntityLiving target, DamageSource damagesource, int damage){
		ArsMagicaApi.instance.getSpellScrollHelper().AttackTargetSpecial(target, damagesource, damage);
	}
	
	/**
	 * Gets the affinities that this spell is associated with
	 * @param stack The itemstack representing the spell being cast
	 */
	public abstract EnumSet<ArsMagicaSpellAffinity> getAffinity(ItemStack stack);
	
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		if (getMaxItemUseDuration(itemstack) == 0){
			return EnumAction.none;
		}
		return EnumAction.bow;
	}
	
	@Override
	public final boolean hasEffect(ItemStack par1ItemStack) {
		return par1ItemStack.getItem() instanceof ITieredSpell;
	}
	
	public boolean isBeamSpell(ItemStack stack){
		return this instanceof IBeamSpell;
	}

	public boolean isDamaging(ItemStack stack){
		return this instanceof IDamagingSpell;
	}

	/**
	 * Affects the color of the name of the spell
	 */
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		return EnumRarity.rare;
	}

	@Override
	public final int getMaxItemUseDuration(ItemStack itemstack)
	{
		return getChargeTime(itemstack);
	}
	
	@Override
	public final String getItemDisplayName(ItemStack par1ItemStack) {
		return spellScrollName();
	}
	
	/***
	 * Gets the time that the player needs to keep the item in use before the spell will cast
	 * @param stack The itemstack representing the spell being cast
	 * @return
	 */
	public int getChargeTime(ItemStack stack)
	{
		return maxChargeTime;
	}

	@Override
	public final boolean isDamageable() {
		return true;
	}

	/***
	 * Called when a spell scroll is equipped and a block is directly targeted on use
	 */
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{		
		//handle cooldown
		if (itemstack.getItemDamage() > 0){
			return true;
		}
		return false;
	}

	/***
	 * Gets the name of the texture of the spell scroll, without any file extensions.
	 * @param stack The itemstack representing the spell being cast.  Can be NULL.
	 * @return
	 */
	public abstract String spellScrollTexture(ItemStack stack);

	/***
	 * Is the scroll a ranged spell?
	 */
	public boolean isRanged(ItemStack stack){
		return this instanceof IRangedSpell;
	}
	
	@Override
	public final boolean isRepairable() {
		return false;
	}

	/***
	 * Used for when the spell has a projectile, but casts specifically at a block when a block is targeted
	 */
	public boolean directlyTargetsBlocks(ItemStack stack){
		return this instanceof ITargetBlocksDirectly || this instanceof IBlockTarget;
	}
	
	/***
	 * Does the spell need to spawn particles when cast?
	 */
	public final boolean spawnsClientParticles(){
		return this instanceof ISpawnClientParticles;
	}

	/***
	 * Does the spell need to spawn particles before it applies its effect?
	 */
	public final boolean spawnsClientParticlesPreCast(){
		return this instanceof ISpawnClientParticlesPreCast;
	}

	/***
	 * Can the spell be cast at air? (ie, no block is targeted)
	 */
	public boolean allowCastAtAir(ItemStack stack){
		return this instanceof ICastAtAir;
	}

	/**
	 * Does the spell have a delayed effect?
	 */
	public final boolean hasDelayedEffect(ItemStack stack){
		return this instanceof IDelayedEffect;
	}

	/***
	 * Does this spell spawn a projectile?
	 */
	public boolean hasProjectile(ItemStack stack){
		return this instanceof IHaveProjectile;		
	}

	/**
	 * Public exposure of the getMovingObjectPositionFromPlayer method for the spell scroll helper.
	 */
	public final MovingObjectPosition getPlayerMOP(World world, EntityPlayer player){
		return this.getMovingObjectPositionFromPlayer(world, player, true);
	}
	
	/**
	 * Create the flavor text for the spell (description, mana costs, etc.)
	 */
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		String s = spellDescription();
		ArrayList<String> descriptionLines = new ArrayList<String>();
		int lineLength = 25;
		while (s.length() > lineLength){
			int spaceIndex = s.indexOf(' ', lineLength);
			if (spaceIndex == -1){
				if (s.length() < lineLength + 5){
					break;
				}
				descriptionLines.add(s.substring(0, lineLength));
				s = s.substring(lineLength);
			}else{
				descriptionLines.add(s.substring(0, spaceIndex));
				s = s.substring(spaceIndex + 1);
			}
		}
		descriptionLines.add(s);
		for (String line : descriptionLines){
			par3List.add((new StringBuilder()).append("\2475").append(line).toString());
		}

		float manaCost = (float) ArsMagicaApi.instance.getSpellScrollHelper().GetModifiedSpellManaCost(this, par1ItemStack, par2EntityPlayer);
		manaCost = (float) ArsMagicaApi.instance.getSpellScrollHelper().ApplyAffinityManaModifier(this, par1ItemStack, par2EntityPlayer, manaCost);
		s = String.format("Mana Cost: %.2f", manaCost);
		par3List.add((new StringBuilder()).append("\2477").append(s).toString());

		EnumSet<ArsMagicaSpellAffinity> affinities = this.getAffinity(par1ItemStack);

		s = "Affinity: ";

		for (ArsMagicaSpellAffinity affinity : affinities){
			s += affinity.toString() + " ";
		}

		par3List.add((new StringBuilder()).append("\2477").append(s).toString());
	}
	
	/**
	 * Any additional sounds that need to be loaded up on startup for this spell.
	 */
	public String[] additionalRequiredSounds(){
		return null;
	}

	/**
	 * Whether or not the spell has an affinity.
	 */
	public final boolean hasAffinity(){
		return !getAffinity(null).contains(ArsMagicaSpellAffinity.NONE);
	}
	
	public final boolean generateRecipe() {
		return !(this instanceof INoRecipeGen);
	}
	
	/**
	 * Whether or not the spell is a healing spell.
	 */
	public boolean isHealing(ItemStack stack){
		return this instanceof IHealingSpell;
	}
	
	/**
	 * Whether or not this spell is a zone spell.
	 */
	public boolean isZone(ItemStack stack){
		return this instanceof IZoneSpell;
	}
	
	/**
	 * Whether or not the spell is a rune spell.
	 */
	public boolean isRune(ItemStack stack){
		return this instanceof ICreateRune;
	}
	
	/**
	 * Whether or not the spell is a binding spell (summons an item)
	 */
	public boolean isBinding(ItemStack stack){
		return this instanceof IConjureItem;
	}
	
	/**
	 * Whether or not the spell is channeled.
	 */
	public boolean isChanneled(ItemStack stack){
		return this instanceof IChanneledSpell && !(this instanceof IBeamSpell);
	}
	
	public boolean isHasting(ItemStack stack){
		return this instanceof IHastingEffect;
	}
	
	public boolean isSlowing(ItemStack stack){
		return this instanceof ISlowingEffect;
	}
	
	public boolean canCasterCast(ItemStack stack){
		return !(this instanceof ICasterBlockDisabled);
	}
	
	public boolean canBePlacedIntoSpellBook(ItemStack stack){
		return !(this instanceof ISpellBookDisabled);
	}
	
	public boolean summonsCreature(ItemStack stack){
		return this instanceof ISummonCreature;
	}
	
	public boolean isBuffing(ItemStack stack){
		return this instanceof IBuffingSpell;
	}
	
	public boolean isDebuffing(ItemStack stack){
		return this instanceof IDebuffingSpell;
	}
	
	@Override
 	public int compareTo(SpellScrollBase o) {
		return this.spellScrollName().compareTo(o.spellScrollName());
	}

	@Override
	public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) {
		ArsMagicaApi.instance.getSpellScrollHelper().TickOnUsingSpellScroll(this, stack, player, count);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		return ArsMagicaApi.instance.getSpellScrollHelper().HandleScrollRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
		ArsMagicaApi.instance.getSpellScrollHelper().OnStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
	}

}
