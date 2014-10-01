package universalelectricity.api.core.grid;

import net.minecraftforge.common.util.ForgeDirection;

import java.util.Map;

/**
 * Applied to anything that can connect to another object either part of a grid or tile system.
 * @author Darkguardsman
 */
public interface IConnector
{
    /** Map of connections to directions they came from */
    public Map<Object, ForgeDirection> getConnections();

    public boolean canConnect(ForgeDirection direction, Object object);
}
