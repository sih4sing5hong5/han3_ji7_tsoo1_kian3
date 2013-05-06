package cc.連線服務;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.AbstractHandler;

public class HelloHandler extends AbstractHandler
{

	final String _greeting;

	final String _body;

	public HelloHandler()
	{
		_greeting = "Hello World";
		_body = null;
	}

	public HelloHandler(String greeting)
	{
		_greeting = greeting;
		_body = null;
	}

	public HelloHandler(String greeting, String body)
	{
		_greeting = greeting;
		_body = body;
	}

	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		System.out.println("XDD");
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		response.getWriter().println("<h1>" + _greeting + "</h1>");
		if (_body != null)
			response.getWriter().println(_body);
	}

	@Override
	public void handle(String arg0, HttpServletRequest arg1,
			HttpServletResponse arg2, int arg3) throws IOException,
			ServletException
	{
		System.out.println("XDD");
		arg2.setContentType("text/html;charset=utf-8");
		arg2.setStatus(HttpServletResponse.SC_OK);
//		arg1.setHandled(true);
		arg2.getWriter().println("<h1>" + _greeting + "</h1>");
		if (_body != null)
			arg2.getWriter().println(_body);
		
	}
}