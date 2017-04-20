package shell.command.io;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shell.command.AbstractCommand;
import shell.util.CommonValues;
import shell.exception.InvalidPathException;
import shell.util.IoUtil;

public class CdCommand extends AbstractCommand {

	public CdCommand() {}
	
	public CdCommand(String path) {
		this.executeArgs = path;
	}
	
	@Override
	public void execute() {
		
		// 参数path为类似linux的目录格式
		String path = this.executeArgs;
		
		if (emptyPath(path)) {
			changeToOriginPath();
		} else if (path.equals(".")) {
			return;
		} else if (path.equals("..")) {
			changeToParentPath();
		} else if (IoUtil.isAbsolutePath(path)) {
			changeToAbsolutePath(path);
		} else {
			changeToRelativePath(path);
		}
	}

	private void changeToOriginPath() {
		CommonValues.currentPath = CommonValues.originPath;
	}

	private boolean emptyPath(String path) {
		if (path == null)
			return true;
		if (path.length() == 0)
			return true;
		return false;
	}

	private void changeToRelativePath(String args) {
		String tmpPath;
		if (CommonValues.currentPath.endsWith("/")) {
			tmpPath = CommonValues.currentPath + args;
		} else {
			tmpPath = CommonValues.currentPath + "/" + args;
		}
		
		changeToAbsolutePath(tmpPath);
	}

	private void changeToAbsolutePath(String path) {
		String windowsPath = "";
		try {
			windowsPath = transPathFromLinuxToWin(path);
		} catch (InvalidPathException e) {
			IoUtil.printInvalidPath(path);
			return;
		}
		File file = new File(windowsPath);
		if (file.isDirectory()) {
			CommonValues.currentPath = path.replaceAll("\\\\", "/");
		} else {
			IoUtil.printInvalidPath(path);
		}
	}

	private void changeToParentPath() {
		String currentPath = null;
		try {
			currentPath = IoUtil.transPathFromLinuxToWin(CommonValues.currentPath);
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String parentPath = IoUtil.getParentPath(currentPath);
		if (parentPath == null) {
			System.out.println("No parent path");
		} else {
			CommonValues.currentPath = IoUtil.transPathFromWinToLinux(parentPath);
		}
	}

	private String transPathFromLinuxToWin(String path) throws InvalidPathException{
		String regex = "";
		if (path.length() > 2) {
			regex = "/[a-zA-Z]/";
		} else {
			regex = "/[a-zA-Z]";
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(path);
		if (!matcher.lookingAt()) {
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
}
