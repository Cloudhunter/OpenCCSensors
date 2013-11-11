package mithion.arsmagica.api.events;

import java.util.List;
import java.util.Map;

import mithion.arsmagica.api.spells.creation.BadThingTypes;
import mithion.arsmagica.api.spells.creation.IIllEffect;
import mithion.arsmagica.api.spells.creation.IllEffectBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.Event;

public class IllEffectEvent extends Event{
	/**
	 * The ill effect that was applied
	 */
	public IIllEffect effect;
	
	/**
	 * The list of players that the effect was applied to (if any)
	 * along with corresponding metadata relating to the effect applied (used for description in occulus)
	 */
	public Map<EntityPlayer, Object> players;
	
	/**
	 * The type of ill effect that is being applied.
	 */
	public BadThingTypes type;
	
	/**
	 * The Tile Entity that is applying the Ill Effect.
	 */
	public TileEntity tileEntity;
	
	public IllEffectEvent(IIllEffect effect, Map<EntityPlayer, Object> affectedPlayers, BadThingTypes type, TileEntity tile){
		this.effect = effect;
		this.players = affectedPlayers;
		this.type = type;
		this.tileEntity = tile;
	}
}
