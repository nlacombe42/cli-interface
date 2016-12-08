package net.nlacombe.jcli.impl;

import net.nlacombe.jcli.impl.domain.Cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Jcli
{
	public void run(String basePackage, String[] argumentsArray)
	{
		CommandScanner commandScanner = new CommandScanner();
		Cli cli = commandScanner.getCliFromBasePackage(basePackage);

		List<String> arguments = new ArrayList<>(Arrays.asList(argumentsArray));
		CommandExecuter commandExecuter = new CommandExecuter();
		commandExecuter.executeCommand(cli, arguments);
	}
}
