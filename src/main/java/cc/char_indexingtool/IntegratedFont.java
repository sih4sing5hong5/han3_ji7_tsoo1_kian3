package cc.char_indexingtool;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

/**
 * 佮幾仔个合併字體合做一个，閣用字體編號排，方便存取逐个字體，閣有法度判斷是毋是有這控制碼的字型。
 * 
 * @author Ihc
 */
public class IntegratedFont extends CommonFont
{
	/** 　所整合的字體物件 */
	MergedFont[] 合併字體集;
	/** 楷體字體下的所在 */
	static public final String[][] 楷體字體位址表 = new String[][] { MergedFont.楷體字體位址表,
			new String[] { "/font/cdphanzi-2_7/hzcdp01k.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp02k.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp03k.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp04k.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp05k.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp06k.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp07k.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp08k.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp09k.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp10k.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp11k.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp12k.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp13k.ttf" }, };
	/** 宋體字體下的所在 */
	static public final String[][] 宋體字體位址表 = new String[][] { MergedFont.宋體字體位址表,
			new String[] { "/font/cdphanzi-2_7/hzcdp01m.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp02m.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp03m.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp04m.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp05m.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp06m.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp07m.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp08m.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp09m.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp10m.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp11m.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp12m.ttf" },
			new String[] { "/font/cdphanzi-2_7/hzcdp13m.ttf" }, };
	/** 楷體字體的物件 */
	static private final IntegratedFont 楷體字體;
	/** 宋體字體的物件 */
	static private final IntegratedFont 宋體字體;
	static
	{
		楷體字體 = new IntegratedFont(楷體字體位址表);
		宋體字體 = new IntegratedFont(宋體字體位址表);
	}

	/** 予改變參數時的私有建構工具。 */
	private IntegratedFont()
	{
	}

	/**
	 * 整合字體的準備工具。
	 * 
	 * @param 字體位置
	 *            字體放的所在
	 */
	public IntegratedFont(String[][] 字體位置)
	{
		合併字體集 = new MergedFont[字體位置.length];
		for (int i = 0; i < 合併字體集.length; ++i)
		{
			合併字體集[i] = new MergedFont(字體位置[i]);
		}
	}

	@Override
	public CommonFont 調整字體大小(float 字體大小)
	{
		IntegratedFont 調整結果 = new IntegratedFont();
		調整結果.合併字體集 = new MergedFont[this.合併字體集.length];
		for (int i = 0; i < 合併字體集.length; ++i)
		{
			調整結果.合併字體集[i] = (MergedFont) this.合併字體集[i].調整字體大小(字體大小);
		}
		return 調整結果;
	}

	@Override
	public CommonFont 調整字體選項(int 字體選項)
	{
		IntegratedFont 調整結果 = new IntegratedFont();
		調整結果.合併字體集 = new MergedFont[this.合併字體集.length];
		for (int i = 0; i < 合併字體集.length; ++i)
		{
			調整結果.合併字體集[i] = (MergedFont) this.合併字體集[i].調整字體選項(字體選項);
		}
		return 調整結果;
	}

	@Override
	public CommonFont 調整字體參數(int 字體選項, float 字型大小)
	{
		IntegratedFont 調整結果 = new IntegratedFont();
		調整結果.合併字體集 = new MergedFont[this.合併字體集.length];
		for (int i = 0; i < 合併字體集.length; ++i)
		{
			調整結果.合併字體集[i] = (MergedFont) this.合併字體集[i].調整字體參數(字體選項, 字型大小);
		}
		return 調整結果;
	}

	@Override
	public boolean 有這个字型無(int 控制碼, int 字體編號)
	{
		if (字體編號 < 0 || 字體編號 >= 合併字體集.length)
			return false;
		return 合併字體集[字體編號].有這个字型無(控制碼);
	}

	@Override
	public GlyphVector 提這个字型(FontRenderContext 渲染選項, int 控制碼, int 字體編號)
	{
		if (字體編號 < 0 || 字體編號 >= 合併字體集.length)
			return null;
		return 合併字體集[字體編號].提這个字型(渲染選項, 控制碼);
	}

	/**
	 * 提著系統佮全字庫、構形資料庫公家機關提來的楷體字體。
	 * 
	 * @return　對公家機關提來的楷體字體
	 */
	static public IntegratedFont 提著楷體字體()
	{
		return 楷體字體;
	}

	/**
	 * 提著系統佮全字庫、構形資料庫公家機關提來的宋體字體。
	 * 
	 * @return　對公家機關提來的宋體字體
	 */
	static public IntegratedFont 提著宋體字體()
	{
		return 宋體字體;
	}
}
