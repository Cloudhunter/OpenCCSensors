package appeng.api.me.tiles;

/*
 * Used to signifiy which color a particular IGridTileEntity or IGridMachine is, you must implement both, if you wish to have color bias.
 */
public interface IColoredMETile
{
	public static String[] Colors = {
		"",
		"Black",
		"White",
		"Brown",
		"Red",
		"Yellow",
		"Green"
	};

	public static int[] cableColorTexturesDark = {
		127,
		143,
		159,
		175,
		191,
		207,
		223
	};

	public static int[] cableColorTexturesLit = {
		20,
		31,
		47,
		63,
		79,
		95,
		111
	};

	void setColor( int offset );

	int getColor();
}
