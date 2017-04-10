package cn.team.shell.command.io;

import java.io.File;

import cn.team.shell.command.AbstractCommand;
import cn.team.shell.command.CommonValues;

public class CdCommand extends AbstractCommand {

	public CdCommand(String path) {
		this.executeArgs = path;
	}
	
	@Override
	public void execute() {
		String path = this.executeArgs;
		if (path == null) {
			return;
		}
		if (path.equals(".")) {
			return;
		}
		
		if (path.equals("..")) {
			changeToParentPath();
		} else if (isAbsolutePath(path)) {
			changeToAbsolutePath(path);
		} else {
			changeToRelativePath(path);
		}
		
	}

	private void changeToRelativePath(String args) {
		String tmpPath;
		if (CommonValues.currentPath.endsWith("\\")) {
			tmpPath = CommonValues.currentPath + args;
		} else {
			tmpPath = CommonValues.currentPath + "\\" + args;
		}
		
		if (isAbsolutePath(tmpPath)) {
			changeToAbsolutePath(tmpPath);
		} else {
			System.out.println("No such path: " + args);
		}
	}

	private void changeToAbsolutePath(String path) {
		CommonValues.currentPath = path;
	}

	private void changeToParentPath() {
		String parentPath = getParentPath(CommonValues.currentPath);
		if (parentPath == null) {
			System.out.println("No parent path");
		} else {
			changeToAbsolutePath(parentPath);
		}
	}

	private boolean isAbsolutePath(String path) {
		File file = new File(path);
		if (file.isDirectory() && file.isAbsolute()) {
			return true;
		}
		return false;
	}
	
	private String getParentPath(String path) {
		File file = new File(path);
		String parentPath = file.getParent();
		return parentPath;
	}
}
