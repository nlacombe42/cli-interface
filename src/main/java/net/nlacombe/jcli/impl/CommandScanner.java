package net.nlacombe.jcli.impl;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import net.nlacombe.jcli.api.Argument;
import net.nlacombe.jcli.api.Cli;
import net.nlacombe.jcli.api.Command;
import net.nlacombe.jcli.impl.domain.CommandDefinition;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandScanner
{
	private Map<String, CommandDefinition> commandDefinitions;

	public CommandScanner()
	{
		commandDefinitions = new HashMap<>();
	}

	public Map<String, CommandDefinition> getCommandsFromBasePackage(String basePackage)
	{
		FastClasspathScanner fastClasspathScanner = new FastClasspathScanner(basePackage);
		fastClasspathScanner.matchClassesWithAnnotation(Cli.class, this::loadCliClass).scan();

		return commandDefinitions;
	}

	private void loadCliClass(Class<?> cliClass)
	{
		if (!hasNoParamConstructor(cliClass))
			return;

		Arrays.stream(cliClass.getMethods())
				.filter(this::isCommandWithValidName)
				.forEach(this::addToCommandDefinitions);
	}

	private void addToCommandDefinitions(Method method)
	{
		Command command = method.getAnnotation(Command.class);
		List<String> argumentNames = getArgumentNames(method);
		CommandDefinition commandDefinition = new CommandDefinition(command.name(), command.description(), method, argumentNames);

		commandDefinitions.put(commandDefinition.getName(), commandDefinition);
	}

	private List<String> getArgumentNames(Method method)
	{
		return Arrays.stream(method.getParameters())
				.map(this::getParameterName)
				.collect(Collectors.toList());
	}

	private String getParameterName(Parameter parameter)
	{
		Argument argument = parameter.getAnnotation(Argument.class);

		if (argument != null && StringUtils.isNotBlank(argument.value()))
			return argument.value();
		else
			return parameter.getName();
	}

	private boolean isCommandWithValidName(Method method)
	{
		Command command = method.getAnnotation(Command.class);

		return command != null && StringUtils.isNotBlank(command.name());
	}

	private boolean hasNoParamConstructor(Class<?> clazz)
	{
		try
		{
			clazz.getConstructor();
			return true;
		}
		catch (NoSuchMethodException e)
		{
			return false;
		}
	}
}
