package openccsensors.common.util;

import cpw.mods.fml.common.Loader;

public class Mods {
	
	/***
	 * IndustrialCraft 2
	 */
	public static final boolean IC2 = Loader.isModLoaded("IC2");
	
	/***
	 * Applied Energistics
	 */
	public static final boolean AE = Loader.isModLoaded("AppliedEnergistics");
	
	/***
	 * Thermal Expansion
	 */
	public static final boolean TE = Loader.isModLoaded("ThermalExpansion");
	
	/***
	 * RotaryCraft
	 */
	public static final boolean RC = Loader.isModLoaded("RotaryCraft");
	
	/***
	 * ThaumCraft
	 */
	public static final boolean TC = Loader.isModLoaded("ThaumCraft");
	
	/***
	 * Ars Magica
	 */
	public static final boolean AM = Loader.isModLoaded("ArsMagica");
	
	/***
	 * RailCraft
	 */
	public static final boolean RAIL = Loader.isModLoaded("Railcraft");
	
	
	/***
	 * CoFH Core
	 */
	private static boolean isCoFHCoreLoaded() {
		try {
			Class cls = Class.forName("cofh.api.energy.IEnergyProvider");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}	
	
	public static final boolean COFH = isCoFHCoreLoaded(); 
		
}
