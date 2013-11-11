package mithion.arsmagica.api.events;

import mithion.arsmagica.api.AMVector3;
import net.minecraftforge.event.Event;

/**
 * Sends an event to a nearby occulus which will log it if the occulus has a book to write in
 * @author Mithion
 *
 */
public class OcculusRecordableEvent extends Event {
	public String eventMessage;
	public AMVector3 location;
	
	public OcculusRecordableEvent(String message, int x, int y, int z){
		this.eventMessage = message;
		location = new AMVector3(x, y, z);
	}
	
	public OcculusRecordableEvent(String message, AMVector3 location){
		this.eventMessage = message;
		this.location = location;
	}
}
