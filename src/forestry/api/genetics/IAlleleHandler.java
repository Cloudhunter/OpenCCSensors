package forestry.api.genetics;

/**
 * @author Alex Binnie
 * 
 *         Handler for events that occur in IAlleleRegistry, such as registering alleles, branches etc. Useful for handling plugin specific behavior (i.e.
 *         creating a list of all bee species etc.)
 * 
 */
public interface IAlleleHandler {

	public void onRegisterAllele(IAllele allele);

	public void onRegisterClassification(IClassification branch);
	
	public void onRegisterFruitFamily(IFruitFamily family);

}
