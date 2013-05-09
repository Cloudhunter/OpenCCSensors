package openccsensors.common.block;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.ComputerCraftAPI;
import openccsensors.OpenCCSensors;
import openccsensors.api.IBasicSensor;
import openccsensors.common.sensor.ProximitySensor;
import openccsensors.common.tileentity.TileEntityGauge;
import openccsensors.common.tileentity.basic.TileEntityBasicProximitySensor;
import openccsensors.common.util.OCSLog;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBasicSensor extends BlockContainer {

	public static final int PROXIMITY_SENSOR_ID = 0;

	public static class Icons {
		public static Icon top;
		public static Icon bottom;
		public static Icon sideAll;
		public static Icon sideOwner;
		public static Icon sidePlayers;
	};

	public BlockBasicSensor() {
		super(OpenCCSensors.Config.basicSensorBlockID, Material.ground);
		setHardness(0.5F);
		setCreativeTab(OpenCCSensors.tabOpenCCSensors);
		GameRegistry.registerBlock(this, "basicSensor");
		GameRegistry.registerTileEntity(TileEntityBasicProximitySensor.class, "basicProximitySensor");
		setUnlocalizedName("openccsensors.gauge");
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
		subItems.add(new ItemStack(this, 1, PROXIMITY_SENSOR_ID));
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		Icons.top = iconRegister.registerIcon("openccsensors:proxTop");
		Icons.bottom = iconRegister.registerIcon("openccsensors:proxBottom");
		Icons.sideAll = iconRegister.registerIcon("openccsensors:proxSideAll");
		Icons.sidePlayers = iconRegister.registerIcon("openccsensors:proxSidePlayers");
		Icons.sideOwner = iconRegister.registerIcon("openccsensors:proxSideOwner");
	}
	
    @Override
	public Icon getIcon(int side, int par2)
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
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
		switch(side) {
		case 0:
			return Icons.bottom;
		case 1:
			return Icons.top;
		default:
			TileEntity tile = blockAccess.getBlockTileEntity(x, y, z);
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
	public TileEntity createTileEntity(World world, int metadata) {
		switch (metadata) {
		case PROXIMITY_SENSOR_ID:
			return new TileEntityBasicProximitySensor();
		}
		return null;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess iblockaccess, int x, int y,
			int z, int m) {
		TileEntity tile = iblockaccess.getBlockTileEntity(x, y, z);
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
	public TileEntity createNewTileEntity(World world) {
		return createTileEntity(world, PROXIMITY_SENSOR_ID);
	}

	@Override
	public void breakBlock(World worldObj, int xCoord, int yCoord, int zCoord,
			int par5, int par6) {
		int blockId = OpenCCSensors.Config.basicSensorBlockID;
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, blockId);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord + 1, zCoord, blockId);
		worldObj.notifyBlocksOfNeighborChange(xCoord - 1, yCoord, zCoord, blockId);
		worldObj.notifyBlocksOfNeighborChange(xCoord + 1, yCoord, zCoord, blockId);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord - 1, blockId);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord + 1, blockId);
		super.breakBlock(worldObj, xCoord, yCoord, zCoord, par5, par6);

	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player, ItemStack itemStack) {
		TileEntity tile = world.getBlockTileEntity(x,  y,  z);
		if (tile != null && tile instanceof TileEntityBasicProximitySensor) {
			((TileEntityBasicProximitySensor) tile).setOwner(player.getEntityName());
		}
		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if (!world.isRemote) {
			if (player.isSneaking()) {
				return false;
			}
			TileEntity tile = world.getBlockTileEntity(x,  y,  z);
			if (tile != null && tile instanceof TileEntityBasicProximitySensor) {
				((TileEntityBasicProximitySensor) tile).onBlockClicked(player);
			}
		}
		return true;
	}

}
