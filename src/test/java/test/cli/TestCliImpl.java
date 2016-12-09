package test.cli;

import net.nlacombe.jcli.api.CliMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CliMapping
public class TestCliImpl implements TestCli
{
	private static final Logger logger = LoggerFactory.getLogger(TestCliImpl.class);

	@Override
	public void init(String username, String pass)
	{
		logger.info("init: username: {}, pass: {}", username, pass);
	}

	@Override
	public void add(Integer number1, Double number2)
	{
		logger.info("add: " + (number1 + number2));
	}
}
