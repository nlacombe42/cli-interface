package net.nlacombe.jcli.impl;

import net.nlacombe.jcli.impl.domain.Cli;
import net.nlacombe.jcli.impl.domain.Command;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HelpCommand
{
	private static final Logger logger = LoggerFactory.getLogger(HelpCommand.class);

	public void printHelp(Cli cli)
	{
		logger.info(getHelpText(cli));
	}

	public void printHelp(Command command)
	{
		logger.info("Usage: \r\n" + getCommandHelpText(command));
	}

	private String getHelpText(Cli cli)
	{
		StringBuilder helpText = new StringBuilder();

		helpText.append("Usage: COMMAND [...]\r\n\r\n");
		helpText.append("COMMAND:\r\n");

		Map<String, Command> commandsByName = cli.getCommandsByName();

		commandsByName.keySet().stream()
				.sorted()
				.forEach(commandName -> helpText.append(getCommandHelpText(commandsByName.get(commandName))));

		return helpText.toString();
	}

	private String getCommandHelpText(Command command)
	{
		String description = command.getDescription();

		String commandHelpText = "";

		commandHelpText += getCommandDeclarationLine(command);

		if (StringUtils.isNotBlank(description))
			commandHelpText += "\t\t" + description + "\r\n";

		commandHelpText += "\r\n";

		return commandHelpText;
	}

	private String getCommandDeclarationLine(Command command)
	{
		List<String> argumentNames = command.getArgumentNames();

		String commandDeclaration = "";

		commandDeclaration += "\t" + command.getName() + " ";

		if (CollectionUtils.isNotEmpty(argumentNames))
			commandDeclaration += argumentNames.stream().map(String::toUpperCase).collect(Collectors.joining(" "));

		commandDeclaration += "\r\n";

		return commandDeclaration;
	}
}
