package openccsensors.common.api;

public interface ITargetWrapper {
	public ISensorTarget createNew(Object entity, int sx, int sy, int sz);
}
