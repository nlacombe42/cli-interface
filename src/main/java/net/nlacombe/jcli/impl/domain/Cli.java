package net.nlacombe.jcli.impl.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cli
{
	private Map<String, Command> commands;

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
}
