package net.nlacombe.jcli.impl.exception;

import net.nlacombe.jcli.impl.domain.Command;

public class CliUsageException extends RuntimeException
{
	private Command command;
	private String shortMessage;

	public CliUsageException(String shortMessage)
	{
		this.shortMessage = shortMessage;
	}

	public CliUsageException(Command command, String shortMessage)
	{
		this.command = command;
		this.shortMessage = shortMessage;
	}

	@Override
	public String getMessage()
	{
		String message = "Error: ";

		if (command != null)
			message += "error using command \"" + command.getName() + "\" ";

		message += shortMessage;

		return message;
	}

	public Command getCommand()
	{
		return command;
	}
}
