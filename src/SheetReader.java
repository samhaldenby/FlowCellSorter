import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SheetReader {
	public static ArrayList<Sample> read(String fileName) throws IOException{
		//create empty sample list to return 
		ArrayList<Sample> samples = new ArrayList<Sample>();
		
		//open and read file
		FileInputStream fstream = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String line;
		
		while((line = br.readLine()) != null){
			String[] tokens = line.split("\t");
			if(tokens.length==3){
				samples.add(new Sample(tokens[0],tokens[1],Double.parseDouble(tokens[2])));
			}
		}
		return samples;
	}
}
