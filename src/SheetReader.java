import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class SheetReader {
	public static ArrayList<Sample> read(String fileName) throws IOException{
		
		//create temp hashmap of sample names to cross ref to;
		HashMap<String, Integer> sampleNameMap = new HashMap<String, Integer>();
		
		
		//create empty sample list to return 
		ArrayList<Sample> samples = new ArrayList<Sample>();
		
		//open and read file
		FileInputStream fstream = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String line;
		
		while((line = br.readLine()) != null){
			System.out.println(line);
			String[] tokens = line.split("\t");
			if(tokens.length>=3){
				//check if it's already in map
				String name = tokens[0];
				String barcode = tokens[1];
				double reads = Double.parseDouble(tokens[2]);
				int pool = Consts.NO_POOL;
				if(tokens.length>3){
					pool = Integer.parseInt(tokens[3]);
				}
				
				if(sampleNameMap.containsKey(name)){
					sampleNameMap.put(name, sampleNameMap.get(name)+1);
					name = name + "_" + Integer.toString(sampleNameMap.get(name));
				}else{
					sampleNameMap.put(name,0);
				}
				samples.add(new Sample(name,barcode,reads, pool));
			}
		}
		System.out.printf("There are %d samples loaded!\n", samples.size());
		return samples;
	}
	
	
	public static void write(ArrayList<Sample> samples, String fileName) throws IOException{
		 FileWriter fstream = new FileWriter(fileName);
		 BufferedWriter out = new BufferedWriter(fstream);
		 
		 for(Sample s : samples){
			 out.write(s.Name()+"\t"+s.Barcode()+"\t"+s.Reads()+"\t"+s.Pool()+"\n");
		 }
		 
		 out.close();
		
	}
}
