import java.util.Comparator;


public class BundleComparator implements Comparator<SampleBundle>{

	@Override
	public int compare(SampleBundle arg0, SampleBundle arg1) {
		return (int)((arg1.Size() - arg0.Size()) * 1000.0);
		// TODO Auto-generated method stub

	}

}
