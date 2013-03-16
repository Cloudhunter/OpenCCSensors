package forestry.api.genetics;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLLog;

public class AlleleManager {

	public static IAlleleRegistry alleleRegistry;

	public static HashMap<ItemStack, IIndividual> ersatzSpecimen = new HashMap<ItemStack, IIndividual>();
	public static HashMap<ItemStack, IIndividual> ersatzSaplings = new HashMap<ItemStack, IIndividual>();
	
	/**
	 * Access Forestry's default alleles via reflection.
	 * 
	 * @param ident
	 * @return IAllele for the passed identifier or null if none was found.
	 * 
	 * @deprecated
	 * Use IAlleleRegistry.getAllele instead!
	 */
	@Deprecated
	public static IAllele getAllele(String ident) {
		IAllele allele = null;

		try {

			String alleleClass = "forestry.core.genetics.Allele";

			Object obj = Class.forName(alleleClass).getField(ident).get(null);
			if (obj instanceof IAllele) {
				allele = (IAllele) obj;
			}
		} catch (Exception ex) {
			FMLLog.warning("Could not retrieve bee allele identified by: " + ident);
		}

		return allele;
	}

}
