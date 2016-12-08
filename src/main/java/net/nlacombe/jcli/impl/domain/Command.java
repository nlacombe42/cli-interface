package net.nlacombe.jcli.impl.domain;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class Command
{
	private String name;
	private String description;
	private Method method;
	private List<Parameter> parameters;

	public Command(String name, String description, Method method, List<Parameter> parameters)
	{
		this.name = name;
		this.description = description;
		this.method = method;
		this.parameters = parameters;
	}

	public List<String> getParameterNames()
	{
		return parameters.stream().map(Parameter::getName).collect(Collectors.toList());
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

	public void setMethod(Method method)
	{
		this.method = method;
	}

	public List<Parameter> getParameters()
	{
		return parameters;
	}
}
