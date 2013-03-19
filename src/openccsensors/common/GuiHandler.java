package openccsensors.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.client.gui.GuiSensor;
import openccsensors.common.container.ContainerSensor;
import openccsensors.common.tileentity.TileEntitySensor;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {	
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player,
			World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			return new ContainerSensor(player.inventory, tile);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player,
			World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof TileEntitySensor) {
				return new GuiSensor(player.inventory, tile);
			}
		}
		return null;
	}
}
