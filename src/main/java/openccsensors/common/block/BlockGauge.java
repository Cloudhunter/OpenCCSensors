package openccsensors.common.block;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import openccsensors.OpenCCSensors;
import openccsensors.common.tileentity.TileEntityGauge;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGauge extends BlockContainer {

	private IIcon icon;
	
	public BlockGauge() {
		super(Material.ground);
		setHardness(0.5F);
		setCreativeTab(OpenCCSensors.tabOpenCCSensors);
		GameRegistry.registerBlock(this, "gauge");
		GameRegistry.registerTileEntity(TileEntityGauge.class, "gauge");
		setBlockName("openccsensors.gauge");
	}

	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
		return icon;
    }

	@Override
	public IIcon getIcon(int i, int damage)
	{
		return icon;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityGauge();
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		icon = iconRegister.registerIcon("openccsensors:gauge");
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        return (dir == NORTH && (world.isSideSolid(x, y, z + 1, NORTH) || world.getTileEntity(x, y, z + 1) != null)) ||
               (dir == SOUTH && (world.isSideSolid(x, y, z - 1, SOUTH) || world.getTileEntity(x, y, z - 1) != null)) ||
               (dir == WEST  && (world.isSideSolid(x + 1, y, z, WEST) || world.getTileEntity(x - 1, y, z) != null)) ||
               (dir == EAST  && (world.isSideSolid(x - 1, y, z, EAST) || world.getTileEntity(x - 1, y, z) != null));
    }
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return (world.isSideSolid(x - 1, y, z, ForgeDirection.EAST) || world.getTileEntity(x - 1, y, z) != null)
				|| (world.isSideSolid(x + 1, y, z, ForgeDirection.WEST) || world.getTileEntity(x - 1, y, z) != null)
				|| (world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH) || world.getTileEntity(x, y, z - 1) != null)
				|| (world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH) || world.getTileEntity(x, y, z + 1) != null);
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
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return 0;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block par5) {
		int metadata = world.getBlockMetadata(x, y, z);
		ForgeDirection infront = ForgeDirection.getOrientation(metadata);
		ForgeDirection behind = infront.getOpposite();
		if (world.isAirBlock(x + behind.offsetX, y, z + behind.offsetZ)) {
			this.dropBlockAsItem(world, x, y, z, metadata, 0);
			world.setBlockToAir(x, y, z);
		}
		super.onNeighborBlockChange(world, x, y, z, par5);
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
