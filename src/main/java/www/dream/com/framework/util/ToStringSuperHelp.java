package www.dream.com.framework.util;

public class ToStringSuperHelp {
	public static String trimSuperString(String string) {
		return string.substring(string.indexOf('[') + 1, string.indexOf(']'));
	}
}
