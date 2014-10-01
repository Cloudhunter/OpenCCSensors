package universalelectricity.api.core.grid;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by robert on 8/14/2014.
 */
public interface ISave
{
    /** Called to load the object from NBT */
    public void load(NBTTagCompound nbt);

    /** Called to save the object to NBT */
    public void save(NBTTagCompound nbt);
}
