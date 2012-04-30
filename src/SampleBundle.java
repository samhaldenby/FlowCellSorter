import java.util.ArrayList;
import java.util.Iterator;

/** Class representing a grouping of samples as opposed to an individual one
*
* @author Sam Haldenby
*/
public class SampleBundle {
	private ArrayList<Sample> samples_;
	private double size_;
	private int bundleHash_=0;
	
	public SampleBundle(ArrayList<Sample> samples){
		samples_ = samples;
//		Collections.sort(samples_, new LaneBarcodeComparator());
		size_ = 0.0;
		
		Iterator<Sample> iSample = samples_.iterator();
		while(iSample.hasNext()){
			Sample sample = iSample.next();
			size_+=sample.Reads();
			bundleHash_ |=sample.Barcode().hashCode();
		}
		
		
	}
	
	public boolean addSample(Sample sample){
		//check barcode clashes
		boolean barcodeAlreadyPresent = false;
		for(Sample iSample : samples_){
			if(sample.Barcode().equals(iSample.Barcode())){
				barcodeAlreadyPresent = true;
				break;
			}
		}
		
		if(barcodeAlreadyPresent){
			System.out.printf("Failed to add sample to bundle as there is a barcode clash!\n");
			return false;
		}
		
		samples_.add(sample);
		size_+=sample.Reads();
		return true;
	}
	
	public ArrayList<Sample> Samples(){
		return samples_;
	}
	
	public double Size(){
		return size_;
	}

	public void printBundle() {
		System.out.printf("%.2f\t: ",size_);
		Iterator<Sample> iSample = samples_.iterator();
		while(iSample.hasNext()){
			Sample s = iSample.next();
			System.out.printf("[%s-%s-%.2f] ", s.Name(), s.Barcode(), s.Reads());
		}
		System.out.println();
	}
	
	
	public boolean containsBarcode(String barcode){
		Iterator<Sample> iSample = samples_.iterator();
		while(iSample.hasNext()){
			if(iSample.next().Barcode().equals(barcode)){
				return true;
			}
		}
		
		return false;
	}

	public boolean equals(Object other) {
	   
	    SampleBundle b = (SampleBundle) other;
	   if(size_== b.Size() && bundleHash_ == b.BundleHash()){
		   return true;
	   }
	    return false;
	}
	
	private int BundleHash() {
		return bundleHash_;
	}

	public int hashCode(){
		return (int)(size_*1000.0);
	}
	
	
	public ArrayList<Sample> getSharedBarcodes(ArrayList<Sample> qSamples){
		ArrayList<Sample> shared = new ArrayList<Sample>();
		for(int q = 0; q< qSamples.size(); ++q){
			for(int s=0; s< samples_.size(); ++s){
				if (qSamples.get(q).Barcode().equals(samples_.get(s).Barcode())){
					shared.add(samples_.get(s));
					break;	//TODO: Should this be a continue?
				}
			}
		}
		
		return shared;
	}
	
	
}
