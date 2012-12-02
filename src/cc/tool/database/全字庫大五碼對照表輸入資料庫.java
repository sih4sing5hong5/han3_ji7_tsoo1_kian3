package cc.tool.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class 全字庫大五碼對照表輸入資料庫
{
	static String 基本平面 = "CNS2UNICODE_Unicode BMP字面.txt";
	static String 第二平面 = "CNS2UNICODE_Unicode第2字面.txt";
	static String 十五平面 = "CNS2UNICODE_Unicode第15字面.txt";

	static private String 目錄 = "/home/Ihc/意泉計劃/機構資料/全字庫/授權薛丞宏先生/中文碼對照表/Uniocde/";
	static private String[] 全部檔案 = new String[] { 基本平面, 第二平面, 十五平面 };
	static private String 資料表 = "CNS2UNICODE";
	static private String 欄位名 = "Unicode";

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		全字庫對照表輸入資料庫 輸入工具 = new 全字庫對照表輸入資料庫(目錄, 全部檔案, 資料表, 欄位名);
		輸入工具.更新();
	}
}
