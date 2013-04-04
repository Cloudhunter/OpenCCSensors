package openccsensors.common.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.ComputerCraftAPI;

import openccsensors.OpenCCSensors;
import openccsensors.api.EnumItemRarity;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.api.SensorCard;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.Icon;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import openccsensors.api.ISensorCardRegistry;
import openccsensors.common.SensorTier;
import openccsensors.common.sensor.DroppedItemSensor;
import openccsensors.common.sensor.InventorySensor;
import openccsensors.common.sensor.MinecartSensor;
import openccsensors.common.sensor.ProximitySensor;
import openccsensors.common.sensor.SignSensor;
import openccsensors.common.sensor.SonicSensor;
import openccsensors.common.sensor.TankSensor;
import openccsensors.common.sensor.WorldSensor;
import openccsensors.common.util.OCSLog;
import openccsensors.common.util.RecipeUtils;

public class ItemSensorCard extends Item implements ISensorCardRegistry {

	public List<IRequiresIconLoading> iconLoadList = new ArrayList<IRequiresIconLoading>();

	public HashMap<Integer, SensorCard> cards = new HashMap<Integer, SensorCard>();

	public ItemSensorCard() {
		super(OpenCCSensors.Config.sensorCardID);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(ComputerCraftAPI.getCreativeTab());
	}
	
