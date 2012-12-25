package dan200.turtle.api.events;

import dan200.turtle.api.ITurtleAccess;
import net.minecraftforge.event.Event;

public class TurtleEvent extends Event 
{
	
	public final ITurtleAccess turtle;

	public TurtleEvent (ITurtleAccess turtle)
	{
		this.turtle = turtle;
	}
	
}
