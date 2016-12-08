package test;

import org.junit.Test;
import net.nlacombe.jcli.impl.Jcli;

public class JCliTest
{
	@Test
	public void test()
	{
		String[] arguments = "add 1 2".split(" ");

		Jcli cli = new Jcli();
		cli.run("test.cli", arguments);
	}
}
