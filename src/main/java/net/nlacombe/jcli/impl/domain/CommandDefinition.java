package net.nlacombe.jcli.impl.domain;

import java.lang.reflect.Method;
import java.util.List;

public class CommandDefinition
{
	private String name;
	private String description;
	private Method method;
	private List<String> argumentNames;

	public CommandDefinition(String name, String description, Method method, List<String> argumentNames)
	{
		this.name = name;
		this.description = description;
		this.method = method;
		this.argumentNames = argumentNames;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public Method getMethod()
	{
		return method;
	}

	public List<String> getArgumentNames()
	{
		return argumentNames;
	}
}
