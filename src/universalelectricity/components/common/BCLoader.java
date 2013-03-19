package universalelectricity.components.common;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import universalelectricity.components.common.block.BlockBCOre;
import universalelectricity.components.common.block.BlockBasicMachine;
import universalelectricity.components.common.block.BlockCopperWire;
import universalelectricity.components.common.item.ItemBasic;
import universalelectricity.components.common.item.ItemBattery;
import universalelectricity.components.common.item.ItemBlockBCOre;
import universalelectricity.components.common.item.ItemBlockBasicMachine;
import universalelectricity.components.common.item.ItemBlockCopperWire;
import universalelectricity.components.common.item.ItemCircuit;
import universalelectricity.components.common.item.ItemInfiniteBattery;
import universalelectricity.components.common.item.ItemWrench;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.item.ElectricItemHelper;
import universalelectricity.prefab.TranslationHelper;
import universalelectricity.prefab.network.ConnectionHandler;
import universalelectricity.prefab.network.PacketManager;
import universalelectricity.prefab.ore.OreGenReplaceStone;
import universalelectricity.prefab.ore.OreGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = BasicComponents.CHANNEL, name = BasicComponents.NAME, version = UniversalElectricity.VERSION)
@NetworkMod(channels = BasicComponents.CHANNEL, clientSideRequired = true, serverSideRequired = false, connectionHandler = ConnectionHandler.class, packetHandler = PacketManager.class)
public class BCLoader
{

	@Instance("BasicComponents")
	public static BCLoader instance;

	@SidedProxy(clientSide = "universalelectricity.components.client.ClientProxy", serverSide = "universalelectricity.components.common.CommonProxy")
	public static CommonProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		NetworkRegistry.instance().registerGuiHandler(this, this.proxy);
		BasicComponents.registerOres(0, true);
		BasicComponents.registerIngots(0, true);
		BasicComponents.registerPlates(0, true);
		BasicComponents.registerBronzeDust(0, true);
		BasicComponents.registerSteelDust(0, true);
		BasicComponents.registerBattery(0);
		BasicComponents.registerWrench(0);
		BasicComponents.registerCopperWire(0);
		BasicComponents.registerMachines(0);
		BasicComponents.registerCircuits(0);
		BasicComponents.registerMotor(0);
		BasicComponents.registerInfiniteBattery(0);
		proxy.preInit();
	}

	@Init
	public void load(FMLInitializationEvent evt)
	{
		proxy.init();
		BasicComponents.register();
	}
}
