package utils;
import java.io.File;
import java.util.ArrayList;

public class FolderSearcher {
	private String folder;
	
	public FolderSearcher() {
		
	}
	
	public String getFolderIfExist(String dir, int levels, String folderName) {
		listFolders(dir, levels, folderName);
		return folder;
	}
	
	private void listFolders(String dir, int levels, String folderName) {
		if(levels == 0) {
			return;
		}
		try {
			File path = new File(dir);
			File[] filesList = path.listFiles();
			for (File file : filesList) {
				if (file.isDirectory()) {
					String fullPath = file.getAbsolutePath() + folderName;
					String fullRootPath = dir + folderName;
					if (new File(fullPath).exists()){
						folder = fullPath;
					}
					if (new File(fullRootPath).exists()) {
						folder = fullRootPath;
					}
					listFolders(file.getAbsolutePath(), levels - 1, folderName);
				}
			}
		} 
		catch (Exception e) {
			return;
		}
	}
	
	public ArrayList<String> getWindowsDrives(){
		ArrayList<String> results = new ArrayList<String>();
		File[] paths;
		paths = File.listRoots();
		for(File path:paths) {
			results.add(path.getAbsolutePath());
		}
		return results;
	}
}
