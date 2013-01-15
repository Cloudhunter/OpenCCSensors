package openccsensors.common.exceptions;

public class CardNotFoundException extends Exception {

	private boolean isTurtle;
	public CardNotFoundException(boolean isTurtle)
	{
		this.isTurtle = isTurtle;
	}
	
	@Override
	public String getMessage() {
		String msg = "No valid sensor card was found.";
		if (isTurtle) msg += " Please make sure a card is in slot 16 of the turtle.";
		
		return msg;
	}
}
