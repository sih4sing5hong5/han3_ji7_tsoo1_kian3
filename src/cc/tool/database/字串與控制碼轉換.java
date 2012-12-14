package cc.tool.database;

/**
 * 將整個字串裡的字元型態與控制碼轉換的型態。
 * 
 * @author Ihc
 */
public class 字串與控制碼轉換
{
	/**
	 * 將字串型態轉換成整數控制碼
	 * 
	 * @param 字串
	 *            欲轉換的文字
	 * @return 轉換結果
	 */
	static public int[] 轉換成控制碼(String 字串)
	{
		int 控制碼長度 = 0;
		int i = 0;
		while (i < 字串.length())
		{
			i += Character.charCount(字串.charAt(i));
			控制碼長度++;
		}

		int[] 控制碼 = new int[控制碼長度];
		i = 0;
		int 註標 = 0;
		while (i < 字串.length())
		{
			控制碼[註標] = 字串.codePointAt(i);
			i += Character.charCount(字串.charAt(i));
			註標++;
		}
		return 控制碼;
	}

	/**
	 * 將整數控制碼陣列轉換成字串型態
	 * 
	 * @param 控制碼
	 *            欲轉換的控制碼陣列
	 * @return 轉換結果
	 */
	static public String 轉換成字串(int[] 控制碼)
	{
		StringBuilder 字串結果 = new StringBuilder();
		for (int i = 0; i < 控制碼.length; ++i)
		{
			字串結果.append(Character.toChars(控制碼[i]));
		}
		return 字串結果.toString();
	}

	/**
	 * 將整數控制碼轉換成字串型態
	 * 
	 * @param 控制碼
	 *            欲轉換的控制碼
	 * @return 轉換結果
	 */
	static public String 轉換成字串(int 控制碼)
	{
		return new String(Character.toChars(控制碼));
	}
}
