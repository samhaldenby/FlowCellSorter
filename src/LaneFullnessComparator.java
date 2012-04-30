import java.util.Comparator;

/** Comparator for sorting lanes by fullness
*
* @author Sam Haldenby
*/
public class LaneFullnessComparator implements Comparator<Lane>{
	public int compare(Lane lane1, Lane lane2){
		return (int) ((lane1.currentFillLevel() - lane2.currentFillLevel())*10000.0);
	}
}
