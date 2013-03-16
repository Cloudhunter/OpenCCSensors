package openccsensors.common.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import openccsensors.OpenCCSensors;
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
import net.minecraft.util.Icon;
import openccsensors.api.ISensorCardRegistry;
import openccsensors.common.SensorTier;

public class ItemSensorCard extends Item implements ISensorCardRegistry {

	public List<IRequiresIconLoading> iconLoadList = new ArrayList<IRequiresIconLoading>();

	public HashMap<Integer, SensorCard> cards = new HashMap<Integer, SensorCard>();

	public ItemSensorCard() {
		super(OpenCCSensors.Config.sensorCardID);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public void addIconsForLoading (IRequiresIconLoading... list){
		iconLoadList.addAll(Arrays.asList(list));
	}

	@Override
	public void func_94581_a(IconRegister iconRegister) {
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

	@Override
	public void addSensorCard(int id, SensorCard sensorCard) {
		cards.put(id, sensorCard);
	}
}
