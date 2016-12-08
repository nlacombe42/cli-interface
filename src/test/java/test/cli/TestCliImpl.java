package test.cli;

import net.nlacombe.jcli.api.CliMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CliMapping
public class TestCliImpl implements TestCli
{
	private static final Logger logger = LoggerFactory.getLogger(TestCliImpl.class);

	public void add(String addArg1, String addArg2)
	{
		logger.info("ADD COMMAND: {} {}", addArg1, addArg2);
	}

	public void min(String addArg1, String addArg2)
	{
		logger.info("min: {} {}", addArg1, addArg2);
	}
}
