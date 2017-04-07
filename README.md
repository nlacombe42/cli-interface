# jcli
Declare a command line interface in Java with annotations.

## Usage

Compile and install in your local maven repo:
`./gradlew install`

### Gradle
```groovy
compile 'net.nlacombe:jcli:0.4.0'
```

## Example

```java
package net.nlacombe.example.cli;

public interface ExampleCli
{
	@CommandMapping
	void add(@ParameterMapping("name") String namespaceName,
			 @ParameterMapping("source") String source,
			 @ParameterMapping("destination") String destinationPath) throws IOException;
}
```

```java
public class Main
{
	public static void main(String[] arguments)
	{
		new Jcli().run("net.nlacombe.example.cli", arguments);
	}
}
```

Command line: `java -jar example.jar add namespace source destination`
