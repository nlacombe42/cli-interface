package net.nlacombe.jcli.impl;

import net.nlacombe.jcli.impl.domain.CommandDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Jcli
{
	public void run(String basePackage, String[] argumentsArray)
	{
		CommandScanner commandScanner = new CommandScanner();
		Map<String, CommandDefinition> commandDefinitions = commandScanner.getCommandsFromBasePackage(basePackage);

		List<String> arguments = new ArrayList<>(Arrays.asList(argumentsArray));
		CommandExecuter commandExecuter = new CommandExecuter();
		commandExecuter.executeCommand(commandDefinitions, arguments);
	}
}
