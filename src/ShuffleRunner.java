import java.io.IOException;

/** Classs used to invoke RandomShuffler in its own thread
*
* @author Sam Haldenby
*/
public class ShuffleRunner implements Runnable{

	Display display_ = null;
	
	public ShuffleRunner(Display display){
		display_ = display;
	}
	@Override
	public void run() {
		if(Storage.samples!=null){
			display_.disableLoadSave();
			
			FlowCell flowCell = new FlowCell(Consts.LANE_CAPACITY);

			//add samples
			try {
				if(!flowCell.initialAddSamples(Storage.samples, display_)){
					System.out.println("Cancelling shuffle!");
					display_.enableLoadSave();
					return;
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				RandomShuffler.Shuffle(flowCell, display_);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			display_.enableLoadSave();
		}
		
	
		
	}

}
