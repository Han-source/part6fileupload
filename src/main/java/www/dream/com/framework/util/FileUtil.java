package www.dream.com.framework.util;

public class FileUtil {
	//확장자를 제거해서 string으로 반환하기
	public static String truncateExt(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf('.'));
		
	}
}
