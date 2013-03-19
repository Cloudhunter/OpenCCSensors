package universalelectricity.components.common.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import universalelectricity.components.common.BasicComponents;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBasic extends Item
{
	protected List<Icon> icons = new ArrayList<Icon>();

	public ItemBasic(String name, int id)
	{
		super(id);
		this.setUnlocalizedName(name);
		this.setCreativeTab(BasicComponents.TAB);
	}

	@SideOnly(Side.CLIENT)
	public void func_94581_a(IconRegister iconRegister)
	{
		List<ItemStack> list = new ArrayList<ItemStack>();
		this.getSubItems(this.itemID, this.getCreativeTab(), list);

		if (list.size() > 0)
		{
			for (ItemStack itemStack : list)
			{
				this.icons.add(iconRegister.func_94245_a(this.getUnlocalizedName(itemStack).replace("item.", BasicComponents.TEXTURE_NAME_PREFIX)));
			}
		}
		else
		{
			this.iconIndex = iconRegister.func_94245_a(this.getUnlocalizedName().replace("item.", BasicComponents.TEXTURE_NAME_PREFIX));
		}
	}

	@Override
	public Icon getIconFromDamage(int damage)
	{
		if (this.icons.size() > damage)
		{
			return icons.get(damage);
		}

		return super.getIconFromDamage(damage);
	}
}