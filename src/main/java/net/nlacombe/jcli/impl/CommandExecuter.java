package net.nlacombe.jcli.impl;

import net.nlacombe.jcli.impl.domain.Cli;
import net.nlacombe.jcli.impl.domain.Command;
import net.nlacombe.jcli.impl.exception.CliUsageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CommandExecuter
{
	private static final Logger logger = LoggerFactory.getLogger(CommandExecuter.class);

	public void executeCommand(Cli cli, List<String> arguments)
	{
		try
		{
			validateArgumentContainsCommand(arguments);

			String commandName = arguments.get(0);
			Command command = cli.getCommandByName(commandName);
			validateCommandExists(commandName, command);

			arguments.remove(0);
			executeCommand(command, arguments);
		}
		catch (CliUsageException cliUsageException)
		{
			printErrorAndHelp(cli, cliUsageException);
		}
	}

	private void printErrorAndHelp(Cli cli, CliUsageException cliUsageException)
	{
		logger.error(cliUsageException.getMessage() + "\r\n");

		printHelp(cli, cliUsageException);
	}

	private void printHelp(Cli cli, CliUsageException cliUsageException)
	{
		Command command = cliUsageException.getCommand();
		HelpCommand helpCommand = new HelpCommand();

		if (command != null)
			helpCommand.printHelp(command);
		else
			helpCommand.printHelp(cli);
	}

	private void executeCommand(Command command, List<String> commandArguments)
	{
		Method commandMethod = command.getMethod();

		validateEqualNumberOfArgumentAndParameter(command, commandArguments.size(), commandMethod.getParameterCount());

		try
		{
			Class<?> cliClass = commandMethod.getDeclaringClass();
			Object cliClassInstance = cliClass.getConstructor().newInstance();
			List<Object> convertedArguments = convertArguments(commandMethod.getParameterTypes(), commandArguments);

			commandMethod.invoke(cliClassInstance, convertedArguments.toArray());
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error: unknown error executing command.", e);
		}
	}

	private List<Object> convertArguments(Class<?>[] parameterTypes, List<String> commandArguments)
	{
		List<Object> convertedArguments = new ArrayList<>(commandArguments.size());

		for (Integer i = 0; i < commandArguments.size(); i++)
		{
			Class<?> parameterType = parameterTypes[i];
			String argument = commandArguments.get(i);

			convertedArguments.add(convertArgument(parameterType, argument));
		}

		return convertedArguments;
	}

	private Object convertArgument(Class<?> parameterType, String argument)
	{
		if (parameterType.equals(Integer.class))
			return new Integer(argument);
		else if (parameterType.equals(Double.class))
			return new Double(argument);
		else if (parameterType.equals(Path.class))
			return Paths.get(argument);
		else
			return argument;
	}

	private void validateEqualNumberOfArgumentAndParameter(Command command, Integer numberOfArguments, Integer numberOfParameters)
	{
		if (numberOfArguments < numberOfParameters)
			throw new CliUsageException(command, "not enough argument(s).");
		else if (numberOfArguments > numberOfParameters)
			throw new CliUsageException(command, "too much argument(s).");
	}

	private void validateArgumentContainsCommand(List<String> arguments)
	{
		if (arguments.isEmpty())
			throw new CliUsageException("no command specified.");
	}

	private void validateCommandExists(String commandName, Command command)
	{
		if (command == null)
			throw new CliUsageException("no command named \"" + commandName + "\"");
	}
}
