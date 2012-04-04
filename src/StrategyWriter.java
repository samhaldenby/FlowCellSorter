import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class StrategyWriter {

	public static void write(String fileName) throws IOException {
		 FileWriter fstream = new FileWriter(fileName);
		 BufferedWriter out = new BufferedWriter(fstream);
		 
		 int newLaneNumber = 1;
		 for(Lane iLane : Scores.best.NonEmptyLanes()){
//			 out.write("Lane " + Integer.toString(newLaneNumber));
			 for(Sample iSample : iLane.getSamples()){
				 String line = String.format("%d\t%s\t%s\t%.2f\t%s\n", newLaneNumber, iSample.Name(), (iSample.isBarcoded() ? iSample.Barcode() : ""), iSample.Reads(), (iSample.isPooled() ? Integer.toString(iSample.Pool()) : ""));
				 
				out.write(line);
			 }
			 
//			 out.write("\n");
			 
			 ++newLaneNumber;
//			 out.write(s.Name()+"\t"+s.Barcode()+"\t"+s.Reads()+"\t"+s.Pool()+"\n");
		 }
		 
		 out.close();
		// TODO Auto-generated method stub
		
	}

}
