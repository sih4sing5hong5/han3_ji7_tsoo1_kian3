package cc.tool.database;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

/**
 * 用Stax來處理微軟Access產生的xml檔，使之可以轉成資料庫的xml格式。其循序存取符合實際需要。
 * 
 * @author Ihc
 */
public class StaxMsToPg
{

	/**
	 * @param args
	 *            程式參數
	 */
	public static void main(String[] args)
	{
		try
		{
			File inputFile = new File("/home/Ihc/tmpfs/檢字表.xml");
			PrintStream printStream = new PrintStream(new File(
					inputFile.getParent() + "/output.xml"));
			// printStream=printStream;
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(inputFile)));
			printStream.println(bufferedReader.readLine());

			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					new FileInputStream(inputFile));
			InputStream in = bufferedInputStream;
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader parser = factory.createXMLStreamReader(in);
			int level = 0;
			boolean running = true;
			while (running)
			{
				int event = parser.next();
				switch (event)
				{
				case XMLStreamConstants.END_DOCUMENT:
					parser.close();
					running = false;
					break;
				case XMLStreamConstants.START_ELEMENT:
					level++;
					printStream.print('<');
					if (level == 3)
					{
						printStream.print("column name=\""
								+ parser.getLocalName() + "\"");
					}
					else
					{
						printStream.print(getTag(parser.getLocalName(), level));
					}
					printStream.print('>');
					if (level == 1)
						printStream.print("\n<records>");
					break;
				case XMLStreamConstants.END_ELEMENT:
					if (level == 1)
						printStream.println("</records>");
					printStream.print("</");
					printStream.print(getTag(parser.getLocalName(), level));
					printStream.print('>');
					level--;
					break;
				case XMLStreamConstants.CHARACTERS:
					// case XMLStreamConstants.CDATA:
					printStream.print(parser.getText());
					break;
				default:
					break;
				}
			}
			printStream.close();
			bufferedReader.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @param origin
	 *            原本標籤
	 * @param level
	 *            標籤在xml第幾層
	 * @return 新標籤名
	 */
	static private String getTag(String origin, int level)
	{
		if (level == 2)
			return "row";
		if (level == 3)
			return "column";
		if (origin.equals("dataroot"))
			return "data";
		return origin;
	}
}
