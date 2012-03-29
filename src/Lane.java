import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



public class Lane {
	private int laneNumber_;
	private double capacity_;
	private ArrayList<Sample> samples_ = new ArrayList<Sample>();
	
	
	
	public Lane(int laneNumber, double capacity){
		//announce
//		System.out.printf("Creating Lane (number=%d, capacity=%.2f)\n",laneNumber, capacity);
		laneNumber_ = laneNumber;
		capacity_ = capacity;
	}
	
	
	
	
	public Sample getRandomSample(){
		int rnd = (int) (Math.random() * samples_.size());
		return samples_.get(rnd);
	}
	
	
	
	public boolean addSample(Sample sample){
		//check if sample already exists (DEBUG only)
		Iterator<Sample> iSample = samples_.iterator();
		while(iSample.hasNext()){
			Sample subjectSample = iSample.next();
			if(subjectSample.Name().equals(sample.Name())){
				System.out.printf("Not adding %s to lane %d as it already exists\n",sample.Name(), laneNumber_);
				return false;
			}
		}
		//add
		samples_.add(sample);
//		System.out.printf("Added sample %s to lane %d\n", sample.Name(), laneNumber_);
		return true;
	}
	
	
	public void removeSample(Sample s){
		//check if sample already exists (DEBUG only)
		Iterator<Sample> iSample = samples_.iterator();
		while(iSample.hasNext()){
			Sample subjectSample = iSample.next();
			if(subjectSample.Name().equals(s.Name())){
//				System.out.println("Removing sample!");
				iSample.remove();
				return;
			}
		}
		
	}
	
	
	
	public double currentFillLevel(){
		double currTotal=0.0;
		Iterator<Sample> iSample = samples_.iterator();
		while(iSample.hasNext()){
			currTotal+=iSample.next().Reads();
		}
		return currTotal;
	}
	
	
	
	public double remainingCapacity(){
		return capacity_ - this.currentFillLevel(); 
	}
	
	
	
	public int LaneNumber(){
		return laneNumber_;
	}
	
	
	public double Capacity(){
		return capacity_;
	}
	
	
	
	public Sample getSmallestSampleToEnsureNoOverFill(){
		System.out.printf("\nGrabbing smallest sample to ensure no overfill in lane (%d)\n",this.LaneNumber());
		Sample sample = null;
		Collections.sort(this.samples_, new SampleSizeComparator());
		Iterator<Sample> iSample = samples_.iterator();
		while(iSample.hasNext() && sample==null){
			Sample currSample = iSample.next();
			System.out.printf("Lane %d\t%s\t%.2f\n", this.LaneNumber(), currSample.Name(), currSample.Reads() );
		}
		
		iSample = samples_.iterator();
		while(iSample.hasNext() && sample==null){
			Sample currSample = iSample.next();
			System.out.printf("%s\t%.2f\n", currSample.Name(), currSample.Reads() );
			if(currSample.Reads() >= -this.remainingCapacity()){
				System.out.printf("Found best option! (%.2f > %.2f)\n",currSample.Reads(), -this.remainingCapacity());
				sample = currSample;
			}
		}
		if (sample == null){
			sample = samples_.get(samples_.size()-1);
			System.out.println("None big enough so grabbing last one!");
		}
		
		return sample;
	}
	
	
	public void printLane(){
		Iterator<Sample> iSample = samples_.iterator();
		System.out.printf("%d:", laneNumber_);
		while(iSample.hasNext()){
			Sample sample = iSample.next();
			System.out.printf("[%s,%.2f] ",sample.Barcode(),sample.Reads());
		}
//		System.out.println();
		
	}
	
	
	public boolean isEmpty(){
		return (samples_.size()==0);
	}




	public int numSamples() {
		return samples_.size();
	}




	public ArrayList<Sample> getSamples() {
		return samples_;
	}




	public void removeAllSamples() {
		samples_.clear();
	}




	public boolean hasBarcode(String barcode) {

		for(int s=0;s<samples_.size();++s){
			if(samples_.get(s).Barcode().equals(barcode)){
				return true;
			}
		}
		return false;
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




	public Sample getSampleByBarcode(String barcode) throws IOException {
		for(int s=0;s<samples_.size();++s){
			if(samples_.get(s).Barcode().equals(barcode)){
				return samples_.get(s);
			}
		}
		
		System.out.println("ERROR: Returning null!");
		System.in.read();
		return null;
	}
	
	
	public  ArrayList<SampleBundle> calculateSampleBundlePermutations(){
//		System.out.printf("\t->Calculating bundles: Lane %d\n", this.laneNumber_);
		Set<SampleBundle> bundles = new HashSet<SampleBundle>();
//		BitSet bits = new BitSet(samples.size());
//		bits =
		
		//create new temp set
		Set<SampleBundle> tempSet = new HashSet<SampleBundle>();
		
		//do first one
		for (int i =0; i<samples_.size(); ++i){
			
			
			//add empty one
			ArrayList<Sample> newSet = new ArrayList<Sample>();
			bundles.add(new SampleBundle(newSet));
			
			//for each existing bundle
			Iterator<SampleBundle> iBundle = bundles.iterator();
			while(iBundle.hasNext()){
				//create daughter
				SampleBundle samBun = iBundle.next();
				ArrayList<Sample> daughter = new ArrayList<Sample>();
				Iterator<Sample> iSample = samBun.Samples().iterator();
				while(iSample.hasNext()){
					daughter.add(iSample.next());
				}
				//add current
				daughter.add(samples_.get(i));
				
				//add to bundle set
				tempSet.add(new SampleBundle(daughter));

				
			}
			
			bundles.addAll(tempSet);

			
		}
		
		
		//convert to arrayList
		ArrayList<SampleBundle> list = new ArrayList<SampleBundle>(bundles);
		Collections.sort(list, new BundleComparator());
//		System.out.println("\t->Done");
		return list;
	}




	public boolean addBundle(SampleBundle bundle) {
		//for each sample
		for (int i=0;i<bundle.Samples().size(); ++i){
			//check if sample already exists (DEBUG only)
			Iterator<Sample> iSample = samples_.iterator();
			while(iSample.hasNext()){
				Sample subjectSample = iSample.next();
				if(subjectSample.Name().equals(bundle.Samples().get(i).Name())){
					System.out.printf("Not adding %s to lane %d as it already exists\n",bundle.Samples().get(i).Name(), laneNumber_);
					return false;
				}
			}
			//add
			samples_.add(bundle.Samples().get(i));
	//		System.out.printf("Added sample %s to lane %d\n", sample.Name(), laneNumber_);
		}
		return true;

		
	}
	
	
	
}
