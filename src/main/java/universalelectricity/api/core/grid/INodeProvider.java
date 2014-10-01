package universalelectricity.api.core.grid;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Calclavia
 */
public interface INodeProvider
{
	/**
	 * @param nodeType - The type of node we are looking for.
	 * @param from     - The direction.
	 * @return Returns the node object.
	 */
	public INode getNode(Class< ? extends INode> nodeType, ForgeDirection from);
}
