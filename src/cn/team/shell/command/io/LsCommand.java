package cn.team.shell.command.io;

import cn.team.shell.command.AbstractCommand;

public class LsCommand extends AbstractCommand {

	public LsCommand(String fileName) {
		this.executeArgs = fileName;
	}
	public LsCommand(String commandArgs, String fileName) {
		this(fileName);
		this.commandArgs = commandArgs;
	}
	
	@Override
	public void execute() {
		//TODO
		System.out.println("ls");
	}

}
