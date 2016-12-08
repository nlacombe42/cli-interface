package test;

import org.junit.Test;
import net.nlacombe.jcli.impl.Jcli;

public class JCliTest
{
	@Test
	public void test()
	{
		String[] arguments = "ee D".split(" ");

		Jcli cli = new Jcli();
		cli.run("test.cli", arguments);
	}
}
