package cn.team.shell.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import cn.team.shell.command.Executable;
import cn.team.shell.command.io.CatCommand;
import cn.team.shell.command.io.Cdcommand;
import cn.team.shell.command.io.PwdCommand;
import cn.team.shell.command.IoCommands;
import cn.team.shell.command.io.LsCommand;


public class Cilent {

	private static Scanner scanner;

	public static void main(String[] args) {
		console();
	}

	private static void console() {
		
		while (true) {
			Executable exec = null;
			printUserAndComputerName();
			Map<String, String> commandAndArgsMap = getInputCommandAndArgs();
			String command = commandAndArgsMap.get("command");
			String executeArgs = commandAndArgsMap.get("executeArgs");
			
			switch(command) {
				case IoCommands.cat:
					exec = new CatCommand(executeArgs);
					break;
				case IoCommands.cd:
					exec = new Cdcommand(executeArgs);
					break;
				case IoCommands.ls: 
					exec = new LsCommand(executeArgs);
					break;
				case IoCommands.pwd:
					exec = new PwdCommand();
					break;
				default:
					System.out.println("No such command: " + command);
					continue;
			}
			
			exec.execute();
		}
	}

	private static void printUserAndComputerName() {
		//TODO 后续加入用户管理后获取实际的用户名和机器名
		String user = "root";
		String computerName = "localhost";
		String printInfo = "[" + user + "@" + computerName + "]# ";
		System.out.print(printInfo);
	}
	
	private static Map<String, String> getInputCommandAndArgs() {
		String inputLine = getInputLine();
		//TODO 目前没有考虑到 类似 ls -l 的命令
		String[] commandAndArgsArray = inputLine.split(" ", 2);
		Map<String, String> commandAndArgsMap = new HashMap<String, String>();
		commandAndArgsMap.put("command", commandAndArgsArray[0]);
		if (commandAndArgsArray.length > 1) {
			commandAndArgsMap.put("executeArgs", commandAndArgsArray[1]);
		}
		return commandAndArgsMap;
	}

	private static String getInputLine() {
		scanner = new Scanner(System.in);
		String inputLine = scanner.nextLine();
		return inputLine;
	}
}
