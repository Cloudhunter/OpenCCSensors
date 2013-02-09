package forestry.api.arboriculture;

import forestry.api.genetics.IAllele;

public interface ITreeBreedingManager {

	void registerTreeTemplate(IAllele[] template);

	void registerTreeTemplate(String identifier, IAllele[] template);

	IAllele[] getTreeTemplate(String identifier);

	IAllele[] getDefaultTreeTemplate();

}
