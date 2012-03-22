import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class Main {

	final static int NUM_OF_FCS=2;
	final static double LANE_CAPACITY=4.0;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String fileName = args[0];
		ArrayList<Sample> samples = null;
		try {
			samples = SheetReader.read(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//create flowcell
		double totalRequired = 0.0;
		Iterator<Sample> iSample = samples.iterator();
		while(iSample.hasNext()){
			totalRequired+=iSample.next().Reads();
		}
		
		int numLanesRequired = (int) ((totalRequired+LANE_CAPACITY -1.0) / LANE_CAPACITY);
		System.out.printf("TotalRequired: %.2f\n",totalRequired);
		System.out.println(numLanesRequired);
//		System.in.read();
		
		
		int iter = 50000;
		
		FlowCell best  = null;
		double bestScore = 0;
		while(iter!=0){
		
			FlowCell flowCell = new FlowCell(numLanesRequired,LANE_CAPACITY);
			
			//add samples
			iSample = samples.iterator();
			while(iSample.hasNext()){
				Lane lane= flowCell.getEmptiestLane();
				lane.addSample(iSample.next());
			}
			
//			System.out.printf("Fill level for fc = %.2f of %.2f\n", flowCell.currentFillLevel(), flowCell.Capacity());
	//		Shuffler.Shuffle(flowCell);
			
			if(RandomShuffler.Shuffle(flowCell)){
				//tot scores
				if(flowCell.calculateFlowCellScore() > bestScore){
					bestScore = flowCell.calculateFlowCellScore();
					best = flowCell;
				}
				
				System.out.printf("Iters left: %d\n",iter);
				iter--;
			}
		
			
		}
		
		best.printFlowCell();
			
		
		
		

	}

}
