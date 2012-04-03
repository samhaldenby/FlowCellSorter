import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FlowCell {
	private int numLanes_;
	private ArrayList<Lane> lanes_ = new ArrayList<Lane>();
	private double capacity_;
	private double capacityPerLane_;
	private double maxScore_;

	public FlowCell(double capacityPerLane) {
		// announce
		// System.out.printf("Creating FlowCell (lanes=%d, capacityPerLane=%.2f)\n",numLanes,
		// capacityPerLane);
//		numLanes_ = numLanes;
		capacityPerLane_ = capacityPerLane;
//		capacity_ = numLanes_ * capacityPerLane;

//		// create lanes
//		for (int l = 0; l < numLanes; ++l) {
//			lanes_.add(new Lane(l, capacityPerLane));
//		}
	}

	public boolean initialAddSamples(ArrayList<Sample> samples) throws IOException {
		//DEBUG
		
		//First, create two lots of samples: a map of pooled bundles and a list of unpooled samples
		HashMap<Integer,SampleBundle> pools = new HashMap<Integer,SampleBundle>();
		ArrayList<Sample> unpooled = new ArrayList<Sample>();
		
		//for each sample, assign to pool
		for(Sample iSample : samples){
			if(iSample.isPooled()){
				if(!pools.containsKey(iSample.Pool())){
					ArrayList<Sample> newList = new ArrayList<Sample>();
					newList.add(iSample);
					pools.put(iSample.Pool(), new SampleBundle(newList));
				} else {
					SampleBundle bundle = pools.get(iSample.Pool());
					bundle.addSample(iSample);
					pools.put(iSample.Pool(), bundle);
				}
			} else{
				unpooled.add(iSample);
			}

		}
		
		//now add pools to lanes
		
		int laneNum=0;
		lanes_ = new ArrayList<Lane>();
		for(Map.Entry<Integer, SampleBundle> iEntry : pools.entrySet()){
			lanes_.add(new Lane(laneNum, capacityPerLane_));
			lanes_.get(laneNum).addBundle(iEntry.getValue());
			++laneNum;
		}
		
		//now add individual samples
		for(Sample iSample : unpooled){
			lanes_.add(new Lane(laneNum, capacityPerLane_));
			lanes_.get(laneNum).addSample(iSample);
			++laneNum;
		}
		
			
			
			
			
//			Iterator<Sample> iSample = samples.iterator();
//		int laneNum=0;
//		while (iSample.hasNext()) {
//			Sample sample = iSample.next();
//			lanes_.add(new Lane(laneNum, capacityPerLane_));
//			lanes_.get(laneNum).addSample(sample);
//			++laneNum;
//		}
		//END DEBUG
//		Iterator<Sample> iSample = samples.iterator();
//		while (iSample.hasNext()) {
//			Sample sample = iSample.next();
//			int randomLaneNumber = (int) (Math.random() * numLanes_);
//			Lane targetLane = lanes_.get(randomLaneNumber);
//			// check that this lane doesn't already contain a sample with the
//			// same barcode
//			int additionAttempts = 0;
//			while (targetLane.hasBarcode(sample.Barcode())) {
//				if (++additionAttempts > 100) {
//					this.clear();
//					Scores.initFail+=1;
//					return false;
//				}
//				randomLaneNumber = (int) (Math.random() * numLanes_);
//				targetLane = lanes_.get(randomLaneNumber);
//			}
//
//			targetLane.addSample(sample);
//		}
		
		
		//calculate max score
		maxScore_ = this.calculateMaxPossibleScore();
		
//		System.in.read();
		return true;
	}

	private void clear() {
		// clear all lanes
		Iterator<Lane> iLane = lanes_.iterator();
		while (iLane.hasNext()) {
			iLane.next().removeAllSamples();
		}

	}

	public void addLane() {
		lanes_.add(new Lane(numLanes_ - 1, capacityPerLane_));
		capacity_ += capacityPerLane_;
		numLanes_++;
	}

	public double currentFillLevel() {
		double val=0.00000d;
		Iterator<Lane> iLane = lanes_.iterator();
		while (iLane.hasNext()) {
			val += iLane.next().currentFillLevel();
		}

		return val;
	}

	public double Capacity() {
		return capacity_;
	}

	public Lane Lane(int number) {
		return lanes_.get(number);
	}

	public int NumLanes() {
		return numLanes_;
	}

	public ArrayList<Lane> underFilled() {
		ArrayList<Lane> underFilledLanes = new ArrayList<Lane>();
		Iterator<Lane> iLane = lanes_.iterator();
		while (iLane.hasNext()) {
			Lane l = iLane.next();
			if (l.remainingCapacity() > 0) {
				underFilledLanes.add(l);
			}
		}
		return underFilledLanes;
	}

	public ArrayList<Lane> overFilled() {
		ArrayList<Lane> overFilledLanes = new ArrayList<Lane>();
		Iterator<Lane> iLane = lanes_.iterator();
		while (iLane.hasNext()) {
			Lane l = iLane.next();
			if (l.remainingCapacity() < 0) {
				// System.out.printf("Remaining capacity = %.2f -> adding to overFilled\n",l.currentFillLevel());
				overFilledLanes.add(l);
			}
		}
		return overFilledLanes;
	}

	public Lane getEmptiestLane() {
		Lane lane = lanes_.get(0);
		double lowest = lanes_.get(0).currentFillLevel();
		Iterator<Lane> iLane = lanes_.iterator();
		while (iLane.hasNext()) {
			Lane currLane = iLane.next();
			if (currLane.currentFillLevel() < lowest) {
				lowest = currLane.currentFillLevel();
				lane = currLane;
			}
		}

		return lane;
	}

	public void printFlowCell() {
		ArrayList<Lane> sortedLanes = lanes_;
		Collections.sort(sortedLanes, new LaneFullnessComparator());
		Iterator<Lane> iLane = sortedLanes.iterator();
		double totScore = 0.0;
		while (iLane.hasNext()) {
			Lane currLane = iLane.next();
			if(!currLane.isEmpty())
			{


				System.out.printf("%.6f\t",(currLane.currentFillLevel() / currLane.Capacity()) * 100.0);
				currLane.printLane();
				System.out.println();
			}
			
			

		

		}
		double score = calculateFlowCellScore();
//		double score2 = calculateFlowCellScoreBak();

		System.out.printf("\nTotal Score: %.2f vs %.2f (of %.2f (%.2f%%)\n", score, (Scores.best==null ? 0.0 : Scores.best.calculateFlowCellScore()), this.maxScore_, 100.0 * (score/this.maxScore_));
//		System.out.printf("\nTotal Score: %.2f of %.2f (%.2f%%)\n", score2,this.calculateMaxPossibleScoreBak(), 100.0 * (score2/this.calculateMaxPossibleScoreBak()));
	}

	public double calculateFlowCellScore() {
		Iterator<Lane> iLane = lanes_.iterator();
		double totScore = 0.0;
		while (iLane.hasNext()) {
			Lane currLane = iLane.next();
//			if (!currLane.isEmpty()) {
				double score = Math.pow(100.0 - ((currLane.currentFillLevel() / currLane.Capacity()) * 100), 2);
						
				totScore += score;
//				System.out.printf("%.2f + ", score);

//			}

		}
//		System.out.printf(" TotScore = %.2f\n", totScore);

		return totScore/(double)(lanes_.size());///Math.pow(this.NumNonEmptyLanes(),2);
	}
	
	public double calculateFlowCellScorePercent() {
		Iterator<Lane> iLane = lanes_.iterator();
		double totScore = 0.0;
		int numLanes = 0;
		while (iLane.hasNext()) {
			Lane currLane = iLane.next();
			if (!currLane.isEmpty()) {
				totScore+= currLane.currentFillLevel();
				numLanes++;
						

//				System.out.printf("%.2f + ", score);

			}

		}
//		System.out.printf(" TotScore = %.2f\n", totScore);

		return totScore/(double)numLanes;
	}

	public ArrayList<Lane> getLanes() {
		return lanes_;
	}
	
	public double calculateMaxPossibleScoreBak(){
		double totalSampleSize = this.currentFillLevel();
		int wholeNumber = (int) totalSampleSize;
//		System.out.println("Calculating max score");
//		System.out.printf("TotalSize: %.2f  ->  %d\n",totalSampleSize, wholeNumber);
		double remainder = 100.0 * (1.0 - (totalSampleSize - wholeNumber));
		double maxScore = Math.pow(remainder, 2);
//		System.out.printf("MaxScore: %.2f\n", maxScore);
		
		return maxScore;
	}
	
	public double calculateMaxPossibleScore(){
		double totalSampleSize = this.currentFillLevel();
		int wholeNumber = (int) totalSampleSize;
//		System.out.println("Calculating max score");
//		System.out.printf("TotalSize: %.2f  ->  %d\n",totalSampleSize, wholeNumber);
		double remainder = totalSampleSize - wholeNumber;
		double maxScore = (wholeNumber + remainder)/(wholeNumber+1);
//		System.out.printf("MaxScore: %.2f\n", maxScore);
		
		return maxScore;
	}

	public int NumNonEmptyLanes() {
		int nonEmptyLanes = 0;
		Iterator<Lane> iLane = lanes_.iterator();
		while (iLane.hasNext()) {
			if (!iLane.next().isEmpty()) {
				nonEmptyLanes++;
			}
		}
		return nonEmptyLanes;
	}

	public ArrayList<Lane> NonEmptyLanes() {
		ArrayList<Lane> nonEmptyLanes = new ArrayList<Lane>();
		Iterator<Lane> iLane = lanes_.iterator();
		while (iLane.hasNext()) {
			Lane lane = iLane.next();
			if (!lane.isEmpty()) {
				nonEmptyLanes.add(lane);
			}
		}
		return nonEmptyLanes;
	}

}
