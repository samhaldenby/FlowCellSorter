import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/** Contains information regarding all lanes/samples/etc
*
* @author Sam Haldenby
*/
public class FlowCell {
	private int numLanes_;
	private ArrayList<Lane> lanes_ = new ArrayList<Lane>();
	private double capacity_;
	private double capacityPerLane_;

	
	/** Constructor
	 * 
	 * @param capacityPerLane		Maximum capacity per lane
	 */
	public FlowCell(double capacityPerLane) {
		capacityPerLane_ = capacityPerLane;
	}

	
	/** Adds samples to lanes of flowcell
	 * 
	 * @throws IOException 		
	 * @param samples		List of samples to add
	 * @param display		Reference to main display
	 * @return	Success/failure
	 */
	public boolean initialAddSamples(ArrayList<Sample> samples, Display display) throws IOException {
		//DEBUG
		
		//First, create two lots of samples: a map of pooled bundles and a list of unpooled samples
		HashMap<Integer,SampleBundle> pools = new HashMap<Integer,SampleBundle>();
		ArrayList<Sample> unpooled = new ArrayList<Sample>();
		
		//for each sample, assign to correct pool (or unpooled, if no pool)
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
		
		//Finally validate flow cell
		boolean overfill = false;
		HashSet<Integer> overfilledPools = new HashSet<Integer>();
		for(Lane iLane : this.lanes_){
			if(iLane.currentFillLevel() > iLane.Capacity()){
				//grab pool number (this can only happen if pool is over filled)
				overfilledPools.add(iLane.getSamples().get(0).Pool());
				overfill = true;
			}
		}
		
		if(overfill){
			String errorMessage = "Error: Over-full pools:";
			for(Integer i : overfilledPools){
				errorMessage = errorMessage + " [" + Integer.toString(i) + "]";
			}
			display.updateMessage(errorMessage);
			return false;
		}

		return true;
	}


	
	/** Adds an empty lane to flowcell
	 *
	 */
	public void addLane() {
		lanes_.add(new Lane(numLanes_ - 1, capacityPerLane_));
		capacity_ += capacityPerLane_;
		numLanes_++;
	}

	
	
	/** Get reference to a particular lane
	 * 
	 * @param number		Number of lane to be retrieved
	 * @return	Requested lane
	 */
	public Lane Lane(int number) {
		return lanes_.get(number);
	}

	
	
	/** Get number of lanes on flowcell
	 *
	 * @return	Number of lanes
	 */
	public int NumLanes() {
		return numLanes_;
	}



	/** Calculates score for flowcell based on how good configuration is
	 *
	 * @return	Flowcell score
	 */
	public double calculateFlowCellScore() {
		Iterator<Lane> iLane = lanes_.iterator();
		double totScore = 0.0;
		while (iLane.hasNext()) {
			Lane currLane = iLane.next();
			double score = Math.pow(100.0 - ((currLane.currentFillLevel() / currLane.Capacity()) * 100), 2);			
			totScore += score;
		}


		return totScore/(double)(lanes_.size());///Math.pow(this.NumNonEmptyLanes(),2);
	}
	


	/** Gets a reference to list of all lanes on flowcell
	 * 
	 * @return	Reference to list of lanes
	 */
	public ArrayList<Lane> getLanes() {
		return lanes_;
	}
	


	/** Gets the number of non-empty lanes on the flowcell
	 * 
	 * @return	Number of non-empty lanes
	 */
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

	
	
	/** Gets a list of references to non-empty lanes on the flowcell
	 * 
	 * @return	List of references to non-empty lanes
	 */
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

	
	
	/** Gets how many samples are loaded on flowcell
	 * 
	 * @return	Total number of samples
	 */
	public int NumSamples() {
		int lanes = 0;
		for(Lane iLane : lanes_){
			lanes+=iLane.numSamples();
		}
		return lanes;
	}

	
	
	/** Gets how much free space there is on flowcell
	 * 
	 * @return	Percentage free space on flowcell
	 */
	public double FreeSpace() {
		double totFree=0.0d;
		double totSpace = 0.0d;
		for(Lane iLane : lanes_){
			if(!iLane.isEmpty()){
				totFree+=iLane.remainingCapacity();
				totSpace+=iLane.Capacity();
			}
		}
		
		return (totFree/totSpace) * 100.d;
	}

}
