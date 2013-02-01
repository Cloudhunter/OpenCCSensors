package forestry.api.genetics;

/**
 * Should be extended for different jobs. ISpeciesAllele, IBiomeAllele, etc.
 */
public interface IAllele {
	String getUID();

	boolean isDominant();
}
