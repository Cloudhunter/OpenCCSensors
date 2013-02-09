package openccsensors.common.helper;

import java.util.HashMap;
import net.minecraft.item.ItemStack;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeInterface;
import forestry.api.apiculture.BeeManager;

public  class BeeHelper {
	
	public static boolean isBee(ItemStack itemstack){
		try{
			return BeeManager.beeInterface.isBee(itemstack);
		}catch(Exception e){return false;}	
	}
	
	//make sure you run isBee first
	public static HashMap beeMap(ItemStack itemstack,HashMap map){
		try{
			if(BeeManager.beeInterface.isBee(itemstack)){
				
				if(BeeManager.beeInterface.isDrone(itemstack))
					map.put("Type","Drone");
				else if(BeeManager.beeInterface.isMated(itemstack))
					map.put("Type","Queen");
				else
					map.put("Type","Prinsess");
				try{
				
					IBee thisBee = BeeManager.beeInterface.getBee(itemstack);
				
				if(thisBee.isAnalyzed()){	
					map.put("isAnalysed",true);
					map.put("isNatural",thisBee.isNatural());
					map.put("Generation",thisBee.getGeneration());
					map.put("Health",thisBee.getHealth());
					map.put("MaxHealth",thisBee.getMaxHealth());
					map.put("hasEffect",thisBee.hasEffect());
					
					
					IBeeGenome genome = thisBee.getGenome();
						map.put("getSpeed",genome.getSpeed());
						map.put("getLifespan",genome.getLifespan());
						map.put("getTolerantFlyer",genome.getTolerantFlyer());
						map.put("getCaveDwelling",genome.getCaveDwelling());
						map.put("getFertility",genome.getFertility());
						map.put("getNocturnal",genome.getNocturnal());
						map.put("Fertility",genome.getFertility());
						map.put("Flowering",genome.getFlowering());
						
						
						
						
						
						
						/*try{	
							IAlleleBeeSpecies product=genome.getPrimaryAsBee();
							
							map.put("Primary product",product.getProducts());
							map.put("Special Product",product.getSpecialty());
						}catch(Exception alleE){}*/
				}else{
					map.put("isAnalysed",false);
					
				}	
				}catch(Exception ibee){}	
			} //if the item is a bee. 
		}catch(Exception e){		
			
		}
		return map;
	}//beeMap
	
	//TODO fit this for the gauge
	public static int beeHp(ItemStack itemstack){
		IBee thisBee = BeeManager.beeInterface.getBee(itemstack);
		int hp		=thisBee.getHealth();
		int maxhp	 =thisBee.getMaxHealth();//use this for the gauge
		try{
			return (int)((hp/maxhp)*100);   
		}
		catch(Exception e){return 0;}	
	}

}
