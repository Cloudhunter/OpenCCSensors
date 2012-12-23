package openccsensors.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import openccsensors.OpenCCSensors;
import openccsensors.common.core.OCSLog;
import openccsensors.common.sensorperipheral.ContainerSensor;
import openccsensors.common.sensorperipheral.BlockSensor;
import openccsensors.common.sensorperipheral.TurtleUpgradeSensor;
import openccsensors.common.sensorperipheral.TileEntitySensor;

public class CommonProxy
{

	public void init() 
	{
		// create block and register it
		OpenCCSensors.Blocks.sensorBlock = new BlockSensor( OpenCCSensors.Config.sensorBlockID, Material.rock );
		GameRegistry.registerBlock(OpenCCSensors.Blocks.sensorBlock, "OCS");
		GameRegistry.registerTileEntity(TileEntitySensor.class, "sensor");
		GameRegistry.addRecipe(
				new ItemStack(OpenCCSensors.Blocks.sensorBlock, 1, 0),
				"ooo",
				"ror",
				"sss",
				'o', new ItemStack(Block.obsidian),
				'r', new ItemStack(Item.redstone),
				's',new ItemStack(Block.stone));
		
		// register turtle peripheral if applicable
		if (OpenCCSensors.Config.turtlePeripheralEnabled)
		{
			dan200.turtle.api.TurtleAPI.registerUpgrade(new TurtleUpgradeSensor());
		}
		
		// register GUI handler
		NetworkRegistry.instance().registerGuiHandler( OpenCCSensors.instance, new GuiHandler() );
		
		// setup languages
		setupLanguages();
		
	}
	
	// Language setup (thinking ahead here!)
	private void setupLanguages()
	{
		
		ArrayList arrayList = new ArrayList();
		
        try
        {
        	InputStream input = CommonProxy.class.getResourceAsStream("/openccsensors/resources/languages/languages.txt");
        	
        	if (input == null)
        	{
        		OCSLog.warn("Cannot load languages.txt. Names may not be displayed correctly.");
        		return;
        	}
        	
        	
            BufferedReader var2 = new BufferedReader(new InputStreamReader(input, "UTF-8"));

            for (String var3 = var2.readLine(); var3 != null; var3 = var2.readLine())
            {
                arrayList.add(var3);
            }
        }
        catch (IOException var5)
        {
        	OCSLog.warn("Cannot load languages.txt. Names may not be displayed correctly. Stack trace follows.");
            var5.printStackTrace();
            return;
        }
		
        Iterator iterator = arrayList.iterator();
        
        while(iterator.hasNext())
        {
        	String langString = (String) iterator.next();
        	URL url = CommonProxy.class.getResource("/openccsensors/resources/languages/" + langString + ".lang");
        	if (url == null)
        	{
        		OCSLog.warn("Skipping loading of language %s - language file not found.", langString);
        		continue;
        	}
        	
        	LanguageRegistry.instance().loadLocalization(url, langString, false );
        }

	}
	
	public void registerRenderInformation()
	{
	}
	
	// GUI Stuff
	public Object getGui( InventoryPlayer inventory, TileEntitySensor tileentity )
	{
		// returns nothing on the common proxy - will only return the GUI on the client proxy
		return null;
	}
	
	private class GuiHandler implements IGuiHandler
	{

		@Override
		public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
		{
			TileEntity tile = world.getBlockTileEntity( x, y, z );
			
			if (tile != null && tile instanceof TileEntitySensor)
			{
				return new ContainerSensor(player.inventory, (TileEntitySensor) tile);
			}
			
			return null;
		}

		@Override
		public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z)
		{
			TileEntity tile = world.getBlockTileEntity( x, y, z );
			
			if (tile != null && tile instanceof TileEntitySensor)
			{
				return getGui(player.inventory, (TileEntitySensor) tile);
			}
			
			return null;
		}
		
	}

}
