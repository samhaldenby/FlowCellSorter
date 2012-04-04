import java.io.IOException;
import java.util.Comparator;

/** Comparator for sorting SampleBundles by size
*
* @author Sam Haldenby
*/
public class BundleComparator implements Comparator<SampleBundle>{
	
	/** Compares bundles by size
	 * 
	 * @throws IOException 		
	 * @param bundle1		First bundle
	 * @param bundle2		Second bundle
	 * @return	Comparison result
	 */
	@Override
	public int compare(SampleBundle bundle1, SampleBundle bundle2) {
		return (int)((bundle2.Size() - bundle1.Size()) * 1000.0);		//multiplied by 1000 to ensure sorting is correct to size of 4 decimal places

	}

}
