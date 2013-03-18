package universalelectricity.core.path;

import java.util.Arrays;

import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.block.IConductor;
import universalelectricity.core.block.IConnectionProvider;

/**
 * Check if a conductor connects with another.
 * 
 * @author Calclavia
 * 
 */
public class PathfinderChecker extends Pathfinder
{
	public PathfinderChecker(final IConnectionProvider targetConnector, final IConnectionProvider... ignoreConnector)
	{
		super(new IPathCallBack()
		{
			@Override
			public boolean isValidNode(Pathfinder finder, ForgeDirection direction, IConnectionProvider provider, IConnectionProvider connectedBlock)
			{
				if (connectedBlock instanceof IConductor && !Arrays.asList(ignoreConnector).contains(connectedBlock))
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
				if (provider == targetConnector)
				{
					finder.results.add(provider);
					return true;
				}

				return false;
			}
		});
	}
}
