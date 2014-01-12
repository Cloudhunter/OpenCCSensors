package openccsensors.common.util;

import java.util.HashMap;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleArea;
import forestry.api.genetics.IAlleleBoolean;
import forestry.api.genetics.IAlleleFloat;
import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IChromosome;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyGenome;

import net.minecraft.item.ItemStack;

public class ForestryUtils {
	
	private static ISpeciesRoot beeRoot = AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");
	private static ISpeciesRoot treeRoot = AlleleManager.alleleRegistry.getSpeciesRoot("rootTrees");
	private static ISpeciesRoot butterflyRoot = AlleleManager.alleleRegistry.getSpeciesRoot("rootButterflies");

	public static HashMap genomeToMap(ItemStack item){
		HashMap response = new HashMap();
		if (beeRoot.isMember(item) && beeRoot.getMember(item).isAnalyzed()) {
			response.putAll(beeGenomeToMap(beeRoot.getMember(item)));
		} else if (treeRoot.isMember(item) && treeRoot.getMember(item).isAnalyzed()) {
			response.putAll(treeGenomeToMap(treeRoot.getMember(item)));
		} else if (butterflyRoot.isMember(item) && butterflyRoot.getMember(item).isAnalyzed()) {
			
		}
		return response;
	}
	
	private static Object getAlleleValue(IAllele allele) {
		if (allele instanceof IAlleleBoolean) {
			return ((IAlleleBoolean)allele).getValue();
		} else if (allele instanceof IAlleleInteger) {
			return ((IAlleleInteger)allele).getValue();
		} else if (allele instanceof IAlleleFloat) {
			return ((IAlleleFloat)allele).getValue();
		} else if (allele instanceof IAlleleTolerance) {
			return ((IAlleleTolerance)allele).getValue();
		} else if (allele instanceof IAlleleArea) {
			int[] area = ((IAlleleArea)allele).getValue();
			return area[0] + 'x' + area[1] + 'x' + area[2];
		} else if (allele instanceof IAlleleFlowers) {
			return ((IAlleleFlowers)allele).getProvider().getDescription();
		}
		return null;
	}
	
	public static HashMap beeGenomeToMap(IIndividual individual) {
		HashMap response = new HashMap();
		if (individual instanceof IBee) {
			IBee bee = (IBee) individual;
			IBeeGenome genome = bee.getGenome();
			IChromosome[] chromosomes = ((IGenome)genome).getChromosomes();
			HashMap primary = new HashMap();
			HashMap secondary = new HashMap();
			for (IChromosome chromosome : chromosomes) {
				IAllele priAllele = chromosome.getPrimaryAllele();
				IAllele secAllele = chromosome.getSecondaryAllele();
				primary.put(priAllele.getName(), getAlleleValue(priAllele));
				secondary.put(secAllele.getName(), getAlleleValue(secAllele));
			}
			primary.put("Species", ((IAllele)genome.getPrimary()).getUID());
			secondary.put("Species", ((IAllele)genome.getSecondary()).getUID());
			response.put("Primary", primary);
			response.put("Secondary", secondary);
			response.put("Speed", genome.getSpeed());
			response.put("Lifespan", genome.getLifespan());
			response.put("Fertility", genome.getFertility());
			response.put("TemperatureTolerance", genome.getToleranceTemp());
			response.put("HumidityTolerance", genome.getToleranceHumid());
			response.put("IsNocturnal", genome.getNocturnal());
			response.put("IsTolerantFlyer", genome.getTolerantFlyer());
			response.put("IsCaveDweller", genome.getCaveDwelling());
		}
		
		return response;
	}
	
	public static HashMap treeGenomeToMap(IIndividual individual) {
		HashMap response = new HashMap();
		if (individual instanceof ITree) {
			ITree tree = (ITree) individual;
			ITreeGenome genome = tree.getGenome();
			IChromosome[] chromosomes = ((IGenome)genome).getChromosomes();
			HashMap primary = new HashMap();
			HashMap secondary = new HashMap();
			for (IChromosome chromosome : chromosomes) {
				IAllele priAllele = chromosome.getPrimaryAllele();
				IAllele secAllele = chromosome.getSecondaryAllele();
				primary.put(priAllele.getName(), getAlleleValue(priAllele));
				secondary.put(secAllele.getName(), getAlleleValue(secAllele));
			}
			primary.put("Species", ((IAllele)genome.getPrimary()).getUID());
			secondary.put("Species", ((IAllele)genome.getSecondary()).getUID());
			response.put("Primary", primary);
			response.put("Secondary", secondary);
		}
		return response;
	}
	
	public static HashMap butterflyGenomeToMap(IIndividual individual) {
		HashMap response = new HashMap();
		if (individual instanceof IButterfly) {
			IButterfly butterfly = (IButterfly) individual;
			IButterflyGenome genome = butterfly.getGenome();
			IChromosome[] chromosomes = ((IGenome)genome).getChromosomes();
			HashMap primary = new HashMap();
			HashMap secondary = new HashMap();
			for (IChromosome chromosome : chromosomes) {
				IAllele priAllele = chromosome.getPrimaryAllele();
				IAllele secAllele = chromosome.getSecondaryAllele();
				primary.put(priAllele.getName(), getAlleleValue(priAllele));
				secondary.put(secAllele.getName(), getAlleleValue(secAllele));
			}
			primary.put("Species", ((IAllele)genome.getPrimary()).getUID());
			secondary.put("Species", ((IAllele)genome.getSecondary()).getUID());
			response.put("Primary", primary);
			response.put("Secondary", secondary);
		}
		return response;
	}
}
