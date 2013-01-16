package openccsensors.common.api;

import openccsensors.common.core.ISensorEnvironment;

public interface ISensorAccess {
	public ISensorEnvironment getSensorEnvironment();

	public boolean isTurtle();
}
