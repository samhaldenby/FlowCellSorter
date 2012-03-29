import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class Main {

	final static int NUM_OF_FCS=2;
	final static double LANE_CAPACITY=1.01;
	final static int REQUIRED_ITERATIONS=10000;
	static int TRIES_BEFORE_INCREASING_NUM_LANES = 30000;
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
		
		//DEBUG - random samples
//		samples = SampleSheetRandomiser.create();
		
	
		
		
		
		//create flowcell
		double totalRequired = 0.00d;
		Iterator<Sample> iSample = samples.iterator();
		while(iSample.hasNext()){
			totalRequired+=iSample.next().Reads();
		}
		
//		int numLanesRequired = (int) ((totalRequired+LANE_CAPACITY -1.0) / LANE_CAPACITY); //TODO This doesn't calculate correctly
		int minLanesRequired =  (int) Math.ceil(totalRequired/ LANE_CAPACITY);
		int maxLanesRequired = samples.size();
		int numLanesRequired = (minLanesRequired + maxLanesRequired) / 2;
		
//		numLanesRequired = samples.size(); //DEBUG ONLY
		System.out.printf("TotalRequired: %.2f\n",totalRequired);
		System.out.printf("Min: %d\tMax: %d\tAve: %d\n",minLanesRequired, maxLanesRequired, numLanesRequired);
//		System.in.read();
		
		

		Display display = new Display();
		DisplayUpdater du = new DisplayUpdater(display);
		new Thread(du).start();
		
		long iter = REQUIRED_ITERATIONS;
		int tries = 0;
		int totalTries = 0;
		int timesLanesIncreased =0;
		int iterReducedBy=0;
		
		
		double bestScore = 0;
		while(iter - iterReducedBy!=0){
			++tries;
			++totalTries;

			FlowCell flowCell = new FlowCell(numLanesRequired,LANE_CAPACITY);
			
			//add samples
			int initAddAttempts = 0;
			while(!flowCell.initialAddSamples(samples)){
				if(initAddAttempts++==1000){
					System.out.println("Total failure to add samples. What a pain!");
				}
			}
//			iSample = samples.iterator();
//			while(iSample.hasNext()){
////				Lane lane= flowCell.getEmptiestLane();
////				lane.addSample(iSample.next());
//				Lane lane= flowCell.Lane((int) (Math.random()*flowCell.NumLanes()));
//				lane.addSample(iSample.next());
//			}
			
			if(tries%10000==0){
				System.out.printf("ATTEMPT: %d / %d / %d / initFail: %d / shuffleFail %d / failSwap %d / failDonate %d\n",iter-iterReducedBy,tries,totalTries, Scores.initFail, Scores.shuffleFail, Scores.failSwap, Scores.failDonate);
			}
//			flowCell.printFlowCell();
//			System.in.read();
		
//			System.out.printf("Fill level for fc = %.2f of %.2f\n", flowCell.currentFillLevel(), flowCell.Capacity());
	//		Shuffler.Shuffle(flowCell);
			
			if(RandomShuffler2.Shuffle(flowCell)){
				//tot scores
				if(flowCell.calculateFlowCellScore() > bestScore){
					bestScore = flowCell.calculateFlowCellScore();
					Scores.best = flowCell;
					System.out.printf("New Best: %.5f\n",bestScore);
				}
				
				
				if(iter%1==0){
					System.out.printf("Iters left: %d\tAttempts: %d\tBest: %.5f\n",iter-iterReducedBy, totalTries, bestScore);
					Scores.best.printFlowCell();
				}
				iter--;
				tries=0;
				
			}
			

			
			
		
			//expand number of lanes if required
			if(tries==TRIES_BEFORE_INCREASING_NUM_LANES /*&& iter==REQUIRED_ITERATIONS*/){
				System.out.printf("Increasing lanes from %d to %d\n",numLanesRequired,numLanesRequired+1);
//				System.in.read();
				tries = 0;
				numLanesRequired+=1;
				timesLanesIncreased+=1;
//				iter = REQUIRED_ITERATIONS;
//				TRIES_BEFORE_INCREASING_NUM_LANES*=0.95;
//				if(TRIES_BEFORE_INCREASING_NUM_LANES<1000){
//					TRIES_BEFORE_INCREASING_NUM_LANES=1000;
//				}
//				for(int i=0;i<timesLanesIncreased; ++i){
//					iter*=1.1;
//				}

			}
			
		}
		
		Scores.best.printFlowCell();
		System.out.printf("Num Samples: %d\n", samples.size());
		
			
		
		
		

	}

}
