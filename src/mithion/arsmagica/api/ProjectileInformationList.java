package mithion.arsmagica.api;

import mithion.arsmagica.api.spells.ProjectileInformation;

import com.google.common.base.Optional;

/**
 * This class contains all of the projectile types in Ars Magica, by affinity.
 * There's nothing saying you can't add your own, but these are the defaults.
 * @author Mithion
 *
 */
public class ProjectileInformationList {
	public static Optional<ProjectileInformation> DEFAULT = Optional.absent();
	public static Optional<ProjectileInformation> FIRE = Optional.absent();
	public static Optional<ProjectileInformation> WATER = Optional.absent();
	public static Optional<ProjectileInformation> ICE = Optional.absent();
	public static Optional<ProjectileInformation> LIGHTNING = Optional.absent();
	public static Optional<ProjectileInformation> MAGMA = Optional.absent();
	public static Optional<ProjectileInformation> ENDER = Optional.absent();
	public static Optional<ProjectileInformation> EARTH = Optional.absent();
	public static Optional<ProjectileInformation> AIR = Optional.absent();
	public static Optional<ProjectileInformation> PLANT = Optional.absent();
	public static Optional<ProjectileInformation> LIFE = Optional.absent();
}
