package shell.command.io;

import shell.command.AbstractCommand;
import shell.util.CommonValues;

public class PwdCommand extends AbstractCommand {

	@Override
	public void execute() {
		System.out.println(CommonValues.currentPath);
	}
}
