/** Class representing a single sample of a lane
*
* @author Sam Haldenby
*/
public class Sample {
	
	private String name_;
	private String barcode_;
	private double numReads_;
	private int pool_;
	private boolean barcoded_;
	
	public Sample(String name, String barcode, double numReads, int pool){
		name_ = name;
		barcode_ = barcode;
		numReads_ = numReads;
		pool_ = pool;

	}
	
	public String Name(){
		return name_;
	}
	
	public String Barcode(){
		return barcode_;
	}
	
	public double Reads(){
		return numReads_;
	}
	
	public int Pool(){
		return pool_;
	}
	
	public boolean isBarcoded(){
		return barcoded_;
	}
	
	public boolean isPooled(){
		return (pool_!=Consts.NO_POOL);
	}

	public void setBarcoded(boolean barcoded) {
		barcoded_=barcoded;
	}

	public String print() {
		return String.format("[%s-%s-%.2f-%d]", this.name_, this.barcode_, this.numReads_, this.pool_); 
	}

}
