package openccsensors.common.sensors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractSet;
import java.lang.Math;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowing;
import net.minecraft.block.BlockStationary;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.registry.GameRegistry;

import openccsensors.common.api.ISensorAccess;

import openccsensors.common.core.OCSLog;
import openccsensors.common.helper.SensorHelper;
import openccsensors.common.helper.TargetHelper;


import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.api.SensorUpgradeTier;


import openccsensors.common.util.RayIterator;
import openccsensors.common.util.Vector;
import openccsensors.common.util.BlockFace;

import static openccsensors.common.util.NumberConversions.*;

//NOTE: the documentation below is not ideal for users.

/**
 *  The sonic sensor is an emulation of real-life sonic sensors in MC.
 * In general,by emitting an 'sonic pulse' from the center of the sensor, it can scan in any direction described by a 3D vector,
 * returning to the Lua-side the serialization of a Scan object (see Scan subclass).
 *This serialization can contain info as whether the obstacle hit is an entity, a liquid, a solid, a unloaded chunk, a non-existent chunk, or something else.
 *@author CoolisTheName007
 */
public class SonicSensor implements ISensor {
	
	
	//These constants are updated as the calls' SensorUpgradeTier changes.
	
	private static final double BASE_RANGE=10;
	
	private static double CURRENT_RANGE=BASE_RANGE;
	
	private static int DIAMETER=round(2*CURRENT_RANGE-1);
	
	private static double MAX_DISSIPATION=CURRENT_RANGE*0.5d;
	
	private static int MAX_REQUESTS= round(Math.pow(DIAMETER, 3));
	
	private static void updateRange(SensorUpgradeTier upgrade){
		CURRENT_RANGE=BASE_RANGE*upgrade.getMultiplier();
		DIAMETER=round(2*CURRENT_RANGE-1);
		MAX_DISSIPATION=CURRENT_RANGE/0.5d;
		MAX_REQUESTS= round(Math.pow(DIAMETER, 3));
	}
	
	@Override
	public String[] getCustomMethods(SensorUpgradeTier upgrade) {
		return new String[] {
				"scanDirection",//see method of the same name
				"scanSeveral", //see method of the same name
				"scanSphere", //see method of the same name
				"getFacing",//return a textual description of the block facing
				"getMaxDissipation",//returns the maximum dissipation(see subclass Scan) of the sensor, according to the upgrade
				"getMaxRequests"//maximum number of requests scanSeveral can take
				};
	}
	
