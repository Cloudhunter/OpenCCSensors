package openccsensors.common.sensor;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockMelon;
import net.minecraft.block.BlockNetherStalk;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockStem;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.Ic2Utils;

public class CropSensor implements ISensor, IRequiresIconLoading {

	public static final String STATUS_NEW = "New";
	public static final String STATUS_GROWING = "Growing";
	public static final String STATUS_GROWN = "Grown";
	
	class CropTarget {
		public int X;
		public int Y;
		public int Z;
		public Block block;
		public int metadata;
		public CropTarget (int x, int y, int z, Block b, int metadata) {
			this.X = x;
			this.Y = y;
			this.Z = z;
			this.block = b;
			this.metadata = metadata;
		}
	}
	
	private Icon icon;
	
	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:crop");
	}

	@Override
	public HashMap getDetails(World world, Object obj, Vec3 sensorPos, boolean additional) {

		HashMap response = new HashMap();
		
		if (obj instanceof CropTarget) {

			CropTarget target = (CropTarget) obj;
			
			HashMap position = new HashMap();
			
			position.put("X", target.X - sensorPos.xCoord);
			position.put("Y", target.Y - sensorPos.yCoord);
			position.put("Z", target.Z - sensorPos.zCoord);
			response.put("Position", position);
			
			response.put("Name", target.block.getLocalizedName());
			response.put("RawName", target.block.getUnlocalizedName());
			response.put("Size", target.metadata);
			if (target.block instanceof BlockCrops || target.block instanceof BlockStem) {
				if (target.metadata == 0) {
					response.put("Status", STATUS_NEW);
				}else if (target.metadata == 7) {
					response.put("Status", STATUS_GROWN);
				}else {
					response.put("Status", STATUS_GROWING);
				}
			}else if (target.block instanceof BlockNetherStalk) {
				if (target.metadata == 0) {
					response.put("Status", STATUS_NEW);
				}else {
					response.put("Status", STATUS_GROWN);
				}
			}else if (target.block instanceof BlockPumpkin || target.block instanceof BlockMelon) {
				response.put("Status", STATUS_GROWN);
			}
		}else if (ModLoader.isModLoaded("IC2") && obj instanceof TileEntity) {
			response.putAll(Ic2Utils.getCropDetails(obj, sensorPos, additional));
		}
		
		return response;
		
	}

	@Override
	public String[] getCustomMethods(ISensorTier tier) {
		return null;
	}

	@Override
	public Object callCustomMethod(World world, Vec3 location, int methodID,
			Object[] args, ISensorTier tier) throws Exception {
		return null;
	}

	@Override
	public String getName() {
		return "cropCard";
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Item.wheat);
	}

	@Override
	public HashMap getTargets(World world, Vec3 location, ISensorTier tier) {

		HashMap targets = new HashMap();
		int distance = (int) tier.getMultiplier();
		
		for (int x = -distance; x <= distance; x++) {
			for (int y = -distance; y <= distance; y++) {
				for (int z = -distance; z <= distance; z++) {

					int tileX = x + (int)location.xCoord;
					int tileY = y + (int)location.yCoord;
					int tileZ = z + (int)location.zCoord;

					String name = String.format("%s,%s,%s", x, y, z);

					Block b = Block.blocksList[world.getBlockId(tileX, tileY, tileZ)];
					
					if (b != null && (
							b instanceof BlockCrops ||
							b instanceof BlockNetherStalk ||
							b instanceof BlockStem ||
							b instanceof BlockPumpkin ||
							b instanceof BlockMelon
							)) {
						CropTarget potentialTarget = new CropTarget(
								tileX,
								tileY,
								tileZ,
								b,
								world.getBlockMetadata(tileX, tileY, tileZ)
						);
						targets.put(name, potentialTarget);
					}else {
						TileEntity tile = world.getBlockTileEntity(tileX, tileY, tileZ);
						if (tile != null & ModLoader.isModLoaded("IC2") && Ic2Utils.isValidCropTarget(tile)) {
							targets.put(name, tile);
						}
						
					}
				}
			}
		}
		return targets;
	}

}
