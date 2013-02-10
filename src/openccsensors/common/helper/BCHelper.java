package openccsensors.common.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import openccsensors.common.core.OCSLog;
import openccsensors.common.items.ItemSensorCard;
import cpw.mods.fml.common.Loader;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class BCHelper {
	
	public static Item getStoneGear() {
		Item gear = null;
		try {
			Class buildcraftCore = Class.forName("buildcraft.BuildCraftCore");
			if (buildcraftCore != null) {
				Field gearField = buildcraftCore.getDeclaredField("stoneGearItem");
				gear = (Item) gearField.get(buildcraftCore);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return gear;
	}

	public static void addTier1CardRecipe() {

		Item bcItem = BCHelper.getStoneGear();
		if (bcItem == null) {
			bcItem = Item.coal;
		}
		RecipeHelper.addTier1CardRecipe(ItemSensorCard.BUILDCRAFT_TIER_1, new ItemStack(bcItem));
	
	}
	
	public static Map getEngineData(TileEntity tile) {
		Class tileClass = tile.getClass();
		System.out.println("tileClass = "+tileClass.getName());
		Map engineDetails = new HashMap();
		try {
			Class engineTileClass = Class.forName("buildcraft.energy.TileEngine");
			Class baseEngineClass = Class.forName("buildcraft.energy.Engine");
			Class<Enum> energyStageEnum = (Class<Enum>)Class.forName("buildcraft.energy.Engine$EnergyStage");
			if (engineTileClass != null && engineTileClass.isAssignableFrom(tileClass))
			{
				Method engineMethod = tileClass.getDeclaredMethod("getEngine");
				Object engine = engineMethod.invoke(tile);
				Class engineClass = engine.getClass();
				Method engineHeatMethod = engineClass.getMethod("getHeat");
				int heat = (Integer)engineHeatMethod.invoke(engine);
				Method isBurningMethod = engineClass.getMethod("isBurning");
				boolean isBurning = (Boolean)isBurningMethod.invoke(engine);
				Method isActiveMethod = engineClass.getMethod("isActive");
				boolean isActive = (Boolean)isActiveMethod.invoke(engine);
				Method getEnergyStageMethod = engineClass.getMethod("getEnergyStage");
				Enum energyStage = (Enum) getEnergyStageMethod.invoke(engine);
				engineDetails.put("Heat", heat);
				engineDetails.put("IsBurning", isBurning);
				engineDetails.put("IsActive", isActive);
				engineDetails.put("EnergyStage", energyStage.ordinal());
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return engineDetails;
	}
}
