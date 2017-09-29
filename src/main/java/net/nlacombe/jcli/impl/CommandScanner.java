package net.nlacombe.jcli.impl;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import net.nlacombe.jcli.api.CliExceptionHandler;
import net.nlacombe.jcli.api.CliMapping;
import net.nlacombe.jcli.api.CommandMapping;
import net.nlacombe.jcli.impl.domain.Cli;
import net.nlacombe.jcli.impl.domain.Command;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandScanner
{
	public Cli getCliFromBasePackage(String basePackage)
	{
		Cli cli = new Cli();

		FastClasspathScanner classpathScanner = new FastClasspathScanner(basePackage);
		classpathScanner.matchClassesWithAnnotation(CliMapping.class, cliMappingClass -> addCommandsToCli(cli, cliMappingClass)).scan();
		classpathScanner.matchClassesWithMethodAnnotation(CliExceptionHandler.class,
				(clazz, cliExceptionHandlerMethod) -> cli.setExceptionHandler((Method) cliExceptionHandlerMethod)).scan();

		return cli;
	}

	private void addCommandsToCli(Cli cli, Class<?> cliMappingClass)
	{
		if (!hasNoParamConstructor(cliMappingClass))
			return;

		cli.addCommands(getCommands(cliMappingClass));
	}

	private List<Command> getCommands(Class<?> cliMappingClass)
	{
		return new ArrayList<>(getCommandsByMethodSignature(cliMappingClass).values());
	}

	private Map<String, Command> getCommandsByMethodSignature(Class<?> cliMappingClass)
	{
		Map<String, Command> parentCommands = getParentCommandsByMethodSignature(cliMappingClass);
		Map<String, Command> currentCommands = getCommandsByMethodSignature(parentCommands, cliMappingClass.getMethods());
		Map<String, Command> allCommands = new HashMap<>(parentCommands.size() + currentCommands.size());

		allCommands.putAll(parentCommands);
		allCommands.putAll(currentCommands);

		return allCommands;
	}

	private Map<String, Command> getParentCommandsByMethodSignature(Class<?> cliMappingClass)
	{
		Map<String, Command> allParentsCommands = new HashMap<>();

		Arrays.stream(cliMappingClass.getInterfaces()).forEach(cliClass ->
		{
			Map<String, Command> currentParentCommands = getCommandsByMethodSignature(cliClass);
			allParentsCommands.putAll(currentParentCommands);
		});

		return allParentsCommands;
	}

	private Map<String, Command> getCommandsByMethodSignature(Map<String, Command> parentCommands, Method[] methods)
	{
		Map<String, Command> commands = new HashMap<>();

		Arrays.stream(methods)
				.forEach(method ->
				{
					String methodSignature = getMethodSignature(method);

					if (hasCommandMappingWithValidName(method))
						commands.put(methodSignature, CommandFactory.getCommand(method));
					else if (parentCommands.containsKey(methodSignature))
					{
						Command parentCommand = parentCommands.get(methodSignature);
						parentCommand.setMethod(method);
						commands.put(methodSignature, parentCommand);
					}
				});

		return commands;
	}

	private String getMethodSignature(Method method)
	{
		return method.getName() + "(" + StringUtils.join(method.getParameterTypes(), ",") + ")";
	}

	private boolean hasCommandMappingWithValidName(Method method)
	{
		CommandMapping commandMapping = method.getAnnotation(CommandMapping.class);

		return commandMapping != null;
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
