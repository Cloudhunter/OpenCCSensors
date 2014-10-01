package ic2.api.crops;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.BiomeDictionary.Type;

/**
 * General management of the crop system.
 */
public abstract class Crops {
	public static Crops instance;





	/**
	 * Adds a crop nutrient biome bonus.
	 * 
	 * +10/-10  0 indicates no bonus and negative values indicate a penalty.
	 * 
	 * @param type Forge biome type to apply the bonus in
	 * @param nutrientsBonus Nutrient stat bonus
	 */
	public abstract void addBiomenutrientsBonus(Type type, int nutrientsBonus);

	/**
	 * Adds a crop humidity biome bonus.
	 * 
	 * +10/-10 0 indicates no bonus and negative values indicate a penalty.
	 * 
	 * @param type Forge biome type to apply the bonus in
	 * @param humidityBonus Humidity stat bonus
	 */
	public abstract void addBiomehumidityBonus(Type type, int humidityBonus);


	/**
	 * Gets the humidity bonus for a biome.
	 * 
	 * @param biome Biome to check
	 * @return Humidity bonus or 0 if none
	 */
	public abstract int getHumidityBiomeBonus(BiomeGenBase biome);

	/**
	 * Gets the nutrient bonus for a biome.
	 * 
	 * @param biome Biome to check
	 * @return Nutrient bonus or 0 if none
	 */
	public abstract int getNutrientBiomeBonus(BiomeGenBase biome);

	/**
	 * Returns the list of registered crops.
	 * 
	 * @return Registered crops by ID
	 */
	public abstract CropCard[] getCropList();

	/**
	 * Auto-assign an ID to a plant and register it.
	 * Usage of this method is not recommended! Other plants could take your IDs and cause your plants to turn into other plants.
	 * 
	 * @param crop plant to register
	 * @return The ID assigned to the plant
	 */
	public abstract short registerCrop(CropCard crop);

	/**
	 * Attempt to register a plant to an ID.
	 * If the ID is taken, the crop will not be registered and a console print will notify the user.
	 * 
	 * @param crop plant to register
	 * @param i ID to register the plant to
	 * @return Whether the crop was registered
	 */
	public abstract boolean registerCrop(CropCard crop, int i);

	/**
	 * Registers a base seed, an item used to plant a crop.
	 * 
	 * @param stack item
	 * @param id plant ID
	 * @param size initial size
	 * @param growth initial growth stat
	 * @param gain initial gain stat
	 * @param resistance initial resistance stat
	 * @return True if successful
	 */
	public abstract boolean registerBaseSeed(ItemStack stack, int id, int size, int growth, int gain, int resistance);

	/**
	 * Finds a base seed from the given item.
	 * 
	 * @return Base seed or null if none found
	 */
	public abstract BaseSeed getBaseSeed(ItemStack stack);

	/**
	 * Execute registerSprites for all registered crop cards.
	 * 
	 * This method will get called by IC2, don't call it yourself.
	 */
	@SideOnly(Side.CLIENT)
	public abstract void startSpriteRegistration(IIconRegister iconRegister);

	/**
	 * Returns the ID for the given crop.
	 * 
	 * @param crop Crop to look up
	 * @return ID, or -1 if not found
	 */
	public abstract int getIdFor(CropCard crop);
}