	@Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.openccsensors.%s", getSensorCard(itemStack).getSensor().getName());
    }
	
	public void registerSensors() {

		SensorTier tier1 = new SensorTier("Mk. I", EnumItemRarity.COMMON, 2, "openccsensors:tier1");
		SensorTier tier2 = new SensorTier("Mk. II", EnumItemRarity.UNCOMMON, 4, "openccsensors:tier2");
		SensorTier tier3 = new SensorTier("Mk. III", EnumItemRarity.RARE, 6, "openccsensors:tier3");
		SensorTier tier4 = new SensorTier("Mk. IV", EnumItemRarity.EPIC, 8, "openccsensors:tier4");
		
		addIconsForLoading(tier1, tier2, tier3, tier4);
		
		OpenCCSensors.Tiers.tier1 = tier1;
		OpenCCSensors.Tiers.tier2 = tier2;
		OpenCCSensors.Tiers.tier3 = tier3;
		OpenCCSensors.Tiers.tier4 = tier4;
		
		addSensorCard(1, new SensorCard(OpenCCSensors.Sensors.proximitySensor, tier1));
		addSensorCard(2, new SensorCard(OpenCCSensors.Sensors.proximitySensor, tier2));
		addSensorCard(3, new SensorCard(OpenCCSensors.Sensors.proximitySensor, tier3));
		addSensorCard(4, new SensorCard(OpenCCSensors.Sensors.proximitySensor, tier4));
		addIconsForLoading(OpenCCSensors.Sensors.proximitySensor);
		
		addSensorCard(5, new SensorCard(OpenCCSensors.Sensors.droppedItemSensor, tier1));
		addSensorCard(6, new SensorCard(OpenCCSensors.Sensors.droppedItemSensor, tier2));
		addSensorCard(7, new SensorCard(OpenCCSensors.Sensors.droppedItemSensor, tier3));
		addSensorCard(8, new SensorCard(OpenCCSensors.Sensors.droppedItemSensor, tier4));
		addIconsForLoading(OpenCCSensors.Sensors.droppedItemSensor);
		
		addSensorCard(9, new SensorCard(OpenCCSensors.Sensors.signSensor, tier1));
		addSensorCard(10, new SensorCard(OpenCCSensors.Sensors.signSensor, tier2));
		addSensorCard(11, new SensorCard(OpenCCSensors.Sensors.signSensor, tier3));
		addSensorCard(12, new SensorCard(OpenCCSensors.Sensors.signSensor, tier4));
		addIconsForLoading(OpenCCSensors.Sensors.signSensor);
		
		addSensorCard(13, new SensorCard(OpenCCSensors.Sensors.minecartSensor, tier1));
		addSensorCard(14, new SensorCard(OpenCCSensors.Sensors.minecartSensor, tier2));
		addSensorCard(15, new SensorCard(OpenCCSensors.Sensors.minecartSensor, tier3));
		addSensorCard(16, new SensorCard(OpenCCSensors.Sensors.minecartSensor, tier4));
		addIconsForLoading(OpenCCSensors.Sensors.minecartSensor);

		addSensorCard(17, new SensorCard(OpenCCSensors.Sensors.sonicSensor, tier1));
		addSensorCard(18, new SensorCard(OpenCCSensors.Sensors.sonicSensor, tier2));
		addSensorCard(19, new SensorCard(OpenCCSensors.Sensors.sonicSensor, tier3));
		addSensorCard(20, new SensorCard(OpenCCSensors.Sensors.sonicSensor, tier4));
		addIconsForLoading(OpenCCSensors.Sensors.sonicSensor);

		addSensorCard(21, new SensorCard(OpenCCSensors.Sensors.tankSensor, tier1));
		addSensorCard(22, new SensorCard(OpenCCSensors.Sensors.tankSensor, tier2));
		addSensorCard(23, new SensorCard(OpenCCSensors.Sensors.tankSensor, tier3));
		addSensorCard(24, new SensorCard(OpenCCSensors.Sensors.tankSensor, tier4));
		addIconsForLoading(OpenCCSensors.Sensors.tankSensor);

		addSensorCard(25, new SensorCard(OpenCCSensors.Sensors.inventorySensor, tier1));
		addSensorCard(26, new SensorCard(OpenCCSensors.Sensors.inventorySensor, tier2));
		addSensorCard(27, new SensorCard(OpenCCSensors.Sensors.inventorySensor, tier3));
		addSensorCard(28, new SensorCard(OpenCCSensors.Sensors.inventorySensor, tier4));
		addIconsForLoading(OpenCCSensors.Sensors.inventorySensor);

		addSensorCard(29, new SensorCard(OpenCCSensors.Sensors.worldSensor, tier1));
		addIconsForLoading(OpenCCSensors.Sensors.worldSensor);
		
		addSensorCard(30, new SensorCard(OpenCCSensors.Sensors.powerSensor, tier1));
		addSensorCard(31, new SensorCard(OpenCCSensors.Sensors.powerSensor, tier2));
		addSensorCard(32, new SensorCard(OpenCCSensors.Sensors.powerSensor, tier3));
		addSensorCard(33, new SensorCard(OpenCCSensors.Sensors.powerSensor, tier4));
		addIconsForLoading(OpenCCSensors.Sensors.powerSensor);
		
		addSensorCard(34, new SensorCard(OpenCCSensors.Sensors.appliedEnergisticsSensor, tier1));
		addSensorCard(35, new SensorCard(OpenCCSensors.Sensors.appliedEnergisticsSensor, tier2));
		addSensorCard(36, new SensorCard(OpenCCSensors.Sensors.appliedEnergisticsSensor, tier3));
		addSensorCard(37, new SensorCard(OpenCCSensors.Sensors.appliedEnergisticsSensor, tier4));
		addIconsForLoading(OpenCCSensors.Sensors.appliedEnergisticsSensor);
		
	}

	public void addIconsForLoading (IRequiresIconLoading... list){
		iconLoadList.addAll(Arrays.asList(list));
	}

	@Override
	public void updateIcons(IconRegister iconRegister) {
		for (IRequiresIconLoading item : iconLoadList) {
			item.loadIcon(iconRegister);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player,
			List list, boolean par4) {
		ISensorTier tier = getSensorCard(itemStack).getTier();
		list.add(tier.getName());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public EnumRarity getRarity(ItemStack itemStack) {
		ISensorTier tier = getSensorCard(itemStack).getTier();
		switch (tier.getRarity()) {
		case COMMON:
			return EnumRarity.common;
		case UNCOMMON:
			return EnumRarity.uncommon;
		case RARE:
			return EnumRarity.rare;
		case EPIC:
			return EnumRarity.epic;
		}
		return EnumRarity.common;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(int id, CreativeTabs tab, List subItems) {
		for (Entry<Integer, SensorCard> entry : cards.entrySet()) {
			subItems.add(new ItemStack(id, 1, entry.getKey()));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIconFromDamageForRenderPass(int dmgValue, int renderPass) {
		return getSensorCard(dmgValue).getIconForRenderPass(renderPass);
	}

	public SensorCard getSensorCard(ItemStack stack) {
		return getSensorCard(stack.getItemDamage());
	}

	public SensorCard getSensorCard(int id) {
		return cards.get(id);
	}
	
	public Entry<Integer, SensorCard> getEntryForSensorAndTier(ISensor sensor, SensorTier tier) {
		for (Entry<Integer, SensorCard> entry : cards.entrySet()) {
			if (entry.getValue().getSensor() == sensor && entry.getValue().getTier() == tier) {
				return entry;
			}
		}
		return null;
	}

	@Override
	public void addSensorCard(int id, SensorCard sensorCard) {
		cards.put(id, sensorCard);
		Entry<Integer, SensorCard> previousTier;
		if (sensorCard.getTier() == OpenCCSensors.Tiers.tier1){
			RecipeUtils.addTier1Recipe(
					sensorCard.getSensor().getUniqueRecipeItem(),
					new ItemStack(OpenCCSensors.Items.sensorCard, 1, id)
			);
		}else if (sensorCard.getTier() == OpenCCSensors.Tiers.tier2){
			previousTier = getEntryForSensorAndTier(sensorCard.getSensor(), OpenCCSensors.Tiers.tier1);
			RecipeUtils.addTier2Recipe(
					new ItemStack(OpenCCSensors.Items.sensorCard, 1, previousTier.getKey()),
					new ItemStack(OpenCCSensors.Items.sensorCard, 1, id)
			);
		}else if (sensorCard.getTier() == OpenCCSensors.Tiers.tier3) {
			previousTier = getEntryForSensorAndTier(sensorCard.getSensor(), OpenCCSensors.Tiers.tier2);
			RecipeUtils.addTier3Recipe(
					new ItemStack(OpenCCSensors.Items.sensorCard, 1, previousTier.getKey()),
					new ItemStack(OpenCCSensors.Items.sensorCard, 1, id)
			);
		}else if (sensorCard.getTier() == OpenCCSensors.Tiers.tier4) {
			previousTier = getEntryForSensorAndTier(sensorCard.getSensor(), OpenCCSensors.Tiers.tier3);
			RecipeUtils.addTier4Recipe(
					new ItemStack(OpenCCSensors.Items.sensorCard, 1, previousTier.getKey()),
					new ItemStack(OpenCCSensors.Items.sensorCard, 1, id)
			);
		}
	}
	
}
