package openccsensors.common.sensorperipheral;

import java.util.Random;

import dan200.computer.api.ComputerCraftAPI;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import openccsensors.OpenCCSensors;
import openccsensors.common.core.OCSLog;

public class BlockSensor extends BlockContainer 
{

	private Random random = new Random();
	
	public BlockSensor( int par1, Material par2Material ) 
	{
		super(par1, par2Material);
		blockIndexInTexture = 1;
		
		setCreativeTab(ComputerCraftAPI.getCreativeTab());
		
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	/**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	/*
    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType()
    {
        return OpenCCSensors.Config.sensorBlockRenderID;
    }
	
	@Override
	public TileEntity createNewTileEntity( World world )
	{
		return new TileEntitySensor();
	}
	
    public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ )
    {
        if (!world.isRemote)
        {
        	if (player.isSneaking())
        	{
        		return false;
        	}
        	player.openGui(OpenCCSensors.instance, 1987, world, x, y, z);
        	return true;
        }
        
        return true;
    }
    
    @Override
    public String getBlockName()
    {
    	return "openccsensors.tile.sensorblock";
    }
    
    @Override
    public int idDropped(int metadata, Random random, int zero)
    {
    	return this.blockID;
    }
    
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        TileEntitySensor var7 = (TileEntitySensor)par1World.getBlockTileEntity(par2, par3, par4);

        if (var7 != null)
        {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
            {
                ItemStack var9 = var7.getStackInSlot(var8);

                if (var9 != null)
                {
                    float var10 = this.random.nextFloat() * 0.8F + 0.1F;
                    float var11 = this.random.nextFloat() * 0.8F + 0.1F;
                    EntityItem var14;

                    for (float var12 = this.random.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; par1World.spawnEntityInWorld(var14))
                    {
                        int var13 = this.random.nextInt(21) + 10;

                        if (var13 > var9.stackSize)
                        {
                            var13 = var9.stackSize;
                        }

                        var9.stackSize -= var13;
                        var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));
                        float var15 = 0.05F;
                        var14.motionX = (double)((float)this.random.nextGaussian() * var15);
                        var14.motionY = (double)((float)this.random.nextGaussian() * var15 + 0.2F);
                        var14.motionZ = (double)((float)this.random.nextGaussian() * var15);

                        if (var9.hasTagCompound())
                        {
                            var14.func_92014_d().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                        }
                    }
                }
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    @Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityPlayer) {
    	int p = MathHelper.floor_double((double)((par5EntityPlayer.rotationYaw * 4F) / 360F) + 0.5D) & 3; //this is a smart equation
		byte byte0 = 3;
		
		switch(p)
		{
		case 0:
			byte0 = 2;
			break;
		case 1:
			byte0 = 1;
			break;
		case 2:
			byte0 = 0;
			break;
		case 3:
			byte0 = 3;
			break;
		default:
			break;
		}

		par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0);
	}
    
    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
    	return 0;
    }
    
}
