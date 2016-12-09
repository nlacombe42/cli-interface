package net.nlacombe.jcli.impl.domain;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cli
{
	private Map<String, Command> commands;
	private Method exceptionHandler;

	public Cli()
	{
		commands = new HashMap<>();
	}

	public void addCommand(Command command)
	{
		commands.put(command.getName(), command);
	}

	public void addCommands(Collection<Command> commands)
	{
		commands.forEach(this::addCommand);
	}

	public Command getCommandByName(String name)
	{
		return commands.get(name);
	}

	public Map<String, Command> getCommandsByName()
	{
		return commands;
	}

	public Method getExceptionHandler()
	{
		return exceptionHandler;
	}

	public void setExceptionHandler(Method exceptionHandler)
	{
		this.exceptionHandler = exceptionHandler;
	}
}
