package net.nlacombe.jcli.impl;

import net.nlacombe.jcli.impl.domain.Cli;
import net.nlacombe.jcli.impl.domain.Command;
import net.nlacombe.jcli.impl.domain.Parameter;
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
				.map(commandsByName::get)
				.forEach(command -> helpText.append(getCommandHelpText(command)));

		return helpText.toString();
	}

	private String getCommandHelpText(Command command)
	{
		String description = command.getDescription();

		StringBuilder helpText = new StringBuilder();

		helpText.append(getCommandDeclarationLine(command));
		helpText.append(getParametersHelpText(command.getParameters()));

		if (StringUtils.isNotBlank(description))
			helpText.append(String.format("\t\t%s\r\n", description));

		helpText.append("\r\n");

		return helpText.toString();
	}

	private String getParametersHelpText(List<Parameter> parameters)
	{
		StringBuilder helpText = new StringBuilder();

		parameters.stream()
				.filter(parameter -> StringUtils.isNotBlank(parameter.getDescription()))
				.sorted()
				.forEach(parameter ->
						helpText.append(String.format("\t\t%s %s\r\n", parameter.getName().toUpperCase(), parameter.getDescription())));

		helpText.append("\r\n");

		return helpText.toString();
	}

	private String getCommandDeclarationLine(Command command)
	{
		List<String> argumentNames = command.getParameterNames();

		String commandDeclaration = "";

		commandDeclaration += "\t" + command.getName() + " ";

		if (CollectionUtils.isNotEmpty(argumentNames))
			commandDeclaration += argumentNames.stream().map(String::toUpperCase).collect(Collectors.joining(" "));

		commandDeclaration += "\r\n";

		return commandDeclaration;
	}
}
