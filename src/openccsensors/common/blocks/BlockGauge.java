package openccsensors.common.blocks;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import openccsensors.OpenCCSensors;
import openccsensors.common.blocks.tileentity.TileEntityGauge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.ComputerCraftAPI;

public class BlockGauge extends BlockContainer {

	public BlockGauge(int par1, Material par2Material) {
		super(par1, par2Material);
		blockIndexInTexture = 0;
		setHardness(0.5F);
		setCreativeTab(ComputerCraftAPI.getCreativeTab());
	}

	/**
	 * Checks to see if its valid to put this block at the specified
	 * coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST)
				|| par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST)
				|| par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH)
				|| par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityGauge();
	}

	@Override
	public String getBlockName() {
		return "openccsensors.tile.gaugeblock";
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this
	 * box can change after the pool has been cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3,
				par4);
	}

	@Override
	public int getRenderType() {
		return OpenCCSensors.RenderIds.gaugeRenderId;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super
				.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z,
	 * side, hitX, hitY, hitZ, block metadata
	 */
	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4,
			int par5, float par6, float par7, float par8, int par9) {
		int var10 = par9;

		if ((var10 == 0 || par5 == 2)
				&& par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH)) {
			var10 = 2;
		}

		if ((var10 == 0 || par5 == 3)
				&& par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH)) {
			var10 = 3;
		}

		if ((var10 == 0 || par5 == 4)
				&& par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST)) {
			var10 = 4;
		}

		if ((var10 == 0 || par5 == 5)
				&& par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST)) {
			var10 = 5;
		}

		return var10;
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which
	 * neighbor changed (coordinates passed are their own) Args: x, y, z,
	 * neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, int par5) {
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		boolean var7 = false;

		if (var6 == 2
				&& par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH)) {
			var7 = true;
		}

		if (var6 == 3
				&& par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH)) {
			var7 = true;
		}

		if (var6 == 4
				&& par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST)) {
			var7 = true;
		}

		if (var6 == 5
				&& par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST)) {
			var7 = true;
		}

		if (!var7) {
			this.dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
		}

		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y,
	 * z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess,
			int par2, int par3, int par4) {
		this.updateGaugeBounds(par1IBlockAccess.getBlockMetadata(par2, par3,
				par4));
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
}
