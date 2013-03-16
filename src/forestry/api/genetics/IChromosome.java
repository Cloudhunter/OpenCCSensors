package forestry.api.genetics;

import forestry.api.core.INBTTagable;

/**
 * Other implementations than Forestry's default one are not supported.
 * 
 * @author SirSengir
 */
public interface IChromosome extends INBTTagable {

	IAllele getPrimaryAllele();

	IAllele getSecondaryAllele();

	IAllele getInactiveAllele();

	IAllele getActiveAllele();

}
