import java.io.File;


public class Preferences {
	static private File currentDirectory = null;
		
		
	public static void setWorkingDirectory(String dirName){
		currentDirectory = new File(dirName);
	}
	
	public static File CurrentDirectory(){
		return (currentDirectory==null ? new File("/") : currentDirectory );
	}
}
