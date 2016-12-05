package net.nlacombe.jcli.impl.exception;

import net.nlacombe.jcli.impl.domain.CommandDefinition;

public class CliUsageException extends RuntimeException
{
	private CommandDefinition commandDefinition;
	private String shortMessage;

	public CliUsageException(String shortMessage)
	{
		this.shortMessage = shortMessage;
	}

	public CliUsageException(CommandDefinition commandDefinition, String shortMessage)
	{
		this.commandDefinition = commandDefinition;
		this.shortMessage = shortMessage;
	}

	@Override
	public String getMessage()
	{
		String message = "Error: ";

		if (commandDefinition != null)
			message += "error using command \"" + commandDefinition.getName() + "\" ";

		message += shortMessage;

		return message;
	}

	public CommandDefinition getCommandDefinition()
	{
		return commandDefinition;
	}
}
