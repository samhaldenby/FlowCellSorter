import java.util.ArrayList;
import java.util.Iterator;


public class FlowCell {
	private int numLanes_;
	private ArrayList<Lane> lanes_ = new ArrayList<Lane>();
	private double capacity_;
	
	public FlowCell(int numLanes, double capacityPerLane){
		//announce
//		System.out.printf("Creating FlowCell (lanes=%d, capacityPerLane=%.2f)\n",numLanes, capacityPerLane);
		numLanes_ = numLanes;
		capacity_ = numLanes_ * capacityPerLane;
		
		//create lanes
		for(int l=0; l <numLanes; ++l){
			lanes_.add(new Lane(l, capacityPerLane));
		}
	}
	
	
	
	public double currentFillLevel(){
		double val=0.0;
		Iterator<Lane> iLane = lanes_.iterator();
		while(iLane.hasNext()){
			val+=iLane.next().currentFillLevel();
		}
		
		return val;
	}
	
	
	
	public double Capacity(){
		return capacity_;
	}
	
	public Lane Lane(int number){
		return lanes_.get(number);
	}
	
	
	public int NumLanes(){
		return numLanes_;
	}
	
	
	
	public ArrayList<Lane> underFilled(){
		ArrayList<Lane> underFilledLanes = new ArrayList<Lane>();
		Iterator<Lane> iLane = lanes_.iterator();
		while(iLane.hasNext()){
			Lane l = iLane.next();
			if(l.remainingCapacity()>0){
				underFilledLanes.add(l);
			}
		}
		return underFilledLanes;
	}
	
	
	
	public ArrayList<Lane> overFilled(){
		ArrayList<Lane> overFilledLanes = new ArrayList<Lane>();
		Iterator<Lane> iLane = lanes_.iterator();
		while(iLane.hasNext()){
			Lane l = iLane.next();
			if(l.remainingCapacity()<0){
//				System.out.printf("Remaining capacity = %.2f -> adding to overFilled\n",l.currentFillLevel());
				overFilledLanes.add(l);
			}
		}
		return overFilledLanes;
	}



	public Lane getEmptiestLane() {
		Lane lane = lanes_.get(0);
		double lowest = lanes_.get(0).currentFillLevel();
		Iterator<Lane> iLane = lanes_.iterator();
		while(iLane.hasNext()){
			Lane currLane = iLane.next();
			if(currLane.currentFillLevel() < lowest){
				lowest = currLane.currentFillLevel();
				lane = currLane;
			}
		}
		
		return lane;
	}
	
	public void printFlowCell(){
		Iterator<Lane> iLane = lanes_.iterator();
		double totScore = 0.0;
		while(iLane.hasNext()){
			Lane currLane = iLane.next();
			double score = (100.0 - ((currLane.currentFillLevel()/currLane.Capacity())*100)) * (100.0 - ((currLane.currentFillLevel()/currLane.Capacity())*100));
			totScore+=score;
			System.out.printf("** Lane %s %.2f%% full\t%.2f\n",currLane.LaneNumber(), (currLane.currentFillLevel()/currLane.Capacity()) * 100.0, score);
			currLane.printLane();
		}
		
		System.out.printf("\nTotal Score: %.2f\n",totScore);
	}
	
	public double calculateFlowCellScore(){
		Iterator<Lane> iLane = lanes_.iterator();
		double totScore = 0.0;
		while(iLane.hasNext()){
			Lane currLane = iLane.next();
			double score = (100.0 - ((currLane.currentFillLevel()/currLane.Capacity())*100)) * (100.0 - ((currLane.currentFillLevel()/currLane.Capacity())*100));
			totScore+=score;

		}
		
		return totScore;
	}




	
	
	
	
}
