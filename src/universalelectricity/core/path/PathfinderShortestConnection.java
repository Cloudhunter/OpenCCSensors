package universalelectricity.core.path;

import java.util.HashMap;

import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.block.IConductor;
import universalelectricity.core.block.IConnectionProvider;

/**
 * Finds all the possible conductors. Inspired by A* Pathfinding Algorithm.
 * 
 * @author Calclavia
 * 
 */
public class PathfinderShortestConnection extends Pathfinder
{
	/**
	 * The score of this specific path. The higher the score, the better the path is.
	 */
	public HashMap<IConnectionProvider, Integer> gScore = new HashMap<IConnectionProvider, Integer>();

	public PathfinderShortestConnection()
	{
		super(new IPathCallBack()
		{
			@Override
			public boolean isValidNode(Pathfinder finder, ForgeDirection direction, IConnectionProvider provider, IConnectionProvider connectedBlock)
			{
				if (connectedBlock instanceof IConductor)
				{
					if (((IConductor) connectedBlock).canConnect(direction.getOpposite()))
					{
						return true;
					}
				}
				return false;
			}

			@Override
			public boolean onSearch(Pathfinder finder, IConnectionProvider provider)
			{
				return false;
			}
		});
	}

	@Override
	public Pathfinder clear()
	{
		this.gScore.clear();
		return super.clear();
	}
}