	@Override
	public Object callCustomMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args, SensorUpgradeTier upgrade) throws Exception {
		updateRange(upgrade);
		switch(methodID)
		{
			case 0:
				return scanDirection(world,x,y,z,args);
			case 1:
				return scanSeveral(world,x,y,z,args);
			case 2:
				return scanSphere(world,x,y,z,args);
			case 3:
				return BlockFace.notchToBlockFace(2+sensor.getSensorEnvironment().getFacing()).toString();
			case 4:
				return MAX_DISSIPATION;
			case 5:
				return MAX_REQUESTS;
			default:
				return null;
		}
	}
	@Override
	public HashMap<String, ArrayList<ISensorTarget>> getSurroundingTargets(
			World world, int x, int y, int z, SensorUpgradeTier upgrade){
		updateRange(upgrade);
		HashMap<String, ArrayList<ISensorTarget>> ret = new HashMap();
		HashMap placeholder= new HashMap();
		Vector org=new Vector(x,y,z);
		for (BlockFace face : BlockFace.values()){
			if (face!=BlockFace.SELF){
				ArrayList<ISensorTarget> arr=new ArrayList();
				try {
					arr.add(
							new SonicTarget(
									world,
									org,
									new Vector(face.getModX(),face.getModY(),face.getModZ())
									)
							)
					;
				} catch (Exception e) {
					//this WON'T happen
					e.printStackTrace();
				}
				ret.put(face.toString(),arr);
			}
		}
		return ret;
	}
	
	/**
	 * Takes a direction, some options (see Scan subclass), and return the serialization of a Scan object.
	 * Lua-side arguments: (number x, number y,number z,(optional) boolean hitMedium,(optional) boolean hitEntity, (optional) boolean rayTrace)
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Map scanDirection(World world, int x, int y, int z, Object[] args) throws Exception{
		if ((args.length < 3) || (!(args[0] instanceof Double)) || (!(args[1] instanceof Double)) || (!(args[2] instanceof Double)))
		{	
			
			throw new Exception("invalid arguments: must provide a non-zero vector coordinates in the form of  a varag (x,y,z,(optional args)...");
		}
		Vector org =new Vector(x, y, z);
		Vector dir =new Vector((Double) args[0],(Double) args[1] , (Double) args[2]).normalize();
		
		return new Scan(world,org,dir,(args.length>=4?(args[3] instanceof Boolean?(Boolean)args[3]:true):true),(args.length>=5?(args[4] instanceof Boolean?(Boolean)args[4]:true):true),(args.length>=6?(args[5] instanceof Boolean?(Boolean)args[5]:true):true)).serialize();
		
	}
	
	/**
	 * Performs scanDirection in several directions, with the same options;
	 * Lua-side arguments:
	 * 	(
	 * 		boolean hitMedium, boolean hitEntity, boolean rayTrace,
	 * 	 	number x, number y,number z,
	 * 		(optional) number x2,(optional) number y2,(optional) number z2),
	 * 		...
	 * 	)
	 * Lua-side return: 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param args
	 * @return (lua-side) integer i->serialized Scan in the i-th direction
	 * @throws Exception
	 */
	
	public static Map scanSeveral(World world, int x, int y, int z, Object[] args) throws Exception{
		Vector org = new Vector(x, y, z);
		Vector dir = new Vector();
		Map<Integer,Map> ret = new HashMap();
		if (!(args.length>=6&&(args[0] instanceof Boolean)&&(args[1] instanceof Boolean)&&(args[2] instanceof Boolean))){
			throw new Exception("invalid arguments: must provide 3 booleans representing options folllowed by sequence of 3D non-zero vector coordinates in the form of  a vararg: (hitMedium,hitEntities,rayTrace,x1,y1,z1,x2,y2,z2,x3...)");
		}
		boolean hitMedium=(Boolean) args[0];
		boolean hitEntities=(Boolean) args[1];
		boolean rayTrace=(Boolean) args[2];
		
		int i = 0;
		for (i=0;i<args.length-3;i=i+3){
			if (!(args.length-3>=2+i+1 && args[i+3] instanceof Double && args[i+1+3] instanceof Double && args[i+2+3] instanceof Double)){
				throw new Exception("invalid arguments: must provide 3 booleans representing options folllowed by sequence of 3D non-zero vector coordinates in the form of  a vararg: (hitMedium,hitEntities,rayTrace,x1,y1,z1,x2,y2,z2,x3...)");
			}
			Double x_ = (Double) args[i+3];
			Double y_ =(Double) args[i+1+3];
			Double z_ =(Double) args[i+2+3];
			
			dir.set(x_,y_,z_);
			Scan scan = new Scan(world, org, dir,hitMedium,hitEntities,rayTrace);
			if (scan!=null){
				ret.put(i/3+1,scan.serialize());
			}
			if (i>MAX_REQUESTS){
				throw new Exception("MAX_REQUESTS="+MAX_REQUESTS+" exceeded: requested "+(args.length-3));
			}
		}
		return ret;
	}
		
	/**
	 * Scans a spherical volume with an interval of 1 degree between each ray.
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param args
	 * @return an HashMap whose keys are the concatenated coords of
	 * @throws Exception 
	 */
	public static Map scanSphere(World world, int x, int y, int z,Object[] args) throws Exception{
		double angle_step = 2*Math.PI/360;
		Vector org = new Vector(x, y, z);
		Vector dir = new Vector();
		
		Map ret = new HashMap();
		
		double H_steps=2*Math.PI/angle_step;
		double V_steps=Math.PI/angle_step;
		
		double inclination ;
		double azimuth ;
		
		int k=0;
		for (double i=0;i<=H_steps;i=i+1){
			azimuth=i/H_steps*2*Math.PI;
			for (double j=0;j<=V_steps;j=j+1){
				inclination=j/V_steps*Math.PI;
				
				dir.setFromSpherical(1, inclination, azimuth);
				
				
				Scan scan = new Scan(world, org, dir);
				if (scan.result==Scan.Result.HIT){
					ret.put(scan.vec.toString(),scan.serialize());
				}
				
				if (++k>MAX_REQUESTS){
					return null;
				}
			}
		}
		
		return ret;
	}
	
	/**
	 * Effectuates scans and can serialize them.
	 * 
	 * @author CoolisTheName007
	 *
	 */
	private static class Scan{
		
		/**
		 *Describes the result of a scan. 
		 */
		public enum Result{
			HIT,//hit something
			NO_REPLY, //out of range
		}
		public Result result=null;
		
		/**
		 * The sonic beam interacts differently to different transport mediums, which can be either air or liquids.
		 * Air dissipates the beam twice faster than liquids, which give the maximum range. 
		 * By default, a change of medium makes the beam return, but that can be changed by setting the option hitMedium to false (see constructor)
		 */
		public static enum Medium{
			//when hitMedium is true (default)
			LIQUID(0.5),
			AIR(1),
			
			//only when hitMedium is false
			IGNORED(1),
			
			//on all scans when the obstacle hit is adjacent to the sensor
			SELF(1);
			
			double dissipation=1;
			Medium(double res){
				this.dissipation=res;
			}
		}
		public Medium medium=Medium.SELF;
		
		/**
		 *Describes the type of obstacles the beam may bounce of.
		 */
		public static enum Hit{
			LIQUID,//block.isLiquid()==true
			AIR,//no block or id==0
			SOLID,//block.isSolid() == true
			ENTITY,//it's an entity
			CHUNK_NON_GENERATED,
			CHUNK_NON_LOADED,
			NON_RECOGNISED,//does not falls into any of the categories
		}
		public Hit hit=null;
		
		
		/**
		 *The vector coordinates of the hit, relative to the sensor MC coordinates, without rotation.
		 *Examples: rayTrace=true(default), the beam hits a block and the direction is (0,1,0) (=UP).
		 *				The vector coordinates are something like (0.5,y,0.5).
		 *			rayTrace=false, same situation:
		 *				(0,y,0)
		 */
		private static Vector VNaN=new Vector(Double.NaN,Double.NaN,Double.NaN);
		public Vector vec= VNaN;
		
		/**
		 * Emulates a sonic beam sent in a direction, that returns to the sensor as soon as it hits an obstacle.
		 * Obstacles are solid blocks (block.isSolid()), and can be some other stuff, depending on the boolean options.
		 * How much the beam can travel before not being able to return to the sensor depends on the medium on which it is moving (see Enum Medium)
		 * 
		 * 
		 * @param world
		 * @param org a vector of integer coordinates from where to start scanning.
		 * The beam is emitted from the center of the corresponding block, and won't hit anything until it leaves the same block. 
		 * @param dir in which direction to scan 
		 * @param hitMedium if true, a change of Medium will make the beam return. Otherwise, medium transitions are ignored, but dissipation is always 1 and no info is given about which medium the beam travelled through.
		 * @param hitEntities if true, entities will make the beam return.
		 * @param rayTrace if true, instead of returning integer coordinates for hits, each non-liquid block and entity in the way of the block will have it's
		 *  bounding box ray-traced. For instance, if rayTrace is true, pointing at the top-half of an half-slab block will make the beam continue onwards,
		 *  while if it's false, it will make the beam return. 
		 */
		Scan(World world, Vector org, Vector dir,boolean hitMedium, boolean hitEntities,boolean rayTrace) throws Exception{
			if (dir.lengthSquared()==0){
				throw new Exception("Can't point the sonic sensor at the zero vector direction");
			}
			
			if (!hitMedium){
				medium=Medium.IGNORED;
			}
			
			Vector start=org.clone().add(0.5,0.5,0.5);
					
			RayIterator iter= new RayIterator(start,dir);
			Vector cur=iter.next();
			
			Vec3 startVec3=start.toVec3();
			Vec3 endVec3= dir.normalize().multiply(MAX_DISSIPATION*2).add(start).toVec3();
			
			Block block;
			Block bprev=Block.dirt;
			int id;
			int prev_id=0;
			
			int x;
			int y;
			int z;
			double dis;
			while (true) {
			       cur=iter.next();
			       
			       	dis=cur.distance(org);
			       	if (dis/medium.dissipation>MAX_DISSIPATION){
			    	   	this.result=Result.NO_REPLY;
			    	   	return;
		   			}
			       x=cur.getBlockX();
			       y=cur.getBlockY();
			       z=cur.getBlockZ();
			       
			       	if (world.getChunkProvider().chunkExists(x>>4,z>>4)){
						Chunk chunk = world.getChunkFromBlockCoords(x, z);
						if (chunk.isChunkLoaded){
							id = world.getBlockId(x, y, z);
							block = Block.blocksList[id];
							
							if (id == 0 || block == null){
								if (hitMedium){
									if(medium!=Medium.SELF && medium!=Medium.AIR){
										this.hit=Hit.AIR;
										this.result=Result.HIT;
										this.vec=cur.subtract(org);
										return;
									}
									medium=Medium.AIR;
								}
								
								if(hitEntities){
									List<Entity> myList = new ArrayList<Entity>();
									chunk.getEntitiesWithinAABBForEntity(null, AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1), myList);
									
									
									for(Entity ent : myList){
										/*
										 *myEnt.getBoundingBox()!=myEnt.boundingbox. go figure...anyways, using the later I seem to be getting results. 
										 */
										AxisAlignedBB box = ent.boundingBox;
										if (box!=null){
											MovingObjectPosition mov = box.calculateIntercept(startVec3, endVec3);
											if (mov!=null){
												if (rayTrace){
													this.vec=new Vector(mov.hitVec).subtract(org);
												}else{
													this.vec=new Vector(mov.hitVec).floorVector().subtract(org);
												}
												this.hit=Hit.ENTITY;
												this.result=Result.HIT;
												return;
											}
										}
										
									}
								}
							}		
							else if(block.blockMaterial.isLiquid()){
								if (hitMedium){
									if(medium!=Medium.SELF && (medium!=Medium.LIQUID ||
											((id!=prev_id)&&
													(bprev instanceof BlockStationary &&
															id!=prev_id-1)
													||
													(bprev instanceof BlockFlowing &&
															prev_id!=id-1)
											)
									)){
										this.hit=Hit.LIQUID;
										this.result=Result.HIT;
										this.vec=cur.subtract(org);
										return;
									}
									medium=Medium.LIQUID;
								}
								if(hitEntities){
									List<Entity> myList = new ArrayList<Entity>();
									chunk.getEntitiesWithinAABBForEntity(null, AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1), myList);
									
									
									for(Entity ent : myList){
										
										/*
										 *myEnt.getBoundingBox()!=myEnt.boundingbox. go figure...anyways, using the later I seem to be getting results. 
										 */
										AxisAlignedBB box = ent.boundingBox;
										
										if (box!=null){
											MovingObjectPosition mov = box.calculateIntercept(startVec3, endVec3);
											if (mov!=null){
												if (rayTrace){
													this.vec=new Vector(mov.hitVec).subtract(org);
												}else{
													this.vec=new Vector(mov.hitVec).floorVector().subtract(org);
												}
												this.hit=Hit.ENTITY;
												this.result=Result.HIT;
												return;
											}
										}
										
									}
								}
							}else if (rayTrace){
									MovingObjectPosition mov = block.collisionRayTrace(world, x, y, z, startVec3,endVec3);
									if (mov!=null){
										this.vec=new Vector(mov.hitVec).subtract(org);
										this.hit=block.blockMaterial.isSolid()?Hit.SOLID:Hit.NON_RECOGNISED;
										this.result=Result.HIT;
										return;
									}
							}else if (block.blockMaterial.isSolid()){
								this.vec=cur.subtract(org);
								this.hit=Hit.SOLID;
								this.result=Result.HIT;
								return;
							}else{
								this.hit=Hit.NON_RECOGNISED;
								this.result=Result.HIT;
								this.vec=cur.subtract(org);
								return;
							}
						}else{
							this.hit=Hit.CHUNK_NON_LOADED;
							this.result=Result.HIT;
							this.vec=cur.subtract(org);
							return;
						}
					}else{
						this.hit=Hit.CHUNK_NON_GENERATED;
						this.result=Result.HIT;
						this.vec=cur.subtract(org);
						return;
					}
			       	prev_id=id;
			       	bprev=block;
			}
		}
		
		//Default behavior when some or no options are provided is to set them to true
		Scan(World world, Vector org, Vector dir) throws Exception{
			this(world, org, dir, true, true,true);
		}
		/**
		 * Serializes a scan into an HashMap (later converted by CC into a Lua table); it's the closest thing to getBasicDetails for general directions.
		 * It's fields correspond to the ones of the Scan object, but in lower-case, together with a field "distance" which is just the norm of the field vec.
		 * The 	"hit",
		 * 		"vec",
		 * 	and "distance" fields value's are only filled when the beam returns to the sensor (= after hitting an obstacle and being in range). 
		 * @return
		 */
		public HashMap serialize(){
			HashMap ret=new HashMap();
			ret.put("result", result.toString());
			ret.put("medium", medium.toString());
			if (hit!=null){
				ret.put("hit", hit.toString());
			}
			if (vec!=VNaN){
				ret.put("vec", vec.serialize());
				ret.put("distance", vec.length());
			}
			return ret;
		}
	}
	
	//Simple class for generating targets from scans
	private static class SonicTarget extends Scan implements ISensorTarget {
		
		
		SonicTarget(World world, Vector org, Vector dir) throws Exception {
			super(world, org, dir);
		}

		SonicTarget(World world, Vector org, Vector dir, boolean hitMedium,
				boolean hitEntities, boolean rayTrace) throws Exception {
			super(world, org, dir, hitMedium, hitEntities, rayTrace);
		}

		private static String[] trackable={"distance"};
		@Override
		public HashMap getBasicDetails(World world) {
			return this.serialize();
		}

		@Override
		public HashMap getExtendedDetails(World world) {
			return getBasicDetails(world);
		}

		@Override
		public String[] getTrackablePropertyNames() {
			return trackable;
		}
	}
}





