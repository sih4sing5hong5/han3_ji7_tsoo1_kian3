package cc.連線服務;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.example.awt.AwtTestExample;

public class HelloServlet extends HttpServlet
{
	BufferedImage image;
	BufferedImage pngiimage;
	BufferedImage off_Image;
	組字介面 aa ;

	public HelloServlet() throws IOException
	{

		File jpg = new File("/home/Ihc/tmpfs/P1160656.JPG");
		File png = new File("/home/Ihc/tmpfs/P1160656-2.png");
		image = ImageIO.read(jpg);
		pngiimage = ImageIO.read(png);
		 aa = new 組字介面(Font.BOLD,200);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// ImageIO.write(image, "jpg", baos);
		// baos.flush();
		// byte[] imageInBytes = baos.toByteArray();
		// baos.close();
		if (request.getParameter("a").equals("我愛文莉"))
			// response.getOutputStream().write(imageInBytes);
			ImageIO.write(image, "jpg", response.getOutputStream());
		else if (request.getParameter("a").equals("我愛文莉2"))
			// response.getOutputStream().write(imageInBytes);
			ImageIO.write(pngiimage, "png", response.getOutputStream());
		else if (request.getParameter("a")!=null)
		{
			off_Image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
			aa.paint(off_Image.getGraphics(),request.getParameter("a"));
			ImageIO.write(off_Image, "png", response.getOutputStream());
		}
		// response.getWriter().wr
		//
		// response.setContentType("text/html");
		// response.setStatus(HttpServletResponse.SC_OK);
		// // response.getOutputStream().write("<h1>Hello 我愛文莉</h1>");
		// //
		// response.getOutputStream().write("<h1>Hello 我愛文莉</h1>".getBytes("utf-8"));
		// response.getWriter().println("<h1>Hello 我愛文莉</h1>");
		// response.getWriter().println(request.getParameterMap());
		// response.getWriter().println(request.getParameter("a"));
		// System.out.println("SSSSSSSSSSss");
		//
		// (System.out).write(request.getParameter("a").getBytes());
		// System.out.println("aaaaaa");
		// //
		// response.getOutputStream().write(request.getParameter("a").getBytes());
	}
}