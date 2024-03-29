import java.util.Comparator;

/** Comparator for comparing barcode sequences
*
* @author Sam Haldenby
*/
public class LaneBarcodeComparator implements Comparator<Sample> {

	@Override
	public int compare(Sample s1, Sample s2) {
		return (s1.Barcode().hashCode() - s2.Barcode().hashCode());

	}

}
