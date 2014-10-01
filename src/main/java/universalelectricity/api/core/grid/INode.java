package universalelectricity.api.core.grid;

/** Simple point in a grid that can link to or represent a tile/multipart/entity/block */
public interface INode
{
	/** Called when the grid rebuilds */
	public void reconstruct();

	/** Called before the node is removed from a grid */
	public void deconstruct();

    /** The object that houses this node */
    public INodeProvider getParent();

}
