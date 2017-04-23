package shell.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shell.exception.InvalidPathException;

public class IoUtil {

	public static String printInvalidPath(String path) {
		String invalidPathMessage = "bash: cd: " + path + ": No such file or directory";
		System.out.println(invalidPathMessage);
		return invalidPathMessage;
	}
	
	public static boolean isAbsolutePath(String path) {
		if (path.startsWith("/")) {
			return true;
		}
		return false;
	}
	
	public static String formatPath(String path) {
		if (path.endsWith("/"))
			return path;
		else 
			return path + "/";
		
	}
	
	public static String getParentPath(String path) {
		File file = new File(path);
		String parentPath = file.getParent();
		return parentPath;
	}
	
	/**
	 * 检查是否为合法的linux目录
	 * @param    path
	 * @throws InvalidPathException 
	 * */
	public static boolean validLinuxPath(String path) {
		String regex = "";
		if (path.length() > 2) {
			regex = "/[a-zA-Z]/";
		} else {
			regex = "/[a-zA-Z]";
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(path);
		if (!matcher.lookingAt()) {
			return false;
		}
		return true;
	}
	
	public static String transPathFromLinuxToWin(String path) throws InvalidPathException{
		if (!validLinuxPath(path)) {
			throw new InvalidPathException(path);
		}
		char diskLetter = path.charAt(1);
		String newPath = "";
		if (path.length() == 2) {
			newPath = diskLetter + ":/";
		} else {
			newPath = diskLetter + ":" + path.substring(2, path.length());
		}
		return newPath;
	}
	
	/**
	 * @param    path   入参应为windows的合法目录
	 * */
	public static String transPathFromWinToLinux(String path) {
		String newPath = path.replaceAll("\\\\", "/");
		newPath = newPath.replaceFirst(":", "");
		newPath = "/" + newPath;
		return newPath;
	}
}
