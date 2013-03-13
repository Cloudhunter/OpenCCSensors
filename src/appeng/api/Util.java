package appeng.api;

import appeng.api.me.util.IAssemblerPattern;
import appeng.api.me.util.IMEInventory;
import appeng.api.me.util.IMEInventoryUtil;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper.UnableToFindClassException;
import cpw.mods.fml.relauncher.ReflectionHelper.UnableToFindMethodException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/*
 * Returns useful stuff of various sorts to access internal features and stuff, the meat of the important stuff is accessed here...
 */
public class Util
{
	static private Method isBlankPattern = null;
	static private Method isPattern = null;
	static private Method getPattern = null;

    static private Method isAssemblerPattern = null;
    static private Method getAssemblerPattern = null;

    static private Method isLocationPattern = null;
    static private Method getLocationPattern = null;

    static private Method isCell = null;
    static private Method GetCell = null;
    static private Method addBasicBlackList = null;
    static private Method GetCellArray = null;
    static private Method getWrapper = null;
    
    static private Method getCellRegistry = null;
    static private Method getExternalStorageRegistry = null;
    static private Method getGrinderRecipeManage = null;
    static private Method getIMEInventoryUtil = null;
    
    static private Method updateGridAt = null;
    
    public static IMEInventoryUtil getIMEInventoryUtil( IMEInventory ime )
    {
        try
        {
            if (getIMEInventoryUtil == null)
            {
            	getIMEInventoryUtil = ReflectionHelper.getClass(Util.class.getClassLoader(), "appeng.common.AppEng").getMethod("getIMEInventoryUtil");
            }
            
            return (IMEInventoryUtil)getIMEInventoryUtil.invoke(null,ime);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    public static void updateGridAt( World w, int x, int y, int z )
    {
        try
        {
            if (updateGridAt == null)
            {
             	updateGridAt = ReflectionHelper.getClass(Util.class.getClassLoader(), "appeng.common.AppEng").getMethod("updateGridAt", World.class, int.class, int.class, int.class );
            }
            
            updateGridAt.invoke( null, w, x, y, z );
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
    }
    
    public static IExternalStorageRegistry getExternalStorageRegistry()
    {
        try
        {
            if (getExternalStorageRegistry == null)
            {
            	getExternalStorageRegistry = ReflectionHelper.getClass(Util.class.getClassLoader(), "appeng.common.AppEng").getMethod("getExternalStorageRegistry" );
            }
            
            return (IExternalStorageRegistry)getExternalStorageRegistry.invoke(null);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static ICellRegistry getCellRegistry()
    {
        try
        {
            if (getCellRegistry == null)
            {
            	getCellRegistry = ReflectionHelper.getClass(Util.class.getClassLoader(), "appeng.common.AppEng").getMethod("getCellRegistry" );
            }
            
            return (ICellRegistry)getCellRegistry.invoke(null);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    public static IGrinderRecipeManager getGrinderRecipeManage()
    {
        try
        {
            if (getGrinderRecipeManage == null)
            {
            	getGrinderRecipeManage = ReflectionHelper.getClass(Util.class.getClassLoader(), "appeng.common.AppEng").getMethod("getGrinderRecipeManage" );
            }

            return (IGrinderRecipeManager)getGrinderRecipeManage.invoke(null);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    // is it an IAssemblerPattern?
    public static Boolean isBlankPattern(ItemStack i)
    {
        try
        {
            if (isBlankPattern == null)
            {
            	isBlankPattern = ReflectionHelper.getClass(Util.class.getClassLoader(), "appeng.me.AssemblerPatternInventory").getMethod("isBlankPattern", ItemStack.class);
            }

            return (Boolean)isBlankPattern.invoke(null, i);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    // is it an IAssemblerPattern?
    public static Boolean isLocationPattern(ItemStack i)
    {
        try
        {
            if (isLocationPattern == null)
            {
            	isLocationPattern = ReflectionHelper.getClass(Util.class.getClassLoader(), "appeng.me.LocationPatternInventory").getMethod("isLocationPattern", ItemStack.class);
            }

            return (Boolean)isLocationPattern.invoke(null, i);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    // is it an IAssemblerPattern?
    public static Boolean isAssemblerPattern(ItemStack i)
    {
        try
        {
            if (isAssemblerPattern == null)
            {
                isAssemblerPattern = ReflectionHelper.getClass(Util.class.getClassLoader(), "appeng.me.AssemblerPatternInventory").getMethod("isAssemblerPattern", ItemStack.class);
            }

            return (Boolean)isAssemblerPattern.invoke(null, i);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    // get the IAssemblerPattern of the Assembly Pattern.
    public static IAssemblerPattern getAssemblerPattern(ItemStack i)
    {
        try
        {
            if (getAssemblerPattern == null)
            {
                getAssemblerPattern = ReflectionHelper.getClass(Util.class.getClassLoader(), "appeng.me.AssemblerPatternInventory").getMethod("getAssemblerPattern", ItemStack.class);
            }

            return (IAssemblerPattern)getAssemblerPattern.invoke(null, i);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    // is it a IStorageCell?
    public static Boolean isBasicCell(ItemStack i)
    {
        try
        {
            if (isCell == null)
            {
                isCell = ReflectionHelper.getClass(Util.class.getClassLoader(), "appeng.me.CellInventory").getMethod("isCell", ItemStack.class);
            }

            return (Boolean)isCell.invoke(null, i);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    public static Boolean isCell(ItemStack i)
    {
    	return getCellRegistry().isCellHandled( i );
    }
    
    public static IMEInventory getCell(ItemStack i)
    {
    	return getCellRegistry().getHandlerForCell( i );
    }
    
    // lets you access internal storage of IStorageCell's
    public static IMEInventory getBasicCell(ItemStack i)
    {
        try
        {
            if (i == null)
            {
                return null;
            }
            
            if (GetCell == null)
            {
                GetCell = ReflectionHelper.getClass(Util.class.getClassLoader(), "appeng.me.CellInventory").getMethod("getCell", ItemStack.class);
            }

            return (IMEInventory)GetCell.invoke(null, i);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    // lets you access internal storage of IStorageCell's
    public static void addBasicBlackList( int ItemID, int Meta )
    {
        try
        {
            if (addBasicBlackList == null)
            {
            	addBasicBlackList = ReflectionHelper.getClass(Util.class.getClassLoader(), "appeng.me.CellInventory").getMethod("addBasicBlackList", int.class, int.class );
            }
            
            addBasicBlackList.invoke(null, ItemID, Meta );
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
    }
    
}
