package openccsensors.common.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import openccsensors.OpenCCSensors;
import openccsensors.api.IBasicSensor;
import openccsensors.common.sensor.ProximitySensor;
import openccsensors.common.tileentity.basic.TileEntityBasicProximitySensor;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBasicSensor extends BlockContainer {

	public static final int PROXIMITY_SENSOR_ID = 0;

	public static class Icons {
		public static IIcon top;
		public static IIcon bottom;
		public static IIcon sideAll;
		public static IIcon sideOwner;
		public static IIcon sidePlayers;
	};

	public BlockBasicSensor() {
		super(Material.ground);
		setHardness(0.5F);
		setCreativeTab(OpenCCSensors.tabOpenCCSensors);
		GameRegistry.registerBlock(this, "basicProximitySensor");
		GameRegistry.registerTileEntity(TileEntityBasicProximitySensor.class, "basicProximitySensor");
		setBlockName("openccsensors.proximitysensor");
		
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
		subItems.add(new ItemStack(this, 1, PROXIMITY_SENSOR_ID));
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		Icons.top = iconRegister.registerIcon("openccsensors:proxTop");
		Icons.bottom = iconRegister.registerIcon("openccsensors:proxBottom");
		Icons.sideAll = iconRegister.registerIcon("openccsensors:proxSideAll");
		Icons.sidePlayers = iconRegister.registerIcon("openccsensors:proxSidePlayers");
		Icons.sideOwner = iconRegister.registerIcon("openccsensors:proxSideOwner");
	}
	
    @Override
	public IIcon getIcon(int side, int par2)
    {
		switch(side) {
		case 0:
			return Icons.bottom;
		case 1:
			return Icons.top;
		default:
			return Icons.sideAll;
		}
    }
    
	@Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
		switch(side) {
		case 0:
			return Icons.bottom;
		case 1:
			return Icons.top;
		default:
			TileEntity tile = blockAccess.getTileEntity(x, y, z);
			if ((tile != null) && ((tile instanceof TileEntityBasicProximitySensor))) {
				switch(((TileEntityBasicProximitySensor) tile).getEntityMode()) {
					case ProximitySensor.MODE_ALL:
						return Icons.sideAll;
					case ProximitySensor.MODE_OWNER:
						return Icons.sideOwner;
					case ProximitySensor.MODE_PLAYERS:
						return Icons.sidePlayers;
				}
			}
			return Icons.sideAll;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityBasicProximitySensor();
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess iblockaccess, int x, int y,
			int z, int m) {
		TileEntity tile = iblockaccess.getTileEntity(x, y, z);
		if ((tile != null) && ((tile instanceof IBasicSensor))) {
			return ((IBasicSensor) tile).getPowerOutput();
		}
		return 0;
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess iblockaccess, int x, int y,
			int z, int m) {
		return isProvidingWeakPower(iblockaccess, x, y, z, m);
	}

	@Override
	public void breakBlock(World worldObj, int xCoord, int yCoord, int zCoord,
			Block par5, int par6) {
		worldObj.notifyBlockOfNeighborChange(xCoord, yCoord, zCoord, OpenCCSensors.Blocks.sensorBlock);
		worldObj.notifyBlockOfNeighborChange(xCoord, yCoord - 1, zCoord, OpenCCSensors.Blocks.sensorBlock);
		worldObj.notifyBlockOfNeighborChange(xCoord, yCoord + 1, zCoord, OpenCCSensors.Blocks.sensorBlock);
		worldObj.notifyBlockOfNeighborChange(xCoord - 1, yCoord, zCoord, OpenCCSensors.Blocks.sensorBlock);
		worldObj.notifyBlockOfNeighborChange(xCoord + 1, yCoord, zCoord, OpenCCSensors.Blocks.sensorBlock);
		worldObj.notifyBlockOfNeighborChange(xCoord, yCoord, zCoord - 1, OpenCCSensors.Blocks.sensorBlock);
		worldObj.notifyBlockOfNeighborChange(xCoord, yCoord, zCoord + 1, OpenCCSensors.Blocks.sensorBlock);
		super.breakBlock(worldObj, xCoord, yCoord, zCoord, par5, par6);

	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		TileEntity tile = world.getTileEntity(x,  y,  z);
		if (tile != null && tile instanceof TileEntityBasicProximitySensor) {
			((TileEntityBasicProximitySensor) tile).setOwner(player.getCommandSenderName());
		}
		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if (!world.isRemote) {
			if (player.isSneaking()) {
				return false;
			}
			TileEntity tile = world.getTileEntity(x,  y,  z);
			if (tile != null && tile instanceof TileEntityBasicProximitySensor) {
				((TileEntityBasicProximitySensor) tile).onBlockClicked(player);
			}
		}
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
	    return false;
	}

	@Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return false;
	}

}
