package cc.tool.database;

/**
 * 將整個字串裡的字元型態與控制碼轉換的型態。
 * 
 * @author Ihc
 */
public class String2ControlCode
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
		int 控制碼長度 = 字串.codePointCount(0, 字串.length());
		int[] 控制碼 = new int[控制碼長度];
		
		int 註標 = 0, 字串註標 = 0;
		for (字串註標 = 0, 註標 = 0; 字串註標 < 字串.length(); 字串註標 += Character.charCount(字串.codePointAt(字串註標)), 註標++)
		{
			控制碼[註標] = 字串.codePointAt(字串註標);
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
		// String(int[] codePoints, int offset, int count) (Java 1.5+)
		return new String(控制碼, 0, 控制碼.length);
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
