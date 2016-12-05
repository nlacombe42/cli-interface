package net.nlacombe.jcli.impl;

import net.nlacombe.jcli.impl.domain.CommandDefinition;
import net.nlacombe.jcli.impl.exception.CliUsageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class CommandExecuter
{
	private static final Logger logger = LoggerFactory.getLogger(CommandExecuter.class);

	public void executeCommand(Map<String, CommandDefinition> commandDefinitions, List<String> arguments)
	{
		try
		{
			validateArgumentContainsCommand(arguments);

			String commandName = arguments.get(0);
			CommandDefinition commandDefinition = getCommandDefinition(commandDefinitions, commandName);

			arguments.remove(0);
			executeCommand(commandDefinition, arguments);
		}
		catch (CliUsageException cliUsageException)
		{
			printErrorAndHelp(commandDefinitions, cliUsageException);
		}
	}

	private void printErrorAndHelp(Map<String, CommandDefinition> commandDefinitions, CliUsageException cliUsageException)
	{
		logger.error(cliUsageException.getMessage() + "\r\n");

		printHelp(commandDefinitions, cliUsageException);
	}

	private void printHelp(Map<String, CommandDefinition> commandDefinitions, CliUsageException cliUsageException)
	{
		CommandDefinition commandDefinition = cliUsageException.getCommandDefinition();
		HelpCommand helpCommand = new HelpCommand();

		if (commandDefinition != null)
			helpCommand.printHelp(commandDefinition);
		else
			helpCommand.printHelp(commandDefinitions);
	}

	private CommandDefinition getCommandDefinition(Map<String, CommandDefinition> commandDefinitions, String commandName)
	{
		CommandDefinition commandDefinition = commandDefinitions.get(commandName);

		validateCommandExists(commandName, commandDefinition);

		return commandDefinition;
	}

	private void executeCommand(CommandDefinition commandDefinition, List<String> commandArguments)
	{
		Method commandMethod = commandDefinition.getMethod();

		validateEqualNumberOfArgumentAndParameter(commandDefinition, commandArguments.size(), commandMethod.getParameterCount());

		try
		{
			Class<?> cliClass = commandMethod.getDeclaringClass();
			Object cliClassInstance = cliClass.getConstructor().newInstance();

			commandMethod.invoke(cliClassInstance, commandArguments.toArray());
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error: unknown error executing command.", e);
		}
	}

	private void validateEqualNumberOfArgumentAndParameter(CommandDefinition commandDefinition, Integer numberOfArguments, Integer numberOfParameters)
	{
		if (numberOfArguments < numberOfParameters)
			throw new CliUsageException(commandDefinition, "not enough argument(s).");
		else if (numberOfArguments > numberOfParameters)
			throw new CliUsageException(commandDefinition, "too much argument(s).");
	}

	private void validateArgumentContainsCommand(List<String> arguments)
	{
		if (arguments.isEmpty())
			throw new CliUsageException("no command specified.");
	}

	private void validateCommandExists(String commandName, CommandDefinition commandDefinition)
	{
		if (commandDefinition == null)
			throw new CliUsageException("no command named \"" + commandName + "\"");
	}
}
