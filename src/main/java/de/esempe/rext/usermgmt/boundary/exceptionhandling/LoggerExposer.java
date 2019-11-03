package de.esempe.rext.usermgmt.boundary.exceptionhandling;

import java.util.function.Consumer;

import javax.enterprise.inject.Produces;

public class LoggerExposer
{

	@Produces
	public Consumer<Throwable> fatalErrorConsumer()
	{
		// return Throwable::printStackTrace;
		return LoggerExposer::printThrowable;
	}

	public static void printThrowable(Throwable t)
	{
		System.err.println("Fehler wegen: " + t.getMessage());
	}
}
