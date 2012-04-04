import java.io.IOException;


public class ShuffleRunner implements Runnable{

	Display display_ = null;
	
	public ShuffleRunner(Display display){
		display_ = display;
	}
	@Override
	public void run() {
		if(Storage.samples!=null){
			display_.disableLoadSave();
			int iter = 0;
			
			FlowCell flowCell = new FlowCell(Consts.LANE_CAPACITY, display_);

			//add samples
			try {
				flowCell.initialAddSamples(Storage.samples);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				RandomShuffler.Shuffle(flowCell);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			display_.enableLoadSave();
		}
		
	
		
	}

}
