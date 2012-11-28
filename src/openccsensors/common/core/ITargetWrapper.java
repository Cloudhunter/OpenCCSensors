package openccsensors.common.core;

public interface ITargetWrapper {
	public ISensorTarget createNew(Object entity, int sx, int sy, int sz);
}
