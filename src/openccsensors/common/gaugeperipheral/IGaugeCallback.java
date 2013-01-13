package openccsensors.common.gaugeperipheral;

import net.minecraft.tileentity.TileEntity;

public interface IGaugeCallback {
	public double getPercentage(TileEntity attachedTo);
}
