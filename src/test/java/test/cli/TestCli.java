package test.cli;

import net.nlacombe.jcli.api.Argument;
import net.nlacombe.jcli.api.CommandMapping;

public interface TestCli
{
	@CommandMapping(name = "add", description = "adds stuff")
	void add(@Argument("addArg1") String addArg1, @Argument("bob") String addArg2);

	@CommandMapping(name = "min", description = "substracts stuff")
	void min(@Argument("addArg1") String addArg1, @Argument("addArg2") String addArg2);
}
