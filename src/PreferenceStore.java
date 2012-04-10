import java.io.File;


public class PreferenceStore {
	static private File currentDirectory = null;
	public static String UserDir;
	public static double PrefLaneCapacity;
		
		
	public static void setWorkingDirectory(String dirName){
		currentDirectory = new File(dirName);
	}
	
	public static File CurrentDirectory(){
		return (currentDirectory==null ? new File("/") : currentDirectory );
	}
}
