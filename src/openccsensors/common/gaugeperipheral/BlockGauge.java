package openccsensors.common.gaugeperipheral;

import dan200.computer.api.ComputerCraftAPI;
import openccsensors.OpenCCSensors;
import openccsensors.common.sensorperipheral.TileEntitySensor;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockGauge extends BlockContainer {

	public BlockGauge(int par1,  Material par2Material) {
		super(par1, par2Material);
		blockIndexInTexture = 1;
		
		setCreativeTab(ComputerCraftAPI.getCreativeTab());
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityGauge();
	}

    @Override
    public String getBlockName()
    {
    	return "openccsensors.tile.gaugeblock";
    }
    
    @Override
    public int getRenderType()
    {
        return 2700;
    }
    
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
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
}
