package openccsensors.common.exceptions;

public class UnknownSensorCardException extends Exception {

	@Override
	public String getMessage() {
		return "There was a problem with your sensor card. Please report details on the OpenCCSensors bug tracker";
	}
}
