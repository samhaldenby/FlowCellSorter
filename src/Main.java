import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class Main {

	final static int NUM_OF_FCS=2;
	final static double LANE_CAPACITY=1.01;
	final static int REQUIRED_ITERATIONS=100;
	static int TRIES_BEFORE_INCREASING_NUM_LANES = 30000;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
	
		//set directory (TODO: This is only for debugging purposes)
		Preferences.setWorkingDirectory( "/home/sh695/Documents/Java/FlowCellSorter");
//		String fileName = args[0];
//		System.out.printf("Sample name: %s\n", fileName );
//		ArrayList<Sample> samples = null;
//		try {
//			samples = SheetReader.read(fileName);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//DEBUG - random samples
//		samples = SampleSheetRandomiser.create();
//		
//		SheetReader.write(samples,args[1]);
//		
//	
//		
//		
//		//create flowcell
//		double totalRequired = 0.00d;
//		Iterator<Sample> iSample = samples.iterator();
//		while(iSample.hasNext()){
//			totalRequired+=iSample.next().Reads();
//		}
//		
////		int numLanesRequired = (int) ((totalRequired+LANE_CAPACITY -1.0) / LANE_CAPACITY); //TODO This doesn't calculate correctly
//		int minLanesRequired =  (int) Math.ceil(totalRequired/ LANE_CAPACITY);
//		int maxLanesRequired = samples.size();
//		int numLanesRequired = (minLanesRequired + maxLanesRequired) / 2;
//		
////		numLanesRequired = samples.size(); //DEBUG ONLY
//		System.out.printf("TotalRequired: %.2f\n",totalRequired);
//		System.out.printf("Min: %d\tMax: %d\tAve: %d\n",minLanesRequired, maxLanesRequired, numLanesRequired);
////		System.in.read();
//		
//		

		Display display = new Display();
		
		Scores.display_ = display;
		DisplayUpdater du = new DisplayUpdater(display);
		new Thread(du).start();
		

		
	
//		display.disableLoadSave();
//		FlowCell flowCell = new FlowCell(LANE_CAPACITY, display);

//		//add samples
//		flowCell.initialAddSamples(samples);
//		RandomShuffler.Shuffle(flowCell);
//		display.enableLoadSave();
//		System.out.println("All done!");

	}

}
