import java.util.Comparator;

/** Comparator for sorting samples by size
*
* @author Sam Haldenby
*/
public class SampleSizeComparator implements Comparator<Sample>{
	public int compare(Sample s1, Sample s2){
		return (int) ((s1.Reads() - s2.Reads())*10000f);
	}
}
