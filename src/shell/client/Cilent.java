package shell.client;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import shell.command.Executable;


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
			
			if (inValidCommand(command)) {
				printNoSuchCommand(command);
				continue;
			}
			String className = getClassName(command);
			try {
				Class<?> execClass = Class.forName(className);
				if (executeArgs == null) {
					exec = (Executable) execClass.newInstance();
				} else {
					Constructor<?> constructor = execClass.getConstructor(String.class);
					exec = (Executable) constructor.newInstance(executeArgs);
				}
				exec.execute();
			} catch (ClassNotFoundException e) {
				printNoSuchCommand(command);
				continue;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void printNoSuchCommand(String command) {
		System.out.println("bash: " + command + ": command not found");
	}

	private static String getClassName(String command) {
		String firstChar = command.substring(0, 1).toUpperCase();
		command = command.replaceFirst("[a-z]", firstChar);
		String packageName = "shell.command.io";
		String suffix = "Command";
		String className = packageName + "." + command + suffix;
		return className;
	}

	private static boolean inValidCommand(String command) {
		if (command == null) {
			return true;
		}
		command = command.trim();
		return !(command.matches("[a-z]+"));
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
			commandAndArgsMap.put("executeArgs", commandAndArgsArray[1].trim());
		}
		return commandAndArgsMap;
	}

	private static String getInputLine() {
		scanner = new Scanner(System.in);
		String inputLine = scanner.nextLine();
		return inputLine;
	}
}
