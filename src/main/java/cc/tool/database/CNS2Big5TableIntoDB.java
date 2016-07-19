package cc.tool.database;

/**
 * 將大五碼（Big5）輸入至資料庫的型態。
 * 
 * @author Ihc
 */
public class CNS2Big5TableIntoDB
{
	/**CNS2BIG5.txt為CNS對Big5的對照表*/
	static String 大五碼字 = "CNS2BIG5.txt";
	/**CNS2BIG5(符號).txt為CNS對Big5符號及控制字元的對照表*/
	static String 大五碼符號 = "CNS2BIG5_符號.txt";
	/**CNS2BIG5_Big5E.txt為CNS對Big5E的對照表*/
	static String 大五碼擴充 = "CNS2BIG5_Big5E.txt";
	/**CNS2BIG5_七個倚天外字.txt為CNS對七個倚天外字集的對照表*/
	static String 大五碼倚天 = "CNS2BIG5_七個倚天外字.txt";

	/** 檔案目錄 */
	static private String 目錄 = "/home/Ihc/意泉計劃/機構資料/全字庫/授權薛丞宏先生/中文碼對照表/Big5/";
	/** 所有檔案名 */
	static private String[] 全部檔案 = new String[] { 大五碼字, 大五碼符號, 大五碼擴充, 大五碼倚天 };
	/** 資料庫中的表名 */
	static private String 資料表 = "CNS2BIG5";
	/** 全字庫所對應到的編碼欄位名 */
	static private String 欄位名 = "Big5";

	/**
	 * @param args
	 *            程式參數
	 */
	public static void main(String[] args)
	{
		CNSTableIntoDB 輸入工具 = new CNSTableIntoDB(目錄, 全部檔案, 資料表, 欄位名);
		輸入工具.上傳();
	}
}
