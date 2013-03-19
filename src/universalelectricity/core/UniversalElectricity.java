package universalelectricity.core;

import java.io.File;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Loader;

/**
 * General Universal Electricity class.
 * 
 * @author Calclavia
 * 
 */
public class UniversalElectricity
{
	/**
	 * The version of the Universal Electricity API.
	 */
	public static final int MAJOR_VERSION = 1;
	public static final int MINOR_VERSION = 3;
	public static final int REVISION_VERSION = 0;
	public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION;

	/**
	 * The Universal Electricity configuration file.
	 */
	public static final Configuration CONFIGURATION = new Configuration(new File(Loader.instance().getConfigDir(), "UniversalElectricity/UniversalElectricity.cfg"));

	/**
	 * Multiply this to convert foreign energy into UE Joules.
	 */
	public static double IC2_RATIO = 40;
	public static double BC3_RATIO = 100;

	/**
	 * Multiply this to convert UE Joules into foreign energy.
	 */
	public static double TO_IC2_RATIO = 1 / IC2_RATIO;
	public static double TO_BC_RATIO = 1 / BC3_RATIO;

	/**
	 * Is Universal Electricity currently being voltage sensitive? If so, all machines should
	 * explode under high voltage and react to different amounts of voltage differently.
	 */
	public static boolean isVoltageSensitive = false;

	/**
	 * A general material that can be used by machines. Breakable by hand, suitable for machines.
	 */
	public static final Material machine = new Material(MapColor.ironColor);

	static
	{
		/**
		 * Loads the configuration and sets all the values.
		 */
		CONFIGURATION.load();
		IC2_RATIO = CONFIGURATION.get("Compatiblity", "IndustrialCraft Conversion Ratio", IC2_RATIO).getDouble(IC2_RATIO);
		BC3_RATIO = CONFIGURATION.get("Compatiblity", "BuildCraft Conversion Ratio", BC3_RATIO).getDouble(BC3_RATIO);
		TO_IC2_RATIO = 1 / IC2_RATIO;
		TO_BC_RATIO = 1 / BC3_RATIO;

		isVoltageSensitive = CONFIGURATION.get("Compatiblity", "Is Voltage Sensitive", isVoltageSensitive).getBoolean(isVoltageSensitive);
		CONFIGURATION.save();
	}
}
