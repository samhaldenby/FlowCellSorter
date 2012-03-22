import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;


public class Shuffler {
	
	public static boolean Shuffle(FlowCell fc) throws IOException{
		//1) Check if flowcell is too full
		if(fc.currentFillLevel() > fc.Capacity()){
			System.out.printf("Shuffle failed: Flowcell too full (%.2f/%.2f, args)\n",fc.currentFillLevel(),fc.Capacity());
			return false;
		}
		
		//2) Calculate under/over-filled lanes
		ArrayList<Lane> underFilled = fc.underFilled();
		ArrayList<Lane> overFilled = fc.overFilled();
		
		
		
		while(overFilled.size()>0){
			
			Collections.sort(underFilled, new LaneFullnessComparator());
			Collections.sort(overFilled, new LaneFullnessComparator());

			for(int u=0; u<underFilled.size(); ++u){
				System.out.printf("UnderFilled: %d\t%.2f\n",underFilled.get(u).LaneNumber(), underFilled.get(u).remainingCapacity());
			}
			
			for(int o=0; o<overFilled.size(); ++o){
				System.out.printf("OverFilled : %d\t%.2f\n",overFilled.get(o).LaneNumber(), overFilled.get(o).remainingCapacity());
			}
			
			
		
			//2) Move samples around until no lanes overflowing
			//   Start with largest overfill and try to fit into most appropriate under fill
			Lane fullestLane = overFilled.get(overFilled.size()-1);
			Sample s = fullestLane.getSmallestSampleToEnsureNoOverFill();
			
			//3) Find most appropriate underfill to stick it in
			ListIterator<Lane> iLane = underFilled.listIterator(underFilled.size());
//			Iterator<Lane> iLane = underFilled.iterator();
			boolean foundTargetLane = false;
			while(iLane.hasPrevious() && !foundTargetLane){
				//check that lane has the most appropriate underfill that will accomodate sample
				Lane lane = iLane.previous();
				System.out.printf("Lane %d : Remaining capacity: %.2f\n",lane.LaneNumber(),lane.remainingCapacity());
				if(s.Reads()<=lane.remainingCapacity()){
					System.out.printf("Sticking %s(%.2f) in laneLane %d : Remaining capacity: %.2f\n", s.Name(), s.Reads(), lane.LaneNumber(), lane.remainingCapacity());
					foundTargetLane = true;
					
					//move around!
					lane.addSample(s);
					fullestLane.removeSample(s);
				}
				
			}
			
			//recalculate
			underFilled = fc.underFilled();
			overFilled = fc.overFilled();
			
			System.in.read();
		}
		
		
		
		for(int u=0; u<underFilled.size(); ++u){
			System.out.printf("UnderFilled: %d\t%.2f\t(%.2f)\n",underFilled.get(u).LaneNumber(), underFilled.get(u).remainingCapacity(), underFilled.get(u).currentFillLevel());
		}
		
		for(int o=0; o<overFilled.size(); ++o){
			System.out.printf("OverFilled : %d\t%.2f\t(%.2f)\n",overFilled.get(o).LaneNumber(), overFilled.get(o).remainingCapacity(), overFilled.get(o).currentFillLevel());
		}
		
		System.out.println("DONE!");
		return true;
	}
	

}
