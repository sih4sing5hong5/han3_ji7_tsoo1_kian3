package cc.layouttools;

/**
 * 共注音分類，決定啥物注音會當用，愛下佇佗。
 * 
 * @author Ihc
 */
class ZhuyinClassifier
{
	/** 頂面的聲調符號 */
	private int[] 輕聲;
	/** 主要聲韻符號 */
	private int[] 聲韻;
	/** 邊仔聲調符號 */
	private int[] 調號;

	/**
	 * 建立一个分類工具。
	 * 
	 * @param 輕聲
	 *            頂面的聲調符號
	 * @param 聲韻
	 *            主要聲韻符號
	 * @param 調號
	 *            邊仔聲調符號
	 */
	ZhuyinClassifier(int[] 輕聲, int[] 聲韻, int[] 調號)
	{
		this.輕聲 = 輕聲;
		this.聲韻 = 聲韻;
		this.調號 = 調號;
	}

	/**
	 * 傳入一个符號，判斷是毋是算輕聲。
	 * 
	 * @param 控制碼
	 *            愛判斷的注音符號
	 * @return 是毋是輕聲符號
	 */
	boolean 是毋是輕聲(int 控制碼)
	{
		for (int 符號控制碼 : 輕聲)
			if (符號控制碼 == 控制碼)
				return true;
		return false;
	}

	/**
	 * 傳入一个符號，判斷是毋是算聲韻。
	 * 
	 * @param 控制碼
	 *            愛判斷的注音符號
	 * @return 是毋是聲韻符號
	 */
	boolean 是毋是聲韻(int 控制碼)
	{
		for (int 符號控制碼 : 聲韻)
			if (符號控制碼 == 控制碼)
				return true;
		return false;
	}

	/**
	 * 傳入一个符號，判斷是毋是算調號。
	 * 
	 * @param 控制碼
	 *            愛判斷的注音符號
	 * @return 是毋是調號符號
	 */
	boolean 是毋是調號(int 控制碼)
	{
		for (int 符號控制碼 : 調號)
			if (符號控制碼 == 控制碼)
				return true;
		return false;
	}
}
