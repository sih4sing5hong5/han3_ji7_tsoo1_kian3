package cc.tool.database;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 用Dom來處理微軟Access產生的xml檔，使之可以轉成資料庫的xml格式。但其實作方法適合隨機存取不適合大型檔案。
 * 
 * @author Ihc
 */
public class DomCdpToPg
{

	/**
	 * @param args
	 *            程式參數
	 */
	public static void main(String[] args)
	{
		try
		{
			File file = new File("/home/Ihc/tmpfs/aa.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element "
					+ doc.getDocumentElement().getNodeName());
			NodeList nodeLst = doc.getElementsByTagName("檢字表");
			System.out.println("Information of all employees");

			for (int s = 0; s < nodeLst.getLength(); s++)
			{
				Node fstNode = nodeLst.item(s);
				if (fstNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element fstElmnt = (Element) fstNode;
					System.out.println(fstElmnt.getTextContent());
					System.out.println(fstElmnt.getTagName());
					System.out.println(fstElmnt.getElementsByTagName("*")
							.getLength());
					NodeList attr = fstElmnt.getElementsByTagName("*");
					for (int i = 0; i < attr.getLength(); ++i)
					{
						System.out.println(fstElmnt.getElementsByTagName("*")
								.item(i).getNodeName());
						System.out.println(fstElmnt.getElementsByTagName("*")
								.item(i).getTextContent());
					}
					System.out.println("----------");
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
