package openccsensors.common.api;

import java.util.List;

public interface ISensor {
	public String[] getCustomMethods();
	public Object[] callCustomMethod();
	public List<ISensorTarget> getSurroundingTargets(SensorUpgrade upgrade);
}
