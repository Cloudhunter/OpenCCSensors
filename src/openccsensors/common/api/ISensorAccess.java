package openccsensors.common.api;

import openccsensors.common.core.ISensorEnvironment;

public interface ISensorAccess 
{
	public void setDirectional(boolean bool);
	public boolean isDirectional();
	public boolean isTurtle();
	public ISensorEnvironment getSensorEnvironment();
}
