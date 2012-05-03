/** Storage class for all constant variables
*
* @author Sam Haldenby
*/
public class Consts {
	/** 'Enumerated' value assigned to a Sample's pool field when sample is not in a pool*/
	public static final int NO_POOL = -1;
	
	/** Lane capacity as a proportion. Slightly greater than 1 to cope with rounding errors */
	protected static final double LANE_CAPACITY = 1.01d;
	
	/** Software version number */
	public static final String VERSION = "0.4.7";

	/** 'Enumerated' values for determining lane colouring options */
	public static final int COLOUR_BY_BARCODE = 0;
	public static final int COLOUR_BY_POOL = 1;
	public static final int COLOUR_BY_SPACE = 2;
}

