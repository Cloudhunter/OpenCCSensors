package openccsensors.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.OpenCCSensors;
import openccsensors.OpenCCSensors.Config;
import openccsensors.OpenCCSensors.Items;
import openccsensors.common.api.SensorManager;
import openccsensors.common.blocks.BlockGauge;
import openccsensors.common.blocks.BlockSensor;
import openccsensors.common.blocks.tileentity.TileEntityGauge;
import openccsensors.common.blocks.tileentity.TileEntitySensor;
import openccsensors.common.core.OCSLog;
import openccsensors.common.helper.BCHelper;
import openccsensors.common.helper.IC2Helper;
import openccsensors.common.helper.RecipeHelper;
import openccsensors.common.helper.ThaumcraftHelper;
import openccsensors.common.items.ItemSensorCard;
import openccsensors.common.items.ItemGeneric;
import openccsensors.common.peripherals.ContainerSensor;
import openccsensors.common.sensors.BuildCraftSensor;
import openccsensors.common.sensors.DroppedItemSensor;
import openccsensors.common.sensors.IndustrialCraftSensor;
import openccsensors.common.sensors.InventorySensor;
import openccsensors.common.sensors.MinecartSensor;
import openccsensors.common.sensors.ProximitySensor;
import openccsensors.common.sensors.SignSensor;
import openccsensors.common.sensors.SonicSensor;
import openccsensors.common.sensors.TankSensor;
import openccsensors.common.sensors.ThaumCraftSensor;
import openccsensors.common.sensors.WorldSensor;
import openccsensors.common.turtles.TurtleUpgradeSensor;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dan200.turtle.api.TurtleAPI;

public class CommonProxy {

	private class GuiHandler implements IGuiHandler {

		@Override
		public Object getClientGuiElement(int ID, EntityPlayer player,
				World world, int x, int y, int z) {
			TileEntity tile = world.getBlockTileEntity(x, y, z);

			if (tile != null && tile instanceof TileEntitySensor) {
				return getGui(player.inventory, (TileEntitySensor) tile);
			}

			return null;
		}

		@Override
		public Object getServerGuiElement(int ID, EntityPlayer player,
				World world, int x, int y, int z) {
			TileEntity tile = world.getBlockTileEntity(x, y, z);

			if (tile != null && tile instanceof TileEntitySensor) {
				return new ContainerSensor(player.inventory,
						(TileEntitySensor) tile);
			}

			return null;
		}

	}

	private static final void copy(File source, File destination)
			throws IOException {
		if (source.isDirectory()) {
			copyDirectory(source, destination);
		} else {
			copyFile(source, destination);
		}
	}

