import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;



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
		while(iSample.hasNext()){
			Sample sample = iSample.next();
			System.out.printf("[%s,%.2f] ",sample.Name(),sample.Reads());
		}
		System.out.println();
		
	}
	
	
	
}
