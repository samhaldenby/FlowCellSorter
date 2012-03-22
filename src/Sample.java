
public class Sample {
	
	private String name_;
	private String barcode_;
	private double numReads_;
	
	public Sample(String name, String barcode, double numReads){
		name_ = name;
		barcode_ = barcode;
		numReads_ = numReads;
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
}
