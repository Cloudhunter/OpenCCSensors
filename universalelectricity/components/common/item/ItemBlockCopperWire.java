package universalelectricity.components.common.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import universalelectricity.components.common.tileentity.TileEntityCopperWire;
import universalelectricity.core.electricity.ElectricityDisplay;
import universalelectricity.core.electricity.ElectricityDisplay.ElectricUnit;

public class ItemBlockCopperWire extends ItemBlock
{
	public ItemBlockCopperWire(int id)
	{
		super(id);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add("Resistance: " + ElectricityDisplay.getDisplay(TileEntityCopperWire.RESISTANCE, ElectricUnit.RESISTANCE));
		par3List.add("Max Amps: " + ElectricityDisplay.getDisplay(TileEntityCopperWire.MAX_AMPS, ElectricUnit.AMPERE));
	}
}
