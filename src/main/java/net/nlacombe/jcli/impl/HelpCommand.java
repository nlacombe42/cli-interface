package net.nlacombe.jcli.impl;

import net.nlacombe.jcli.impl.domain.CommandDefinition;
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

	public void printHelp(Map<String, CommandDefinition> commandDefinitions)
	{
		logger.info(getHelpText(commandDefinitions));
	}

	public void printHelp(CommandDefinition commandDefinition)
	{
		logger.info("Usage: \r\n" + getCommandHelpText(commandDefinition));
	}

	private String getHelpText(Map<String, CommandDefinition> commandDefinitions)
	{
		StringBuilder helpText = new StringBuilder();

		helpText.append("Usage: COMMAND [...]\r\n\r\n");
		helpText.append("COMMAND:\r\n");

		commandDefinitions.keySet().stream()
				.sorted()
				.forEach(commandName -> helpText.append(getCommandHelpText(commandDefinitions.get(commandName))));

		return helpText.toString();
	}

	private String getCommandHelpText(CommandDefinition commandDefinition)
	{
		String description = commandDefinition.getDescription();

		String commandHelpText = "";

		commandHelpText += getCommandDeclarationLine(commandDefinition);

		if (StringUtils.isNotBlank(description))
			commandHelpText += "\t\t" + description + "\r\n";

		commandHelpText += "\r\n";

		return commandHelpText;
	}

	private String getCommandDeclarationLine(CommandDefinition commandDefinition)
	{
		List<String> argumentNames = commandDefinition.getArgumentNames();

		String commandDeclaration = "";

		commandDeclaration += "\t" + commandDefinition.getName() + " ";

		if (CollectionUtils.isNotEmpty(argumentNames))
			commandDeclaration += argumentNames.stream().map(String::toUpperCase).collect(Collectors.joining(" "));

		commandDeclaration += "\r\n";

		return commandDeclaration;
	}
}
