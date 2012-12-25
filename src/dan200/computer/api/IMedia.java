
package dan200.computer.api;

/**
 * TODO: Document me
 */
public interface IMedia
{
	public String getLabel( net.minecraft.item.ItemStack stack );
	public boolean setLabel( net.minecraft.item.ItemStack stack, String label );
	
	public String getAudioTitle( net.minecraft.item.ItemStack stack );
	public String getAudioRecordName( net.minecraft.item.ItemStack stack );	
    
    public String mountData( net.minecraft.item.ItemStack stack, IComputerAccess computer );
}
