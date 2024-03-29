import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/** Static class for randomly generating a test samplesheet at runtime. For debugging purposes
*
* @author Sam Haldenby
*/
public class SampleSheetRandomiser {
	
	
	private static int NUM_SAMPLES =400;
	private static int NUM_POOLS = NUM_SAMPLES;
	private static float CHANCE_OF_POOLING=0.1f;
	
	public static ArrayList<Sample> create() throws IOException{
		ArrayList<Sample> samples = new ArrayList<Sample>();
		int numOfSamples = (int)(Math.random()*1+NUM_SAMPLES);
		
		float num = 0.66f;
		HashMap<Integer,HashSet<String>> poolHash = new HashMap<Integer,HashSet<String>>();
		ArrayList<String> barcodes = new ArrayList<String>();
		barcodes.add("ATCACG");
		barcodes.add("CGATGT");
		barcodes.add("TTAGGC");
		barcodes.add("TGACCA");
		barcodes.add("ACAGTG");
		barcodes.add("GCCAAT");
		barcodes.add("CAGATC");
		barcodes.add("ACTTGA");
		barcodes.add("GATCAG");
		barcodes.add("TAGCTT");
		barcodes.add("GGCTAC");
		barcodes.add("CTTGTA");
		barcodes.add("AGTCAA");
		barcodes.add("AGTTCC");
		barcodes.add("ATGTCA");
		barcodes.add("CCGTCC");
		barcodes.add("GTAGAG");
		barcodes.add("GTCCGC");
		barcodes.add("GTGAAA");
		barcodes.add("GTGGCC");
		barcodes.add("GTTTCG");
		barcodes.add("CGTACG");
		barcodes.add("GAGTGG");
		barcodes.add("GGTAGC");
		barcodes.add("ACTGAT");
		barcodes.add("ATGAGC");
		barcodes.add("ATTCCT");
		barcodes.add("CAAAAG");
		barcodes.add("CAACTA");
		barcodes.add("CACCGG");
		barcodes.add("CACGAT");
		barcodes.add("CACTCA");
		barcodes.add("CAGGCG");
		barcodes.add("CATGGC");
		barcodes.add("CATTTT");
		barcodes.add("CCAACA");
		barcodes.add("CGGAAT");
		barcodes.add("CTAGCT");
		barcodes.add("CTATAC");
		barcodes.add("CTCAGA");
		barcodes.add("GACGAC");
		barcodes.add("TAATCG");
		barcodes.add("TACAGC");
		barcodes.add("TATAAT");
		barcodes.add("TCATTC");
		barcodes.add("TCCCGA");
		barcodes.add("TCGAAG");
		barcodes.add("TCGGCA");
		
		
		//create pools
		for(int p=0; p<NUM_POOLS; ++p){
			poolHash.put(p, new HashSet<String>());
		}
		
		Random r = new Random();
		for(int s= 0; s< numOfSamples; ++s){
			int rnd= (int)(Math.random()*6);
			
			if(rnd==0) num=0.25f;
			else if(rnd==1) num=0.66f;
			else if(rnd==2) num=0.5f;
			else if(rnd==3) num=0.33f;
			else if(rnd==4) num=0.1f;
			else if (rnd==5) num=0.75f;
			num = r.nextFloat() *0.5f;	//comment this out if you want more realistic sample sizes			
	
			String sampleName = "S" + Integer.toString(s);
			String barcode = barcodes.get((int)(Math.random()*24));
			
			//should this be pooled?
			int pool = Consts.NO_POOL;
			if(r.nextFloat()<CHANCE_OF_POOLING){
				//Pooling info
				//only add to a pool if a sample of that barcode does not already exist
				
				int randPoolNum = r.nextInt(NUM_POOLS);
				int poolingAttempt=0;
				while(poolHash.get(randPoolNum).contains(barcode)){
					if(++poolingAttempt==100){
						System.out.println("SampleSheetRandomiser failed to add all samples to pools. Please restart");
						System.in.read();
					}
					randPoolNum = r.nextInt(NUM_POOLS);
				}
				
				//add to pool
				HashSet<String> tempBarcodeSet = poolHash.get(randPoolNum);
				tempBarcodeSet.add(barcode);
				poolHash.put(randPoolNum, tempBarcodeSet);
				pool = randPoolNum;
			}
			
			



			Sample sample = new Sample(sampleName,barcode, num, pool);
			sample.setBarcoded(true);
			System.out.printf("Sample: %s\n",sample.print());
			samples.add(new Sample(sampleName,barcode,num, pool));
			System.out.printf("num=%.2f\n",num);
		}
		
		
		return samples;
	}
	
}
