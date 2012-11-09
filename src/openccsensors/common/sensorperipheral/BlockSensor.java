package openccsensors.common.sensorperipheral;

import openccsensors.OpenCCSensors;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockSensor extends BlockContainer 
{

	public BlockSensor( int par1, Material par2Material ) 
	{
		super(par1, par2Material);
		blockIndexInTexture = 1; // Temporary! Will have a texture eventually ;) Will probably end up using a TileEntitySpecialRenderer to render the sensor card on the outside of the block.
		setCreativeTab(CreativeTabs.tabRedstone);
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
        	player.openGui(OpenCCSensors.instance, 1987, world, x, y, z);
        }
        
        return true;
    }
    
    @Override
    public String getBlockName()
    {
    	return "openccsensors.tile.sensorblock";
    }

}