	private static final void copyDirectory(File source, File destination)
			throws IOException {
		if (!source.isDirectory()) {
			throw new IllegalArgumentException("Source (" + source.getPath()
					+ ") must be a directory.");
		}

		if (!source.exists()) {
			throw new IllegalArgumentException("Source directory ("
					+ source.getPath() + ") doesn't exist.");
		}

		if (destination.exists()) {
			// throw new IllegalArgumentException( "Destination (" +
			// destination.getPath() + ") exists." );
		}

		destination.mkdirs();
		File[] files = source.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				copyDirectory(file, new File(destination, file.getName()));
			} else {
				copyFile(file, new File(destination, file.getName()));
			}
		}
	}

	private static final void copyFile(File source, File destination)
			throws IOException {
		FileChannel sourceChannel = new FileInputStream(source).getChannel();
		FileChannel targetChannel = new FileOutputStream(destination)
				.getChannel();
		sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
		sourceChannel.close();
		targetChannel.close();
	}

	private void extractZipToLocation(File zipFile, String sourceFolder,
			String destFolder) {
		try {

			File destFile = new File(getBase(), destFolder);
			String destinationName = destFile.getAbsolutePath();
			byte[] buf = new byte[1024];
			ZipInputStream zipinputstream = null;
			ZipEntry zipentry;
			zipinputstream = new ZipInputStream(new FileInputStream(zipFile));

			zipentry = zipinputstream.getNextEntry();
			while (zipentry != null) {
				// for each entry to be extracted
				String zipentryName = zipentry.getName();
				if (!zipentryName.startsWith(sourceFolder)) {
					zipentry = zipinputstream.getNextEntry();
					continue;
				}
				String entryName = destinationName
						+ zipentryName.substring(Math.min(
								zipentryName.length(),
								sourceFolder.length() - 1));
				entryName = entryName.replace('/', File.separatorChar);
				entryName = entryName.replace('\\', File.separatorChar);
				OCSLog.info("Extracting " + entryName);
				int n;
				FileOutputStream fileoutputstream;
				File newFile = new File(entryName);
				if (zipentry.isDirectory()) {
					if (!newFile.mkdirs()) {
						break;
					}
					zipentry = zipinputstream.getNextEntry();
					continue;
				}

				fileoutputstream = new FileOutputStream(entryName);

				while ((n = zipinputstream.read(buf, 0, 1024)) > -1) {
					fileoutputstream.write(buf, 0, n);
				}

				fileoutputstream.close();
				zipinputstream.closeEntry();
				zipentry = zipinputstream.getNextEntry();

			}// while

			zipinputstream.close();
		} catch (Exception e) {
			OCSLog.warn("Error while extracting Lua files. Peripheral may not automount Lua files! Exception follows.");
			e.printStackTrace();
		}
	}

	public File getBase() {
		return FMLCommonHandler.instance().getMinecraftServerInstance()
				.getFile(".");
	}

	// GUI Stuff
	public Object getGui(InventoryPlayer inventory, TileEntitySensor tileentity) {
		// returns nothing on the common proxy - will only return the GUI on the
		// client proxy
		return null;
	}

	public void init() {
		// create block and register it
		OpenCCSensors.Blocks.sensorBlock = new BlockSensor(
				OpenCCSensors.Config.sensorBlockID, Material.cloth);
		GameRegistry.registerBlock(OpenCCSensors.Blocks.sensorBlock, "OCS");
		GameRegistry.registerTileEntity(TileEntitySensor.class, "sensor");
		GameRegistry.addRecipe(new ItemStack(OpenCCSensors.Blocks.sensorBlock,
				1, 0), "ooo", "ror", "sss", 'o', new ItemStack(Block.obsidian),
				'r', new ItemStack(Item.redstone), 's', new ItemStack(
						Block.stone));

		OpenCCSensors.Blocks.gaugeBlock = new BlockGauge(
				OpenCCSensors.Config.gaugeBlockID, Material.cloth);
		GameRegistry
				.registerBlock(OpenCCSensors.Blocks.gaugeBlock, "OCS.gauge");
		GameRegistry.registerTileEntity(TileEntityGauge.class, "gauge");

		if (OpenCCSensors.Config.turtlePeripheralEnabled) {
			TurtleAPI.registerUpgrade(new TurtleUpgradeSensor());
		}
		

		// register upgrade
		Items.genericItem = new ItemGeneric(Config.genericItemID);
		Items.sensorCard = new ItemSensorCard(Config.sensorCardID);


		// register all sensors
		SensorManager.registerSensor(new ProximitySensor());
		SensorManager.registerSensor(new DroppedItemSensor());
		//SensorManager.registerSensor(new DevSensor());
		SensorManager.registerSensor(new InventorySensor());
		SensorManager.registerSensor(new SignSensor());
		SensorManager.registerSensor(new TankSensor());
		SensorManager.registerSensor(new MinecartSensor());
		SensorManager.registerSensor(new WorldSensor());
		SensorManager.registerSensor(new SonicSensor());
		if (Loader.isModLoaded("BuildCraft|Core"))
			SensorManager.registerSensor(new BuildCraftSensor());
		
		if (Loader.isModLoaded("IC2"))
			SensorManager.registerSensor(new IndustrialCraftSensor());
		
		if (Loader.isModLoaded("Thaumcraft"))
			SensorManager.registerSensor(new ThaumCraftSensor());
		
		
		ItemSensorCard.init();
		
		if (Loader.isModLoaded("Thaumcraft")) {
			Item tcItem = ThaumcraftHelper.getGoggles();
			if (tcItem == null) {
				tcItem = Item.eyeOfEnder;
			}
			RecipeHelper.addTier1CardRecipe(ItemSensorCard.THAUMCRAFT_TIER_1, new ItemStack(tcItem));
		}
		
		if (Loader.isModLoaded("IC2")) {
			ItemStack icStack = IC2Helper.getItemStack("copperCableItem");
			if (icStack == null) {
				icStack = new ItemStack(Item.flint);
			}
			RecipeHelper.addTier1CardRecipe(ItemSensorCard.INDUSTRIALCRAFT_TIER_1, icStack);
		}
		
		if (Loader.isModLoaded("BuildCraft|Core")) {
			Item bcItem = BCHelper.getStoneGear();
			if (bcItem == null) {
				bcItem = Item.coal;
			}
			RecipeHelper.addTier1CardRecipe(ItemSensorCard.BUILDCRAFT_TIER_1, new ItemStack(bcItem));
		}
		
		RecipeHelper.addTier1CardRecipe(ItemSensorCard.WORLD_TIER_1, new ItemStack(Item.enderPearl));
		RecipeHelper.addTier1CardRecipe(ItemSensorCard.MINECART_TIER_1, new ItemStack(Item.minecartEmpty));
		RecipeHelper.addTier1CardRecipe(ItemSensorCard.TANK_TIER_1, new ItemStack(Item.bucketEmpty));
		RecipeHelper.addTier1CardRecipe(ItemSensorCard.SIGN_TIER_1, new ItemStack(Item.sign));
		RecipeHelper.addTier1CardRecipe(ItemSensorCard.INVENTORY_TIER_1, "plankWood");
		RecipeHelper.addTier1CardRecipe(ItemSensorCard.DROPPED_TIER_1, new ItemStack(Item.slimeBall));
		RecipeHelper.addTier1CardRecipe(ItemSensorCard.PROXIMITY_TIER_1, new ItemStack(Block.pressurePlateStone));
		RecipeHelper.addTier1CardRecipe(ItemSensorCard.SONIC_TIER_1, new ItemStack(Block.music));
		RecipeHelper.addTierUpgradeRecipes();
		RecipeHelper.addUpgradeItemRecipes();	
	
		// register GUI handler
		NetworkRegistry.instance().registerGuiHandler(OpenCCSensors.instance, new GuiHandler());

		setupLanguages();
		setupLuaFiles();

	}

	public void registerRenderInformation() {
	}

	// Language setup (thinking ahead here!)
	private void setupLanguages() {

		ArrayList arrayList = new ArrayList();

		try {
			InputStream input = CommonProxy.class
					.getResourceAsStream("/openccsensors/resources/languages/languages.txt");

			if (input == null) {
				OCSLog.warn("Cannot load languages.txt. Names may not be displayed correctly.");
				return;
			}

			BufferedReader var2 = new BufferedReader(new InputStreamReader(
					input, "UTF-8"));

			for (String var3 = var2.readLine(); var3 != null; var3 = var2
					.readLine()) {
				arrayList.add(var3);
			}
		} catch (IOException var5) {
			OCSLog.warn("Cannot load languages.txt. Names may not be displayed correctly. Stack trace follows.");
			var5.printStackTrace();
			return;
		}

		Iterator iterator = arrayList.iterator();

		while (iterator.hasNext()) {
			String langString = (String) iterator.next();
			URL url = CommonProxy.class
					.getResource("/openccsensors/resources/languages/"
							+ langString + ".lang");
			if (url == null) {
				OCSLog.warn(
						"Skipping loading of language %s - language file not found.",
						langString);
				continue;
			}

			LanguageRegistry.instance()
					.loadLocalization(url, langString, false);
		}

	}

	private void setupLuaFiles() {

		File modFile = FMLCommonHandler.instance()
				.findContainerFor(OpenCCSensors.instance).getSource();

		File baseFile = getBase();

		String beginStr = "openccsensors/resources/lua/";
		String destFolder = "mods\\OCSLua\\lua";
		if (modFile.isDirectory()) {
			File srcFile = new File(modFile, beginStr);

			File destFile = new File(baseFile, destFolder);

			try {
				copy(srcFile, destFile);
			} catch (IOException e) {
				OCSLog.warn("Error while copying Lua files. Peripheral may not automount Lua files! Exception follows.");
				e.printStackTrace();
			}

		} else {
			extractZipToLocation(modFile, beginStr, destFolder);
		}

	}

}
