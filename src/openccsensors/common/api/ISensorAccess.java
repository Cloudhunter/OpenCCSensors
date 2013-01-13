package openccsensors.common.api;

import openccsensors.common.core.ISensorEnvironment;

public interface ISensorAccess 
{
	public ISensorEnvironment getSensorEnvironment();
	public boolean isDirectional();
	public boolean isTurtle();
	public void setDirectional(boolean bool);
}
