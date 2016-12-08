package test.cli;

import net.nlacombe.jcli.api.CliMapping;
import net.nlacombe.jcli.api.ParameterMapping;
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
	public void add(@ParameterMapping("number1") String number1, @ParameterMapping("number2") String number2)
	{
		logger.info("add");
	}
}
