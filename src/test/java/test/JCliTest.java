package test;

import net.nlacombe.jcli.impl.Jcli;
import org.junit.Test;

public class JCliTest
{
	@Test
	public void add()
	{
		test("add 1 2.5");
	}

	@Test
	public void exceptionHandler()
	{
		test("boom");
	}

	private void test(String arguments)
	{
		new Jcli().run("test.cli", arguments.split(" "));
	}
}
