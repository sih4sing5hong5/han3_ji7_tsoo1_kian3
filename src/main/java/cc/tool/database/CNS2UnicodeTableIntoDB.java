package cc.tool.database;

/**
 * 將統一碼（Unicode）輸入至資料庫的型態。
 * 
 * @author Ihc
 */
public class CNS2UnicodeTableIntoDB
{
	/** CNS2UNICODE_Unicode BMP字面.txt為CNS對Unicode 第0(BMP)字面的對照表 */
	static String 基本平面 = "CNS2UNICODE_Unicode BMP字面.txt";
	/** CNS2UNICODE_Unicode第2字面.txt為CNS對Unicode 第2字面的對照表 */
	static String 第二平面 = "CNS2UNICODE_Unicode第2字面.txt";
	/** CNS2UNICODE_Unicode第15字面.txt為CNS對Unicode 第15字面的對照表 */
	static String 十五平面 = "CNS2UNICODE_Unicode第15字面.txt";

	/** 檔案目錄 */
	static private String 目錄 = "/home/Ihc/意泉計劃/機構資料/全字庫/授權薛丞宏先生/中文碼對照表/Uniocde/";
	/** 所有檔案名 */
	static private String[] 全部檔案 = new String[] { 基本平面, 第二平面, 十五平面 };
	/** 資料庫中的表名 */
	static private String 資料表 = "CNS2UNICODE";
	/** 全字庫所對應到的編碼欄位名 */
	static private String 欄位名 = "Unicode";

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
