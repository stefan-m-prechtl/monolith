package de.esempe.rext.usermgmt.boundary.exceptionhandling;

import java.lang.reflect.Parameter;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

import com.google.common.base.Preconditions;

/**
 * Interceptor für das Exception-Handling aller Methoden von REST-Ressourcen.
 *
 * @author Stefan M. Prechtl (www.esempe.de)
 *
 */
public class ExceptionHandlingInterceptor
{
	@Inject
	LoggerExposer logger;

	@AroundInvoke
	public Object handleException(final InvocationContext context)
	{
		final Object proceedResponse;
		try
		{
			final Parameter[] parameters = context.getMethod().getParameters();
			for (final Parameter parameter : parameters)
			{
				Preconditions.checkNotNull(parameter);
			}
			proceedResponse = context.proceed();
		}
		catch (final Exception ex)
		{
			this.logger.fatalErrorConsumer().accept(ex);
			return Response.serverError().entity("Fehler:" + ex.getMessage()).build();
		}
		return proceedResponse;
	}

}
