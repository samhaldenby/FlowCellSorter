import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;


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
		
		//preferences
		PreferenceStore.LoadPreferences();
//		Preferences prefs = Preferences.userNodeForPackage(Main.class);
//		prefs.put("userLoadDir", "/home/sh695/Documents/Java/FlowCellSorter/");
//		prefs.put("userSaveDir", "/home/sh695/Documents/Java/FlowCellSorter/");
//		PreferenceStore.LoadDirectory(prefs.get("userLoadDir","/"));
//		PreferenceStore.SaveDirectory(prefs.get("userSaveDir","/"));
//		PreferenceStore.PrefLaneCapacity = prefs.getDouble("prefLaneCapacity",1.d);
		
		//test pathing
//		File test = new File("/home/sh695/Documents/Java/FlowCellSorter/test.txt");
//		System.out.printf("Absolute Path: %s\n", test.getParent());
//		
//		
//		System.out.printf("UserDir = %s\n", PreferenceStore.UserDir);
//		System.out.printf("UserDir = %.2f\n", PreferenceStore.PrefLaneCapacity);
////		System.in.read();
		//set directory (TODO: This is only for debugging purposes)
//		PreferenceStore.setWorkingLoadDirectory( "/home/sh695/Documents/Java/FlowCellSorter");
//		PreferenceStore.setWorkingSaveDirectory( "/home/sh695/Documents/Java/FlowCellSorter");

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
