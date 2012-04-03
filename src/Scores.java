import java.util.ArrayList;


public class Scores {
	public static ArrayList<Double> p = new ArrayList<Double>();
	public static ArrayList<Double> s = new ArrayList<Double>();
	public static ArrayList<Double> p2 = new ArrayList<Double>();
	public static ArrayList<Double> s2 = new ArrayList<Double>();
	public static double totRawToP1 =0.0f;
	public static double totP1ToP2 =0.0f;
	public static double totP2ToS1 =0.0f;
	public static double totS1ToS2 =0.0f;
	public static double totS2ToS3 =0.0f;
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

//		display_.update();
		
	}
	
	public static void report(){
		System.out.printf("Polish: ");
		for(int a=0; a< p.size(); ++a){
			System.out.printf("\t%.2f",p.get(a)/counts);
		}
		System.out.println();
		
		System.out.printf("  Swap: ");
		for(int b=0; b< s.size(); ++b){
			System.out.printf("\t%.2f",s.get(b)/counts);
		}
		System.out.println();
		
		System.out.printf("Polish: ");
		for(int c=0; c< p2.size(); ++c){
			System.out.printf("\t%.2f",p2.get(c)/counts);
		}
		System.out.println();
		
		System.out.printf(" Swap2: ");
		for(int d=0; d< s2.size(); ++d){
			System.out.printf("\t%.2f",s2.get(d)/counts);
		}
		System.out.println();
	}
}
