package shell.command.io;

import shell.command.AbstractCommand;

public class CatCommand extends AbstractCommand {

	public CatCommand(String fileName) {
		this.executeArgs = fileName;
	}
	public CatCommand(String commandArgs, String fileName) {
		this(fileName);
		this.commandArgs = commandArgs;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("cat");
	}

}
