package mithion.arsmagica.api.power;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class AMPowerHelper {
	/**
	 * Gets a nexus close to the specified coordinates.
	 * @param world The world to search in
	 * @param x The x coordinate to search from
	 * @param y The y coordinate to search from
	 * @param z The z coordinate to search from
	 * @param range The range to search in - I wouldn't recommend going higher than a radius of 10.
	 * @return An IPowerIntegration instance representing the tile entity located, or null if none was found.
	 */
	public IPowerIntegration getNearbyNexus(World world, int x, int y, int z, int radius){
		for (int i = x - radius; i <= radius; ++i){
			for (int j = y - radius; j <= radius; ++j){
				for (int k = z - radius; k <= radius; ++k){
					TileEntity te = world.getBlockTileEntity(i, j, k);
					if (te != null && te instanceof IPowerIntegration && (((IPowerIntegration)te).isNexus() || ((IPowerIntegration)te).isNexusImpersonator())){
						return (IPowerIntegration)te;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets a nearby nexus path.
	 * @param world The world to search in
	 * @param x The x coordinate to search from
	 * @param y The y coordinate to search from
	 * @param z The z coordinate to search from
	 * @param range The range to search in
	 * @return An IPowerIntegration instance representing the tile entity located, or null if none was found.
	 */
	public IPowerIntegration getNearbyNexusPath(World world, int x, int y, int z, int radius){
		for (int i = x - radius; i <= radius; ++i){
			for (int j = y - radius; j <= radius; ++j){
				for (int k = z - radius; k <= radius; ++k){
					TileEntity te = world.getBlockTileEntity(i, j, k);
					if (te != null && te instanceof IPowerIntegration && ((IPowerIntegration)te).hasNexusPath() && ((IPowerIntegration)te).canProvidePower()){
						return (IPowerIntegration)te;
					}
				}
			}
		}
		return null;
	}
}
