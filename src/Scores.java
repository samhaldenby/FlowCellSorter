

/** Static class storing information on best flowcell configuration
*
* @author Sam Haldenby
*/
public class Scores {
	public static FlowCell best = null;
	
	public static Display display_ ;
	
	
	//fail rates
	public static int initFail = 0;
	public static int shuffleFail = 0;
	public static int failSwap = 0;
	public static int failDonate = 0;
	
	public static int counts=0;

	public static void updateBest(FlowCell fc){
		
		if (best == null){
			best = fc;
		}
		else if(fc.calculateFlowCellScore() > best.calculateFlowCellScore()){
			best = fc;
		}
	}
	
	public static void reset(){
		best = null;
	}
}
