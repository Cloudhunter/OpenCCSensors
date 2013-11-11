package mithion.arsmagica.api;

import java.util.HashMap;
import java.util.Random;

import mithion.arsmagica.api.spells.ArsMagicaSpellAffinity;
import mithion.arsmagica.api.spells.SpellScrollBase;
import mithion.arsmagica.api.spells.interfaces.IBeamSpell;
import mithion.arsmagica.api.spells.interfaces.IChanneledSpell;
import mithion.arsmagica.api.spells.interfaces.IDelayedEffect;
import mithion.arsmagica.api.spells.interfaces.IHaveProjectile;
import mithion.arsmagica.api.spells.interfaces.ISpawnClientParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface ISpellScrollHelper {
	/**
	 * Called when a player right clicks a block with a scroll equipped.
	 * @param itemstack  The itemstack representing the spell
	 * @param world The world the player is in
	 * @param entityplayer The player entity
	 * @return The itemstack (modified if necessary)
	 */
	ItemStack HandleScrollRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer);

	/**
	 * Override so that this function can return a boolean value - needed for spell book + spell cooldowns
	 */
	boolean OnStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i);

	/**
	 * Called each tick that a spell is on cooldown
	 * @param stack The itemstack representing the spell
	 * @param entity The entity casting the spell.
	 */
	void HandleScrollCooldownTick(ItemStack stack, Entity entity);

	/**
	 * Gets the required runes for the current player in order to make the spell.
	 * @param player The player to check against
	 * @param scroll The spell you want the recipe for
	 * @param spellRandomKey The player's spell recipe random seed
	 * @return
	 */
	Item[] GetRequiredRunes(EntityPlayer player, SpellScrollBase scroll, int spellRandomKey);

	/***
	 * Actually casts the spell - will not check mana, reagents, anything like that
	 * @param scroll The spell being cast
	 * @param stack The itemstack representing the spell
	 * @param entity
	 * The caster of the spell
	 * @param world
	 * The world the spell is being cast in
	 * @param x
	 * X coordinate of the targeted block, or the X position of the caster if targeted at air
	 * @param y
	 * Y coordinate of the targeted block, or the Y position of the caster if targeted at air
	 * @param z
	 * Z coordinate of the targeted block, or the Z position of the caster if targeted at air
	 * @param l
	 * The face of the targeted block, or -1 if cast at air
	 * @param castingMode
	 * The casting mode the caster is using
	 * @return
	 * True if the spell was successfully cast
	 */
	boolean CastSpellNoChecks(SpellScrollBase scroll, ItemStack stack, EntityLiving entity, World world, int x, int y, int z, int l, int castingMode);

	/***
	 * Performs mana checks, reagent checks, then calls out to CastSpellnoChecks.  Also handles mana deductions.
	 * @param entity
	 * The caster of the spell
	 * @param world
	 * The world the spell is being cast in
	 * @param x
	 * X coordinate of the targeted block, or the X position of the caster if targeted at air
	 * @param y
	 * Y coordinate of the targeted block, or the Y position of the caster if targeted at air
	 * @param z
	 * Z coordinate of the targeted block, or the Z position of the caster if targeted at air
	 * @param l
	 * The face of the targeted block, or -1 if cast at air
	 * @param castingMode
	 * The casting mode the caster is using
	 * @return
	 * True if the spell was successfully cast
	 */
	boolean CastSpell(SpellScrollBase scroll, ItemStack stack, EntityLiving entity, World world, int x, int y, int z, int l, int castingMode);
	
	/**
	 * Checks to see if the specified player has the reagents for the specified spell.
	 * @param scroll The scroll to check against
	 * @param stack The itemstack representing the scroll
	 * @param entity The entity attempting to cast the spell
	 * @param world The world the caster is in
	 * @param castingMode The casting mode being attempted
	 * @return True if the reagents are present, otherwise false.
	 */
	boolean CheckForReagents(SpellScrollBase scroll, ItemStack stack, EntityLiving entity, World world, int castingMode);

	/**
	 * Consumes the mana and reagents from the entity as if they had just cast the spell.
	 * @param scroll The spell that was cast
	 * @param stack The itemstack representing the scroll
	 * @param entity The caster of the spell
	 */
	void ConsumeManaAndReagents(SpellScrollBase scroll, ItemStack stack, EntityLiving entity);

	/**
	 * Consumes the reagents for the spell
	 * @param scroll The scroll that was cast
	 * @param stack The itemstack representing the spell
	 * @param entity The caster of the spell
	 * @param castingMode The casting mode used to cast the spell
	 */
	void ConsumeReagents(SpellScrollBase scroll, ItemStack stack, EntityLiving entity, int castingMode);

	/***
	 * Consumes mana from the entity.  Amount consumed is determined by GetModifiedSpellManaCost.
	 * @param stack The itemstack representing the spell being cast
	 * @param entity
	 * The entity to consume the mana from.
	 */
	void ConsumeMana(SpellScrollBase scroll, ItemStack stack, EntityLiving entity);

	/***
	 * Does the entity have enough mana to cast this spell?
	 * Current casting mode is taken into account.
	 * <br/>
	 * Always returns true if the player is in creative mode.
	 * @param stack The itemstack representing the spell being cast
	 */
	boolean EntityHasEnoughManaForSpell(SpellScrollBase scroll, ItemStack stack, EntityLiving entity);

	/***
	 * Does the player have the specified reagent somewhere in their inventory? <br/> Always returns true if the player is in creative mode.
	 * @param player
	 * The player to check
	 * @param castingMode
	 * The casting mode that the player is using
	 * @param reagent
	 * The item id of the reagent needed
	 * @param quantity
	 * The quantity of the reagent needed
	 * @return
	 */
	boolean PlayerHasReagentInInventory(SpellScrollBase scroll, ItemStack stack, EntityPlayer player, int castingMode, int reagent, int quantity);

	/***
	 * Consume [quantity] of the specified reagent from the player.  Does no checking to ensure the reagents are present - use the 
	 * PlayerHasReagentInInventory to ensure the reagents are there.
	 * @param player
	 * The player to consume from
	 * @param castingMode
	 * The player's casting mode
	 * @param reagent
	 * The item id of the reagent to consume
	 * @param quantity
	 * The quantity to consume
	 */
	void ConsumeReagent(SpellScrollBase scroll, ItemStack stack, EntityPlayer player, int castingMode, int reagent, int quantity);

	/***
	 * Handle packets for spawning particles where applicable, then call into the ApplyEffectEx method
	 * @param stack The itemstack representing the spell being cast
	 */
	boolean ApplyEffect(SpellScrollBase scroll, ItemStack stack, EntityLiving caster, World world, double x, double y, double z, float l, int castingMode);

	/***
	 * Handle packets for spawning particles where applicable, then call into the ApplyEffectEx method
	 * @param stack The itemstack representing the spell being cast
	 */
	boolean ApplyEffect(SpellScrollBase scroll, ItemStack stack, EntityLiving caster, World world, int x, int y, int z, int l, int castingMode);

	/***
	 * Handle packets for spawning particles where applicable, then call into the ApplyEffectToEntityEx method
	 * @param stack The itemstack representing the spell being cast
	 */
	public boolean ApplyEffectToEntity(SpellScrollBase scroll, ItemStack stack, EntityLiving caster, World world, EntityLiving target, int castingMode);

	/**
	 * Gets the modified spell cost based on the entity's casting mode
	 * @param stack The itemstack representing the spell being cast
	 */
	double GetModifiedSpellManaCost(SpellScrollBase scroll, ItemStack stack, EntityLiving entity);

	/**
	 * Applies affinity modifiers to the mana cost of the spell.
	 * @param entity The entity casting the spell
	 * @param manaCost The original mana cost before affinity is applied
	 * @return The modified mana cost after affinity is applied.
	 */
	double ApplyAffinityManaModifier(SpellScrollBase scroll, ItemStack stack, EntityLiving entity, float manaCost);

	/**
	 * Called each tick that a spell is in use.
	 * @param scroll The scroll in use
	 * @param stack The itemstack representing the spell
	 * @param player The caster of the spell
	 * @param count The number of ticks the spell has been in use.
	 */
	void TickOnUsingSpellScroll(SpellScrollBase scroll, ItemStack stack, EntityPlayer player, int count);

	/**
	 * Special logic to be used when attacking an entity (handles multipart entities such as the Ender Dragon, and Twilight Forest's Hydra).
	 * Also handles the configurable damage multiplier.
	 * @param target  The target to attack
	 * @param damagesource  The type of damage
	 * @param damage  The amount of damage
	 */
	void AttackTargetSpecial(EntityLiving target, DamageSource damagesource, int damage);
}
