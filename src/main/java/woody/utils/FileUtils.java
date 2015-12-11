package woody.utils;


import java.io.File;

public final class FileUtils {
	public static long fileLen = 0;
	public static void delFilesFromPath(File filePath) {
		File[] files = filePath.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()){
				files[i].delete();
			}else{
				delFilesFromPath(files[i]);
				files[i].delete();// 刪除文件夾
			}
		}
	}
	
	public  static long getFileLen(File filePath){
		fileLen= 0;
		return getFileLenFromPath(filePath);
	}
	
	public static long getFileLenFromPath(File filePath) {
		File[] files = filePath.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()){
				fileLen += files[i].length();
			}else{
				getFileLenFromPath(files[i]);
			}
		}
		return fileLen;
	}
	
	private FileUtils(){}
}

