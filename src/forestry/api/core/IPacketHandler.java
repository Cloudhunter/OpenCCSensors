package forestry.api.core;

import java.io.DataInputStream;

import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;

public interface IPacketHandler {

	void onPacketData(INetworkManager network, int packetID, DataInputStream data, Player player);

}
