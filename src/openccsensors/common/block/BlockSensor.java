package openccsensors.common.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import openccsensors.OpenCCSensors;
import openccsensors.common.tileentity.TileEntitySensor;
import openccsensors.common.util.MiscUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;


public class BlockSensor extends BlockContainer implements IPeripheralProvider {

	public Icon turtleIcon;
	private Icon icon;
	
	public BlockSensor() {
		
		super(OpenCCSensors.Config.sensorBlockID, Material.ground);
		setHardness(0.5F);
		setCreativeTab(OpenCCSensors.tabOpenCCSensors);
		GameRegistry.registerBlock(this, "sensor");
		GameRegistry.registerTileEntity(TileEntitySensor.class, "sensor");
		setUnlocalizedName("openccsensors.sensor");
	}
	
	public boolean canCollideCheck(int par1, boolean par2)
    {
        return true;
    }
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySensor();
	}

	@Override
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
		return icon;
    }

	@Override
	public Icon getIcon(int i, int damage)
	{
		return icon;
	}

	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		turtleIcon = iconRegister.registerIcon("openccsensors:turtleSensor");
		icon = iconRegister.registerIcon("openccsensors:sensor");
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		MiscUtils.dropInventoryItems(world.getBlockTileEntity(x, y, z));
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z,
			int metadata, ForgeDirection face) {
		return 0;
	}
	
	@Override
	public int idDropped(int metadata, Random random, int zero) {
		return this.blockID;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
		super.onBlockPlacedBy(world, x, y, z, entity, itemStack);
		int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.getOrientation(l).getOpposite().ordinal(),1);
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return OpenCCSensors.renderId;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (player.isSneaking()) {
				return false;
			}
			player.openGui(OpenCCSensors.instance, 1987, world, x, y, z);
			return true;
		}
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(World world, int x, int y, int z)
	{
	    return false;
	}
	
	@Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
	{
		return false;
	}

	@Override
	public IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		if (entity instanceof TileEntitySensor) {
			return (IPeripheral)entity;
		}
		return null;
   }
}
