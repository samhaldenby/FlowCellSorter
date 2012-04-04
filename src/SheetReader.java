import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/** Reads formatted sample sheets and converts into Samples
*
* @author Sam Haldenby
*/
public class SheetReader {
	
	
	/** Static function to read in sample sheet. Sheet must have 4+ columns: Name, Barcode(optional), Reads required, Pool(optional)
	 * 
	 * @throws IOException 		
	 * @param fileName		Full path and name of sample sheet
	 * @return		Extracted samples
	 */
	public static ArrayList<Sample> read(String fileName) throws IOException{
		
		HashMap<String, Integer> sampleNameMap = new HashMap<String, Integer>();	//needed for checking against to ensure no duplicate sample names are entered
		
		ArrayList<Sample> samples = new ArrayList<Sample>();	//will be filled and returned
		
		//open and read file
		FileInputStream fstream = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		int tempBarcodeNumber = 1;	//this is assigned to non-barcoded samples (and iterated) as a method of ensuring all non-barcoded samples aren't lumped into the barcode "" pool
		while((line = br.readLine()) != null){
			System.out.println(line);
			String[] tokens = line.split("\t");
			if(tokens.length>=3){

				String name = tokens[0];
				String barcode = tokens[1];
				
				//give a temporary unique barcode if not barcoded
				boolean barcoded = barcode.length()>0;
				if(!barcoded){
					barcode = "NoBC"+Integer.toString(tempBarcodeNumber);
					tempBarcodeNumber++;
				}
				double reads = Double.parseDouble(tokens[2]);
				int pool = Consts.NO_POOL;
				if(tokens.length>3){
					pool = Integer.parseInt(tokens[3]);	//only read pool if provided, else default as no pool
				}
				
				//check for duplicate names and rename if necessary (e.g. A060001 -> A060001_1
				if(sampleNameMap.containsKey(name)){
					sampleNameMap.put(name, sampleNameMap.get(name)+1);
					name = name + "_" + Integer.toString(sampleNameMap.get(name));
				}else{
					sampleNameMap.put(name,0);
				}
				Sample sample = new Sample(name,barcode,reads,pool);
				sample.setBarcoded(barcoded);
				samples.add(sample);
			}
		}
		System.out.printf("There are %d samples loaded!\n", samples.size());
		
//		samples = SampleSheetRandomiser.create(); //TODO : Remove this - debug purposes only!
		return samples;
	}
	
}
