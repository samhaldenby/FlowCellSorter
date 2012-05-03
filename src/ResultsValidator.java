import java.util.HashSet;


/** Static class responsible for the shuffling/sorting of samples
*
* @author Sam Haldenby
*/
public class ResultsValidator {
	static public boolean Validate(FlowCell fc){
		
		//Check 1 - No lane overfilled
		if(!checkFillLevels(fc)){
			return false;
		}
		//Check 2 - No barcode more than once in same lane
		if(!checkBarcodeCounts(fc)){
			return false;
		}
		//Check 3 - No pool split over multiple lanes
		if(!checkForSplitPools(fc)){
			return false;
		}
		//Check 4 - No missing samples
		if(!checkNumberOfSamples(fc)){
			return false;
		}
		//Check 5 - No weird sample duplications
		if(!checkForDuplicateSamples(fc)){
			return false;
		}
		
		
		
		return true;
	}
	
	

	static private boolean checkBarcodeCounts(FlowCell fc){
		for(Lane lane : fc.getLanes()){
			HashSet<String> barcodes = new HashSet<String>();
			for(Sample sample : lane.getSamples()){
				if(barcodes.contains(sample.Barcode())){
					System.err.printf("ERROR: Barcode '%s' appears multiple times in lane %d\n", sample.Barcode(), lane.LaneNumber());
					return false;
				}
				else{
					barcodes.add(sample.Barcode());
				}
			}
		}
		System.out.println("Passed: Barcode count check");
		return true;
	}
	
	
	
	static private boolean checkFillLevels(FlowCell fc){
		for(Lane lane : fc.getLanes()){
			if(lane.currentFillLevel() > lane.Capacity()){
				System.err.printf("ERROR: Lane %d is overfilled (fill level=%.2f, capacity=%.2f)\n", lane.LaneNumber(), lane.currentFillLevel(), lane.Capacity());
				return false;
			}
		}
		System.out.println("Passed: Fill level check");
		return true;
	}
	
	
	static private boolean checkForSplitPools(FlowCell fc){
		//gather all pool ids
		HashSet<Integer> poolNums = new HashSet<Integer>();
		for(Lane lane : fc.getLanes()){
			for(Sample sample : lane.getSamples()){
				if(sample.isPooled()){
					if(!poolNums.contains(sample.Pool())){
						poolNums.add(sample.Pool());
					}
				}
			}
		}
		
		//now for each pool num, check that it isn't split across lanes
		for(Integer poolNum : poolNums){
			HashSet<Integer> presentInLanes = new HashSet<Integer>();
			//check each sample of each lane
			for(Lane lane : fc.getLanes()){
				for(Sample sample : lane.getSamples()){
					if(sample.isPooled() && sample.Pool()==poolNum){
						presentInLanes.add(lane.LaneNumber());
					}
				}
			}
			
			if(presentInLanes.size()!=1){
				System.err.printf("ERROR: Pool %d is split over %d lanes\n", poolNum, presentInLanes.size());
				return false;
			}
		}
		
		System.out.println("Passed: Split pool check");
		return true;	//i.e. all is well
	}
	
	
	
	private static boolean checkNumberOfSamples(FlowCell fc) {
		int endTotalSamples = 0;
		for(Lane lane : fc.getLanes()){
			endTotalSamples+=lane.getSamples().size();
		}
		
		if(endTotalSamples!=Storage.originalNumberOfSamples){
			System.err.printf("ERROR: Number of samples from start to end does not match (start=%d, end=%d)\n",Storage.originalNumberOfSamples, endTotalSamples);
			return false;
		}
		
		System.out.println("Passed: Sample count check");
		return true;
		
	}
	
	private static boolean checkForDuplicateSamples(FlowCell fc) {
		HashSet<String> sampleNames = new HashSet<String>();
		for(Lane lane : fc.getLanes()){
			for(Sample sample : lane.getSamples()){
				if(sampleNames.contains(sample.Name())){
					System.err.printf("ERROR: Sample %s is observed more than once\n", sample.Name());
					return false;
				}
				else{
					sampleNames.add(sample.Name());
				}
			}
		}
		
		System.out.println("Passed: Sample duplication check");
		return true;
	}
	


}
