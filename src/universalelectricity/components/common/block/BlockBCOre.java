package universalelectricity.components.common.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import universalelectricity.components.common.BasicComponents;

public class BlockBCOre extends Block
{
	private Icon iconCopper;
	private Icon iconTin;

	public BlockBCOre(int id)
	{
		super(id, Material.rock);
		this.setCreativeTab(BasicComponents.TAB);
		this.setUnlocalizedName("bcOre");
		this.setHardness(2f);
	}

	@Override
	public Icon getIcon(int side, int metadata)
	{
		if (metadata == 1)
		{
			return this.iconTin;
		}

		return this.iconCopper;
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.iconCopper = par1IconRegister.registerIcon(BasicComponents.TEXTURE_NAME_PREFIX + "oreCopper");
		this.iconTin = par1IconRegister.registerIcon(BasicComponents.TEXTURE_NAME_PREFIX + "oreTin");
	}

	@Override
	public int damageDropped(int metadata)
	{
		return metadata;
	}

	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
	}
}
