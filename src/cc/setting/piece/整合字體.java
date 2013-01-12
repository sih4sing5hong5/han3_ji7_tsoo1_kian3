package cc.setting.piece;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

/**
 * 佮幾仔个合併字體合做一个，閣用字體編號排，方便存取逐个字體，閣有法度判斷是毋是有這控制碼的字型。
 * 
 * @author Ihc
 */
public class 整合字體 extends 字體介面
{
	/** 　所整合的字體物件 */
	合併字體[] 合併字體集;
	/** 楷體字體下的所在 */
	static public final String[][] 楷體字體位址表 = new String[][] { 合併字體.楷體字體位址表,
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp01k.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp02k.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp03k.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp04k.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp05k.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp06k.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp07k.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp08k.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp09k.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp10k.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp11k.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp12k.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp13k.ttf" }, };
	/** 宋體字體下的所在 */
	static public final String[][] 宋體字體位址表 = new String[][] { 合併字體.宋體字體位址表,
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp01m.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp02m.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp03m.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp04m.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp05m.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp06m.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp07m.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp08m.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp09m.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp10m.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp11m.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp12m.ttf" },
			new String[] { "./字體/漢字構形資料庫-2_7/hzcdp13m.ttf" }, };
	/** 楷體字體的物件 */
	static private final 整合字體 楷體字體;
	/** 宋體字體的物件 */
	static private final 整合字體 宋體字體;
	static
	{
		楷體字體 = new 整合字體(楷體字體位址表);
		宋體字體 = new 整合字體(宋體字體位址表);
	}

	/** 予改變參數時的私有建構工具。 */
	private 整合字體()
	{
	}

	/**
	 * 整合字體的準備工具。
	 * 
	 * @param 字體位置
	 *            字體放的所在
	 */
	public 整合字體(String[][] 字體位置)
	{
		合併字體集 = new 合併字體[字體位置.length];
		for (int i = 0; i < 合併字體集.length; ++i)
		{
			合併字體集[i] = new 合併字體(字體位置[i]);
		}
	}

	@Override
	public 字體介面 調整字體大小(float 字體大小)
	{
		整合字體 調整結果 = new 整合字體();
		調整結果.合併字體集 = new 合併字體[this.合併字體集.length];
		for (int i = 0; i < 合併字體集.length; ++i)
		{
			調整結果.合併字體集[i] = (合併字體) this.合併字體集[i].調整字體大小(字體大小);
		}
		return 調整結果;
	}

	@Override
	public 字體介面 調整字體選項(int 字體選項)
	{
		整合字體 調整結果 = new 整合字體();
		調整結果.合併字體集 = new 合併字體[this.合併字體集.length];
		for (int i = 0; i < 合併字體集.length; ++i)
		{
			調整結果.合併字體集[i] = (合併字體) this.合併字體集[i].調整字體選項(字體選項);
		}
		return 調整結果;
	}

	@Override
	public 字體介面 調整字體參數(int 字體選項, float 字型大小)
	{
		整合字體 調整結果 = new 整合字體();
		調整結果.合併字體集 = new 合併字體[this.合併字體集.length];
		for (int i = 0; i < 合併字體集.length; ++i)
		{
			調整結果.合併字體集[i] = (合併字體) this.合併字體集[i].調整字體參數(字體選項, 字型大小);
		}
		return 調整結果;
	}

	@Override
	public boolean 是否可顯示該字型(int 控制碼, int 字體編號)
	{
		if (字體編號 < 0 || 字體編號 >= 合併字體集.length)
			return false;
		return 合併字體集[字體編號].是否可顯示該字型(控制碼);
	}

	@Override
	public GlyphVector 取得該字型(FontRenderContext 渲染選項, int 控制碼, int 字體編號)
	{
		if (字體編號 < 0 || 字體編號 >= 合併字體集.length)
			return null;
		return 合併字體集[字體編號].取得該字型(渲染選項, 控制碼);
	}

	/**
	 * 提著系統佮全字庫、構形資料庫公家機關提來的楷體字體。
	 * 
	 * @return　對公家機關提來的楷體字體
	 */
	static public 整合字體 提著楷體字體()
	{
		return 楷體字體;
	}

	/**
	 * 提著系統佮全字庫、構形資料庫公家機關提來的宋體字體。
	 * 
	 * @return　對公家機關提來的宋體字體
	 */
	static public 整合字體 提著宋體字體()
	{
		return 宋體字體;
	}
}
