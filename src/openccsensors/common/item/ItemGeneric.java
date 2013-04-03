package openccsensors.common.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.ComputerCraftAPI;
import openccsensors.OpenCCSensors;
import openccsensors.api.IItemMeta;
import openccsensors.api.IRequiresIconLoading;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemGeneric extends Item {

	private HashMap<Integer, IItemMeta> metaitems = new HashMap<Integer, IItemMeta>();

	public ItemGeneric() {
		super(OpenCCSensors.Config.genericItemID);
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(64);
		setCreativeTab(ComputerCraftAPI.getCreativeTab());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List subItems) {
		for (Entry<Integer, IItemMeta> entry : metaitems.entrySet()) {
			if (entry.getValue().displayInCreative()) {
				subItems.add(new ItemStack(id, 1, entry.getKey()));
			}
		}
	}

	public void addMeta(IItemMeta meta) {
		metaitems.put(meta.getId(), meta);
	}

	public IItemMeta getMeta(ItemStack stack) {
		return getMeta(stack.getItemDamage());
	}
	
	public IItemMeta getMeta(int id) {
		return metaitems.get(id);
	}
	
	@Override
	public void updateIcons(IconRegister iconRegister) {
		for (Entry<Integer, IItemMeta> entry : metaitems.entrySet()) {
			if (entry.getValue() instanceof IRequiresIconLoading) {
				((IRequiresIconLoading)entry.getValue()).loadIcon(iconRegister);
			}
		}
	}
	
	@Override
    public Icon getIconFromDamage(int id)
    {
        return metaitems.get(id).getIcon();
    }

	@Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.openccsensors.%s", getMeta(itemStack).getName());
    }

}
