import java.io.File;
import java.util.prefs.Preferences;


public class PreferenceStore {
	static public File LoadDirectory = null;
	static public File SaveDirectory = null;
	public static double PrefLaneCapacity;
		
		
	public static void setWorkingLoadDirectory(String dirName){
		LoadDirectory = new File(dirName);
	}
	
	public static void setWorkingSaveDirectory(String dirName){
		SaveDirectory = new File(dirName);
	}
	
	public static File CurrentLoadDirectory(){
		return (LoadDirectory==null ? new File("/") : LoadDirectory );
	}
	
	public static File CurrentSaveDirectory(){
		return (SaveDirectory==null ? new File("/") : SaveDirectory );
	}

	public static void SavePreferences() {
		//preferences
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		prefs.put("userLoadDir", LoadDirectory.toString());
		prefs.put("userSaveDir", SaveDirectory.toString());
		prefs.putDouble("userLaneCapacity", PrefLaneCapacity);
	}
	
	
	public static void LoadPreferences(){
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		PreferenceStore.LoadDirectory = new File(prefs.get("userLoadDir","/"));
		PreferenceStore.SaveDirectory = new File(prefs.get("userSaveDir","/"));
		PreferenceStore.PrefLaneCapacity = prefs.getDouble("prefLaneCapacity",1.d);
		
	}
}
