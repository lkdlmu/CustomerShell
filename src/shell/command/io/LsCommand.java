package shell.command.io;

import java.io.File;

import shell.command.AbstractCommand;
import shell.exception.InvalidPathException;
import shell.util.CommonValues;
import shell.util.IoUtil;

public class LsCommand extends AbstractCommand {

	public LsCommand() {}
	
	public LsCommand(String fileName) {
		this.executeArgs = fileName;
	}
	public LsCommand(String commandArgs, String fileName) {
		this(fileName);
		this.commandArgs = commandArgs;
	}
	
	@Override
	public void execute() {
		if (this.executeArgs == null) {
			this.executeArgs = CommonValues.currentPath;
		} else if (!IoUtil.isAbsolutePath(this.executeArgs)) {
			this.executeArgs = CommonValues.currentPath + this.executeArgs;
		}
		
		try {
			ListFiles(this.executeArgs);
		} catch (InvalidPathException e) {
			IoUtil.printInvalidPath(this.executeArgs);
		}
	}

	private void ListFiles(String fileName) throws InvalidPathException {
		fileName = IoUtil.transPathFromLinuxToWin(fileName);
		File filePath = new File(fileName);
		if (filePath.isFile()) {
			System.out.println(fileName);
		} else if (!filePath.isDirectory()) {
			throw new InvalidPathException(fileName);
		}
		File[] files = filePath.listFiles();
		for(File file : files) {
			if (file.isHidden())
				continue;
			if (file.isDirectory()) {
				System.out.println(file.getName() + "/");
			} else {
				System.out.println(file.getName());
			}
		}
	}
	
}
