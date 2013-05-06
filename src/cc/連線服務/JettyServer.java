package cc.連線服務;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHandler;

public class JettyServer
{
	// {
	// public static void main(String[] args) throws Exception
	// {
	// Server server = new Server(8080);
	// // ContextHandler context = new ContextHandler("/");
	// // context.setContextPath("/");
	// // context.setHandler(new HelloHandler("Root Hello"));
	// // ContextHandler contextFR = new ContextHandler("/fr");
	// // contextFR.setHandler(new HelloHandler("Bonjoir"));
	// // ContextHandler contextIT = new ContextHandler("/it");
	// // contextIT.setHandler(new HelloHandler("Bongiorno"));
	// // ContextHandler contextV = new ContextHandler("/");
	// // contextV.setVirtualHosts(new String[] { "127.0.0.2" });
	// // contextV.setHandler(new HelloHandler("Virtual Hello"));
	// // ContextHandlerCollection contexts = new ContextHandlerCollection();
	// // contexts.setHandlers(new Handler[] { context, contextFR, contextIT,
	// // contextV });
	// server.setHandler(new HelloHandler("Root Hello"));
	// server.start();
	// server.join();
	// }
	public static void main(String[] args) throws Exception
	{
		Server server = new Server(8080);
		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);
		handler.addServletWithMapping(HelloServlet.class, "/*");
		server.start();
		server.join();
	}

	
}
