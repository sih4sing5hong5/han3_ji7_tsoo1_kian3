package cc.連線服務;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHandler;

/**
 * 利用Jetty來做主機端，提供服務予儂組字。
 * 
 * @author Ihc
 */
public class 姊麶服務程式
{
	/**
	 * 主要執行的函式。
	 * 
	 * @param 參數
	 *            無使用著。
	 */
	public static void main(String[] 參數)
	{
		Server server = new Server(8080);
		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);
		handler.addServletWithMapping(組字服務.class, "/*");
		try
		{
			server.start();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			server.join();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
