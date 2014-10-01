package universalelectricity.api.core.grid.electric;

import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.api.core.grid.INode;

/** Applied to any node that can store energy as part of an energy network */
public interface IEnergyNode extends INode
{
    /** Adds energy to the node returns energy added */
    public double addEnergy(ForgeDirection from, double wattage, boolean doAdd);

    /** Removes energy from the node returns energy removed */
    public double removeEnergy(ForgeDirection from, double wattage, boolean doRemove);

	/** Current energy stored in UE joules */
	public double getEnergy(ForgeDirection from);

    /** Max limit on energy stored */
    public double getEnergyCapacity(ForgeDirection from);
}
