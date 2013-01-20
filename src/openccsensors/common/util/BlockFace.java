package openccsensors.common.util;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Represents the face of a block
 */
public enum BlockFace {
    NORTH(0, 0, -1),
    EAST(1, 0, 0),
    SOUTH(0, 0, 1),
    WEST(-1, 0, 0),
    UP(0, 1, 0),
    DOWN(0, -1, 0),
    NORTH_EAST(NORTH, EAST),
    NORTH_WEST(NORTH, WEST),
    SOUTH_EAST(SOUTH, EAST),
    SOUTH_WEST(SOUTH, WEST),
//    WEST_NORTH_WEST(WEST, NORTH_WEST),
//    NORTH_NORTH_WEST(NORTH, NORTH_WEST),
//    NORTH_NORTH_EAST(NORTH, NORTH_EAST),
//    EAST_NORTH_EAST(EAST, NORTH_EAST),
//    EAST_SOUTH_EAST(EAST, SOUTH_EAST),
//    SOUTH_SOUTH_EAST(SOUTH, SOUTH_EAST),
//    SOUTH_SOUTH_WEST(SOUTH, SOUTH_WEST),
//    WEST_SOUTH_WEST(WEST, SOUTH_WEST),
    UP_NORTH(0, 1, -1),
    UP_EAST(1, 1, 0),
    UP_SOUTH(0, 1, 1),
    UP_WEST(-1, 1, 0),
    UP_NORTH_EAST(NORTH_EAST,UP),
    UP_NORTH_WEST(NORTH_WEST,UP),
    UP_SOUTH_EAST(SOUTH_EAST,UP),
    UP_SOUTH_WEST(SOUTH_WEST,UP),
//    UP_WEST_NORTH_WEST(WEST_NORTH_WEST,UP),
//    UP_NORTH_NORTH_WEST(NORTH_NORTH_WEST,UP),
//    UP_NORTH_NORTH_EAST(NORTH_NORTH_EAST,UP),
//    UP_EAST_NORTH_EAST(EAST_NORTH_EAST,UP),
//    UP_EAST_SOUTH_EAST(EAST_SOUTH_EAST,UP),
//    UP_SOUTH_SOUTH_EAST(SOUTH_SOUTH_EAST,UP),
//    UP_SOUTH_SOUTH_WEST(SOUTH_SOUTH_WEST,UP),
//    UP_WEST_SOUTH_WEST(WEST_SOUTH_WEST,UP),
    DOWN_NORTH(0, 1, -1),
    DOWN_EAST(1, 1, 0),
    DOWN_SOUTH(0, 1, 1),
    DOWN_WEST(-1, 1, 0),
    DOWN_NORTH_EAST(NORTH_EAST,DOWN),
    DOWN_NORTH_WEST(NORTH_WEST,DOWN),
    DOWN_SOUTH_EAST(SOUTH_EAST,DOWN),
    DOWN_SOUTH_WEST(SOUTH_WEST,DOWN),
//    DOWN_WEST_NORTH_WEST(WEST_NORTH_WEST,DOWN),
//    DOWN_NORTH_NORTH_WEST(NORTH_NORTH_WEST,DOWN),
//    DOWN_NORTH_NORTH_EAST(NORTH_NORTH_EAST,DOWN),
//    DOWN_EAST_NORTH_EAST(EAST_NORTH_EAST,DOWN),
//    DOWN_EAST_SOUTH_EAST(EAST_SOUTH_EAST,DOWN),
//    DOWN_SOUTH_SOUTH_EAST(SOUTH_SOUTH_EAST,DOWN),
//    DOWN_SOUTH_SOUTH_WEST(SOUTH_SOUTH_WEST,DOWN),
//    DOWN_WEST_SOUTH_WEST(WEST_SOUTH_WEST,DOWN),
    SELF(0, 0, 0);
    
    private final int modX;
    private final int modY;
    private final int modZ;
    
    
    private static final Map<String, BlockFace> nameToValueMap =
            new HashMap<String, BlockFace>();

    static {
        for (BlockFace value : EnumSet.allOf(BlockFace.class)) {
            nameToValueMap.put(value.name(), value);
        }
    }

    public static BlockFace forName(String name) {
        return nameToValueMap.get(name);
    }
    
    private static final  BlockFace[][][] coordsToValueMap =
            new BlockFace[3][3][3];

    static {
        for (BlockFace value : EnumSet.allOf(BlockFace.class)) {
            coordsToValueMap[value.modX+1][value.modY+1][value.modZ+1]=value;
        }
    }

    public static BlockFace forCoords(int x,int y, int z) {
        return coordsToValueMap[x+1][y+1][z+1];
    }

    private BlockFace(final int modX, final int modY, final int modZ) {
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
    }

    private BlockFace(final BlockFace face1, final BlockFace face2) {
        this.modX = face1.getModX() + face2.getModX();
        this.modY = face1.getModY() + face2.getModY();
        this.modZ = face1.getModZ() + face2.getModZ();
    }

    /**
     * Get the amount of X-coordinates to modify to get the represented block
     *
     * @return Amount of X-coordinates to modify
     */
    public int getModX() {
        return modX;
    }

    /**
     * Get the amount of Y-coordinates to modify to get the represented block
     *
     * @return Amount of Y-coordinates to modify
     */
    public int getModY() {
        return modY;
    }

    /**
     * Get the amount of Z-coordinates to modify to get the represented block
     *
     * @return Amount of Z-coordinates to modify
     */
    public int getModZ() {
        return modZ;
    }
    public static BlockFace getFaceIfHasFaceCoords(Vector vec){
    	for (BlockFace face : BlockFace.values()){
    		if (face.modX==vec.getX()&&
    			face.modY==vec.getY()&&
    			face.modZ==vec.getZ()){
    			return face;
    		}
    	}
    	return null;
    }

    public BlockFace getOppositeFace() {
    	return forCoords(modX*-1,modY*-1,modZ*-1);
    }
    
    
	/**
	 * Notch uses a 0-5 to mean DOWN, UP, EAST, WEST, NORTH, SOUTH
	 * in that order all over. This method is convenience to convert for us.
	 *
	 * @return BlockFace the BlockFace represented by this number
	 */
	public static BlockFace notchToBlockFace(int notch) {
		switch (notch) {
		case 0:
			return BlockFace.DOWN;
		case 1:
			return BlockFace.UP;
		case 2:
			return BlockFace.EAST;
		case 3:
			return BlockFace.WEST;
		case 4:
			return BlockFace.NORTH;
		case 5:
			return BlockFace.SOUTH;
		default:
			return BlockFace.SELF;
		}
	}

	public static int blockFaceToNotch(BlockFace face) {
		switch (face) {
		case DOWN:
			return 0;
		case UP:
			return 1;
		case EAST:
			return 2;
		case WEST:
			return 3;
		case NORTH:
			return 4;
		case SOUTH:
			return 5;
		default:
			return 7; // Good as anything here, but technically invalid
		}
	}
}