package cn.team.shell.command.io;

import cn.team.shell.command.AbstractCommand;
import cn.team.shell.command.CommonValues;

public class PwdCommand extends AbstractCommand {

	@Override
	public void execute() {
		System.out.println(CommonValues.currentPath);
	}
}
