package test.cli;

import net.nlacombe.jcli.api.CommandMapping;
import net.nlacombe.jcli.api.ParameterMapping;

public interface TestCli
{
	@CommandMapping(name = "aaaa", description = "Initialize app")
	void init(@ParameterMapping(value = "username", description = "username to use in subsequent commands") String username,
			  @ParameterMapping(value = "pass", description = "password") String pass);

	@CommandMapping(description = "Adds 2 numbers")
	void add(@ParameterMapping("number1") String number1,
			  @ParameterMapping("number2") String number2);
}
