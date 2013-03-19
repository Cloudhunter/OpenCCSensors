package openccsensors.common.block;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.ComputerCraftAPI;
import openccsensors.OpenCCSensors;
import openccsensors.api.IGaugeSensor;
import openccsensors.common.tileentity.TileEntityGauge;
import openccsensors.common.tileentity.TileEntitySensor;
import openccsensors.common.util.MiscUtils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockGauge extends BlockContainer {

	public BlockGauge() {
		super(OpenCCSensors.Config.gaugeBlockID, Material.ground);
		setHardness(0.5F);
		setCreativeTab(ComputerCraftAPI.getCreativeTab());
		GameRegistry.registerBlock(this, "gauge");
		GameRegistry.registerTileEntity(TileEntityGauge.class, "gauge");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityGauge();
	}
	
	@Override
	public void func_94332_a(IconRegister iconRegister)
	{
		
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        return (dir == NORTH && (world.isBlockSolidOnSide(x, y, z + 1, NORTH) || world.getBlockTileEntity(x, y, z + 1) != null)) ||
               (dir == SOUTH && (world.isBlockSolidOnSide(x, y, z - 1, SOUTH) || world.getBlockTileEntity(x, y, z - 1) != null)) ||
               (dir == WEST  && (world.isBlockSolidOnSide(x + 1, y, z, WEST) || world.getBlockTileEntity(x - 1, y, z) != null)) ||
               (dir == EAST  && (world.isBlockSolidOnSide(x - 1, y, z, EAST) || world.getBlockTileEntity(x - 1, y, z) != null));
    }
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return (world.isBlockSolidOnSide(x - 1, y, z, ForgeDirection.EAST) || world.getBlockTileEntity(x - 1, y, z) != null)
				|| (world.isBlockSolidOnSide(x + 1, y, z, ForgeDirection.WEST) || world.getBlockTileEntity(x - 1, y, z) != null)
				|| (world.isBlockSolidOnSide(x, y, z - 1, ForgeDirection.SOUTH) || world.getBlockTileEntity(x, y, z - 1) != null)
				|| (world.isBlockSolidOnSide(x, y, z + 1, ForgeDirection.NORTH) || world.getBlockTileEntity(x, y, z + 1) != null);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public int getRenderType() {
		return OpenCCSensors.renderId;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z,
			int side, float hitX, float hitY, float hitZ, int metadata) {
		int dir = metadata;
		
		if ((dir == 0 || side == 2) && world.blockExists(x, y, z + 1)) {
			dir = 2;
		}

		if ((dir == 0 || side == 3) && world.blockExists(x, y, z - 1)) {
			dir = 3;
		}

		if ((dir == 0 || side == 4) && world.blockExists(x + 1, y, z)) {
			dir = 4;
		}

		if ((dir == 0 || side == 5) && world.blockExists(x - 1, y, z)) {
			dir = 5;
		}
		return dir;
	}
	
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		this.updateGaugeBounds(blockAccess.getBlockMetadata(x, y, z));
	}
	
	public void updateGaugeBounds(int par1) {
		float var3 = 0.125F;

		if (par1 == 2) {
			this.setBlockBounds(0.0F, 0.0F, 1.0F - var3, 1.0F, 1.0F, 1.0F);
		}

		if (par1 == 3) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var3);
		}

		if (par1 == 4) {
			this.setBlockBounds(1.0F - var3, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}

		if (par1 == 5) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, var3, 1.0F, 1.0F);
		}
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z,
			int metadata, ForgeDirection face) {
		return 0;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y,
			int z, int par5) {
		int metadata = world.getBlockMetadata(x, y, z);
		ForgeDirection infront = ForgeDirection.getOrientation(metadata);
		ForgeDirection behind = infront.getOpposite();
		if (world.getBlockId(x + behind.offsetX, y, z + behind.offsetZ) == 0) {
			this.dropBlockAsItem(world, x, y, z, metadata, 0);
			world.setBlockAndMetadataWithNotify(x, y, z, 0, 0, 3);
		}
		super.onNeighborBlockChange(world, x, y, z, par5);
	}
}
