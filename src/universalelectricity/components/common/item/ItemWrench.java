package universalelectricity.components.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.prefab.implement.IToolConfigurator;

public class ItemWrench extends ItemBasic implements IToolConfigurator
{
	public ItemWrench(int id, int texture)
	{
		super("wrench", id);
		this.setMaxStackSize(1);
		this.setMaxDamage(5000);
	}

	@Override
	public boolean canWrench(EntityPlayer entityPlayer, int x, int y, int z)
	{
		return true;
	}

	@Override
	public void wrenchUsed(EntityPlayer entityPlayer, int x, int y, int z)
	{
		if (entityPlayer.getCurrentEquippedItem() != null)
		{
			if (entityPlayer.getCurrentEquippedItem().getItem() instanceof ItemWrench)
			{
				entityPlayer.getCurrentEquippedItem().damageItem(1, entityPlayer);
			}
		}
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		int blockID = world.getBlockId(x, y, z);

		if (blockID == Block.furnaceIdle.blockID || blockID == Block.furnaceBurning.blockID || blockID == Block.field_94340_cs.blockID || blockID == Block.field_96469_cy.blockID || blockID == Block.dispenser.blockID || blockID == Block.pistonBase.blockID || blockID == Block.pistonStickyBase.blockID)
		{
			int metadata = world.getBlockMetadata(x, y, z);

			int[] rotationMatrix = { 1, 2, 3, 4, 5, 0 };

			if (blockID == Block.furnaceIdle.blockID || blockID == Block.furnaceBurning.blockID)
			{
				rotationMatrix = ForgeDirection.ROTATION_MATRIX[0];
			}

			world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.getOrientation(rotationMatrix[metadata]).ordinal(), 3);
			return true;
		}

		return false;
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		return true;
	}

	@Override
	public boolean shouldPassSneakingClickToBlock(World world, int x, int y, int z)
	{
		return true;
	}
}
