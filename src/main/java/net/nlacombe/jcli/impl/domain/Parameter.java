package net.nlacombe.jcli.impl.domain;

public class Parameter implements Comparable<Parameter>
{
	private String name;
	private String description;

	public Parameter(String name, String description)
	{
		this.name = name;
		this.description = description;
	}

	@Override
	public int compareTo(Parameter parameter)
	{
		if (name == null || parameter == null)
			return 1;

		return name.compareTo(parameter.getName());
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}
}
