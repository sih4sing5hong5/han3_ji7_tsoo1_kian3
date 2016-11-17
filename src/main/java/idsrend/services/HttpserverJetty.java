package idsrend.services;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * 利用Jetty來做主機端，提供服務予儂組字。
 *
 * @author Ihc
 */
public class HttpserverJetty
{
	/** 網路監聽埠口 */
	static final int 連接埠 = 8060;

	/**
	 * 主要執行的函式。
	 *
	 * @param 參數
	 *            無使用著。
	 */
	public static void main(String[] 參數)
	{
		Server server = new Server(8060);

		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);
		handler.addServletWithMapping(IDSrendServlet.class, "/*");
		boolean 遏袂啟動 = true;
		while (遏袂啟動)
		{
			try
			{
				server.start();
				遏袂啟動 = false;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				try
				{
					Thread.sleep(3000);
				}
				catch (InterruptedException e1)
				{
				}
			}
		}
		System.out.println("服務啟動～～");
		try
		{
			server.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
