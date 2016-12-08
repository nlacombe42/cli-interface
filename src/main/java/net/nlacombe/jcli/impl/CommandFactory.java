package net.nlacombe.jcli.impl;

import net.nlacombe.jcli.api.CommandMapping;
import net.nlacombe.jcli.api.ParameterMapping;
import net.nlacombe.jcli.impl.domain.Command;
import net.nlacombe.jcli.impl.domain.Parameter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandFactory
{
	public static Command getCommand(Method method)
	{
		String commandName = getCommandName(method);
		List<Parameter> parameters = getParameters(method);
		CommandMapping commandMapping = method.getAnnotation(CommandMapping.class);

		return new Command(commandName, commandMapping.description(), method, parameters);
	}

	private static String getCommandName(Method method)
	{
		CommandMapping commandMapping = method.getAnnotation(CommandMapping.class);

		if (StringUtils.isNotBlank(commandMapping.name()))
			return commandMapping.name();

		return method.getName();
	}

	private static List<Parameter> getParameters(Method method)
	{
		return Arrays.stream(method.getParameters())
				.map(methodParameter ->
				{
					ParameterMapping parameterMapping = methodParameter.getAnnotation(ParameterMapping.class);
					String name = parameterMapping != null && StringUtils.isNotBlank(parameterMapping.value()) ?
							parameterMapping.value() : "";
					String description = parameterMapping != null && StringUtils.isNotBlank(parameterMapping.description()) ?
							parameterMapping.description() : "";

					return new Parameter(name, description);
				})
				.collect(Collectors.toList());
	}
}
