package universalelectricity.components.common.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import universalelectricity.components.common.BasicComponents;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.item.IItemElectric;

/**
 * An infinite battery used for players and modders to test things.
 * 
 * @author Calclavia
 * 
 */
public class ItemInfiniteBattery extends ItemBasic implements IItemElectric
{
	public ItemInfiniteBattery(int id)
	{
		super("infiniteBattery", id);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setCreativeTab(BasicComponents.TAB);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add("\u00a72 Infinite");
	}

	@Override
	public double getJoules(ItemStack itemStack)
	{
		return this.getMaxJoules(itemStack);
	}

	@Override
	public void setJoules(double joules, ItemStack itemStack)
	{

	}

	@Override
	public double getMaxJoules(ItemStack itemStack)
	{
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public double getVoltage(ItemStack itemStack)
	{
		return 25;
	}

	@Override
	public ElectricityPack onReceive(ElectricityPack electricityPack, ItemStack itemStack)
	{
		return electricityPack;
	}

	@Override
	public ElectricityPack onProvide(ElectricityPack electricityPack, ItemStack itemStack)
	{
		return electricityPack;
	}

	@Override
	public ElectricityPack getReceiveRequest(ItemStack itemStack)
	{
		return new ElectricityPack(Double.POSITIVE_INFINITY, this.getVoltage(itemStack));
	}

	@Override
	public ElectricityPack getProvideRequest(ItemStack itemStack)
	{
		return new ElectricityPack(Double.POSITIVE_INFINITY, this.getVoltage(itemStack));
	}
}