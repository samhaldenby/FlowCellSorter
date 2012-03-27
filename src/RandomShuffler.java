import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;


public class RandomShuffler {
	
	public static boolean Shuffle(FlowCell fc) throws IOException{
		//1) Check if flowcell is too full
		if(fc.currentFillLevel() > fc.Capacity()){
			System.out.printf("Shuffle failed: Flowcell too full (%.2f/%.2f)\n",fc.currentFillLevel(),fc.Capacity());
			return false;
		}
		
		//2) Calculate under/over-filled lanes
		ArrayList<Lane> overFilled = fc.overFilled();
		ArrayList<Lane> underFilled = fc.underFilled();
//		for(int u=0; u<underFilled.size(); ++u){
//			System.out.printf("UnderFilled: %d\t%.2f\t(%.2f)\n",underFilled.get(u).LaneNumber(), underFilled.get(u).remainingCapacity(), underFilled.get(u).currentFillLevel());
//		}
//		
//		for(int o=0; o<overFilled.size(); ++o){
//			System.out.printf("OverFilled : %d\t%.2f\t(%.2f)\n",overFilled.get(o).LaneNumber(), overFilled.get(o).remainingCapacity(), overFilled.get(o).currentFillLevel());
//		}

		double INIT_THRESHOLD = 0.99;
		double FULL_THRESHOLD = INIT_THRESHOLD;
		
		int attempts =0;
		while(overFilled.size()>0){
			if(attempts++>1000){
				return false;
			}
			
			//Grab random sample from 2 random lanes and swap
			Lane lane1 = fc.Lane((int) (Math.random() * fc.NumLanes()));
//			System.out.printf("Grabbed L1 as lane %d\t currentFill: %.2f\tremaining %.2f \tFull? %s\n",lane1.LaneNumber(), lane1.currentFillLevel(), lane1.remainingCapacity(), lane1.currentFillLevel() / lane1.Capacity()>FULL_THRESHOLD);
			double l1attempts=0;
			while((lane1.currentFillLevel() / lane1.Capacity() >= FULL_THRESHOLD && lane1.remainingCapacity() >0 )|| lane1.remainingCapacity() >0){
				if(++l1attempts==10){
//					System.out.printf("%.3f\t%d\n",FULL_THRESHOLD,attempts);
					FULL_THRESHOLD-=0.01;
					l1attempts=0;
				}
				lane1 = fc.Lane((int) (Math.random() * fc.NumLanes()));
//				System.out.printf("Grabbed L1 as lane %d\t currentFill: %.2f\tremaining %.2f \tFull? %s\n",lane1.LaneNumber(), lane1.currentFillLevel(), lane1.remainingCapacity(), lane1.currentFillLevel() / lane1.Capacity()>FULL_THRESHOLD);
			}
			
			FULL_THRESHOLD=INIT_THRESHOLD;
			
			Lane lane2 = fc.Lane((int) (Math.random() * fc.NumLanes()));
			double l2attempts=0;
			while(lane2.LaneNumber()==lane1.LaneNumber() || lane2.currentFillLevel() / lane2.Capacity() >= FULL_THRESHOLD){
				if(++l2attempts==10){
//					System.out.printf("%.3f\n",FULL_THRESHOLD);
					FULL_THRESHOLD-=0.01;
					l2attempts=0;
				}
				lane2 = fc.Lane((int) (Math.random() * fc.NumLanes()));
			}
			
			Sample sample1 = lane1.getRandomSample();
			Sample sample2 = lane2.getRandomSample();
			
			//remove
			lane1.removeSample(sample1);
			lane2.removeSample(sample2);
			
			//add
			lane1.addSample(sample2);
			lane2.addSample(sample1);
			
			
			
			//recalculate
			overFilled = fc.overFilled();
			underFilled = fc.underFilled();
//			for(int u=0; u<underFilled.size(); ++u){
//				System.out.printf("UnderFilled: %d\t%.2f\t(%.2f)\n",underFilled.get(u).LaneNumber(), underFilled.get(u).remainingCapacity(), underFilled.get(u).currentFillLevel());
//			}
//			
//			for(int o=0; o<overFilled.size(); ++o){
//				System.out.printf("OverFilled : %d\t%.2f\t(%.2f)\n",overFilled.get(o).LaneNumber(), overFilled.get(o).remainingCapacity(), overFilled.get(o).currentFillLevel());
//			}
//			System.out.printf("FC FULLNESS:%.2f\n", fc.currentFillLevel());
			
//			System.in.read();
		}
		
		
		
//		for(int u=0; u<underFilled.size(); ++u){
//			System.out.printf("UnderFilled: %d\t%.2f\t(%.2f)\n",underFilled.get(u).LaneNumber(), underFilled.get(u).remainingCapacity(), underFilled.get(u).currentFillLevel());
//		}
//		
//		for(int o=0; o<overFilled.size(); ++o){
//			System.out.printf("OverFilled : %d\t%.2f\t(%.2f)\n",overFilled.get(o).LaneNumber(), overFilled.get(o).remainingCapacity(), overFilled.get(o).currentFillLevel());
//		}
		
		
//		fc.printFlowCell();
//		System.out.println("DONE!");
		return true;
	}
	

	

}
