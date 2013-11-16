/*
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at railcraft.wikispaces.com.
 */
package mods.railcraft.api.carts;

import net.minecraft.util.Icon;

/**
 * Used by the renderer to renders blocks in carts.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface ICartContentsTextureProvider{

    public Icon getBlockTextureOnSide(int side);
}
