package universalelectricity.api.core.grid;

import java.util.Set;

/**
 * Basic Grid structure
 * @Author Darkguardsman
 */
public interface IGrid<N>
{
    /** Gets all objects that act as nodes in this grid */
    public Set<N> getNodes();

    /** Adds a node to the grid */
    public void add(N node);

    /** Removes a node from the grid */
    public void remove(N node);

    /** Asks the grid to rebuild */
    public void reconstruct();

    /** Asks teh grid to destroy */
    public void deconstruct();
}
