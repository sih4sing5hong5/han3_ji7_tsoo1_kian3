package cc.char_indexingtool;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 因為吳守禮的字體無照統一碼，所以專門寫一个查編碼對照表的字體。
 *
 * @author Ihc
 */
public class FontCorrespondTable extends MergedFont
{
	/** 統一碼佮逐个字體內部編碼對照表。若無需要，彼格傳<code>null</code> */
	protected ArrayList<HashMap<Integer, Integer>> 對照表集;

	/** 楷體字體下的所在 */
	static public final String[] 楷體字體位址表 = new String[] {
			"/font/WSL_TPS_Font_101/WSL_TPS.ttf",
			"/font/CNS11643/TW-Kai-98_1.ttf",
			"/font/CNS11643/TW-Kai-98_1.ttf",
			"/font/CNS11643/TW-Kai-Ext-B-98_1.ttf",
			"/font/CNS11643/TW-Kai-Plus-98_1.ttf",
			"/font/cdphanzi-2_7/cdpeudck.tte", 
			"/font/UnFont/UnGungseo.ttf", };
	/** 宋體字體下的所在 */
	static public final String[] 宋體字體位址表 = new String[] {
			"/font/WSL_TPS_Font_101/WSL_TPS.ttf",
			"/font/CNS11643/TW-Sung-98_1.ttf",
			"/font/CNS11643/TW-Sung-98_1.ttf",
			"/font/CNS11643/TW-Sung-Ext-B-98_1.ttf",
			"/font/CNS11643/TW-Sung-Plus-98_1.ttf",
			"/font/cdphanzi-2_7/cdpeudc.tte", 
			"/font/NanumFont/NanumMyeongjo.ttf", };
	/** 吳守禮注音字體，統一碼佮伊字體內部編碼的對照表 */
	static protected final HashMap<Integer, Integer> 吳守禮注音字體對照表;
	/** 因為入聲的符號傷細，所以用原本的注音符號，產生入聲編碼佮原本編碼的對照表。 親像「ㆴ」→「ㄅ」、「ㆵ」→「ㄉ」、「ㆶ」→「ㄍ」、「ㆷ」→「ㄏ」。 */
	static protected final HashMap<Integer, Integer> 入聲注音字體對照表;
	/** 楷體字體的物件 */
	static private final FontCorrespondTable 吳守禮注音摻楷體字體;
	/** 宋體字體的物件 */
	static private final FontCorrespondTable 吳守禮注音摻宋體字體;
	static
	{
		吳守禮注音字體對照表 = 產生吳守禮注音字體對照表();
		入聲注音字體對照表 = 產生入聲注音字體對照表();
		// HashMap<Integer, Integer> 空對照表 = new HashMap<Integer, Integer>();
		ArrayList<HashMap<Integer, Integer>> 注音字體對照表 = new ArrayList<HashMap<Integer, Integer>>();
		注音字體對照表.add(吳守禮注音字體對照表);
		注音字體對照表.add(入聲注音字體對照表);
		注音字體對照表.add(null);
		注音字體對照表.add(null);
		注音字體對照表.add(null);
		注音字體對照表.add(null);
		注音字體對照表.add(null);
		注音字體對照表.add(null);

		吳守禮注音摻楷體字體 = new FontCorrespondTable(楷體字體位址表, 注音字體對照表);
		吳守禮注音摻宋體字體 = new FontCorrespondTable(宋體字體位址表, 注音字體對照表);
	}

	/** 建立一个空的字體，予調參數的時陣會當用。 */
	protected FontCorrespondTable()
	{
		super();
		對照表集 = null;
	}

	/**
	 * 若有合併字體，加上對照表就會使變對照字體。
	 *
	 * @param 字體
	 *            愛擴充的合併字體
	 * @param 對照表集
	 *            統一碼佮逐个字體內部編碼對照表。若無需要，彼格傳<code>null</code>
	 */
	protected FontCorrespondTable(MergedFont 字體, ArrayList<HashMap<Integer, Integer>> 對照表集)
	{
		字體集 = 字體.字體集;
		this.對照表集 = 對照表集;
	}

	/**
	 * 予外口物件建立字體。
	 *
	 * @param 字體位置
	 *            字體檔案的所在
	 * @param 對照表集
	 *            統一碼佮逐个字體內部編碼對照表。若無需要，彼格傳<code>null</code>
	 */
	public FontCorrespondTable(String[] 字體位置, ArrayList<HashMap<Integer, Integer>> 對照表集)
	{
		super(字體位置);
		this.對照表集 = 對照表集;
	}

	@Override
	public FontCorrespondTable 調整字體大小(float 字體大小)
	{
		return new FontCorrespondTable(super.調整字體大小(字體大小), 對照表集);
	}

	@Override
	public FontCorrespondTable 調整字體選項(int 字體選項)
	{
		return new FontCorrespondTable(super.調整字體選項(字體選項), 對照表集);
	}

	@Override
	public FontCorrespondTable 調整字體參數(int 字體選項, float 字型大小)
	{
		return new FontCorrespondTable(super.調整字體參數(字體選項, 字型大小), 對照表集);
	}

	@Override
	public boolean 有這个字型無(int 控制碼, int 字體編號)
	{
		if (字體編號 == 0)
		{
			for (int i = 0; i < 字體集.length; ++i)
			{
				if (對照表集.get(i) != null)
				{
					if (對照表集.get(i).containsKey(控制碼))
					{
						if (字體集[i].canDisplay(對照表集.get(i).get(控制碼)))
						{
							return true;
						}
					}
				}
				else
				{
					if (字體集[i].canDisplay(控制碼))
					{
						return true;
					}

				}
			}
		}
		return false;
	}

	@Override
	public GlyphVector 提這个字型(FontRenderContext 渲染選項, int 控制碼, int 字體編號)
	{
		if (字體編號 == 0)
		{
			for (int i = 0; i < 字體集.length; ++i)
			{
				if (對照表集.get(i) != null)
				{
					if (對照表集.get(i).containsKey(控制碼))
					{
						if (字體集[i].canDisplay(對照表集.get(i).get(控制碼)))
						{
							return 字體集[i].createGlyphVector(渲染選項,
									Character.toChars(對照表集.get(i).get(控制碼)));
						}
					}
				}
				else
				{
					if (字體集[i].canDisplay(控制碼))
					{
						return 字體集[i].createGlyphVector(渲染選項,
								Character.toChars(控制碼));
					}

				}
			}
		}
		return null;
	}

	/**
	 * 產生吳守禮注音字體，統一碼佮伊字體內部編碼的對照表。
	 *
	 * @return 統一碼佮伊字體內部編碼的對照表
	 */
	static private HashMap<Integer, Integer> 產生吳守禮注音字體對照表()
	{
		HashMap<Integer, Integer> 吳守禮注音字體對照表 = new HashMap<Integer, Integer>();
		// 吳守禮注音字體對照表.put(0x3105, 0xb000);
		// 吳守禮注音字體對照表.put(0x3106, 0xb002);
		// 吳守禮注音字體對照表.put(0x3107, 0xb003);
		// 吳守禮注音字體對照表.put(0x3108, 0xb004);
		// 吳守禮注音字體對照表.put(0x3109, 0xb006);
		// 吳守禮注音字體對照表.put(0x310a, 0xb008);
		// 吳守禮注音字體對照表.put(0x310b, 0xb009);
		// 吳守禮注音字體對照表.put(0x310c, 0xb00a);
		// 吳守禮注音字體對照表.put(0x310d, 0xb00b);
		// 吳守禮注音字體對照表.put(0x310e, 0xb00d);
		// 吳守禮注音字體對照表.put(0x310f, 0xb00f);
		// 吳守禮注音字體對照表.put(0x3110, 0xb010);
		// 吳守禮注音字體對照表.put(0x3111, 0xb012);
		// 吳守禮注音字體對照表.put(0x3112, 0xb014);
		// 吳守禮注音字體對照表.put(0x3113, 0xb015);
		// 吳守禮注音字體對照表.put(0x3114, 0xb016);
		// 吳守禮注音字體對照表.put(0x3115, 0xb017);
		// 吳守禮注音字體對照表.put(0x3116, 0xb018);
		// 吳守禮注音字體對照表.put(0x3117, 0xb019);
		// 吳守禮注音字體對照表.put(0x3118, 0xb01b);
		// 吳守禮注音字體對照表.put(0x3119, 0xb01c);
		// 吳守禮注音字體對照表.put(0x311a, 0xb01d);
		// 吳守禮注音字體對照表.put(0x311b, 0xb021);
		// 吳守禮注音字體對照表.put(0x311c, 0xb023);
		// 吳守禮注音字體對照表.put(0x311d, 0xb024);
		// 吳守禮注音字體對照表.put(0x311e, 0xb027);
		// 吳守禮注音字體對照表.put(0x311f, 0xb029);
		// 吳守禮注音字體對照表.put(0x3120, 0xb02a);
		// 吳守禮注音字體對照表.put(0x3121, 0xb02c);
		// 吳守禮注音字體對照表.put(0x3122, 0xb031);
		// 吳守禮注音字體對照表.put(0x3123, 0xb032);
		// 吳守禮注音字體對照表.put(0x3124, 0xb033);
		// 吳守禮注音字體對照表.put(0x3125, 0xb035);
		// 吳守禮注音字體對照表.put(0x3126, 0xb037);
		// 吳守禮注音字體對照表.put(0x3127, 0xb038);
		// 吳守禮注音字體對照表.put(0x3128, 0xb03a);
		// 吳守禮注音字體對照表.put(0x3129, 0xb03d);
		吳守禮注音字體對照表.put(0x312a, 0xb005);
		吳守禮注音字體對照表.put(0x312b, 0xb00e);
		吳守禮注音字體對照表.put(0x312c, 0xb013);
		吳守禮注音字體對照表.put(0x312d, 0xb03e);
		吳守禮注音字體對照表.put(0x31a0, 0xb001);
		吳守禮注音字體對照表.put(0x31a1, 0xb01a);
		吳守禮注音字體對照表.put(0x31a2, 0xb011);
		吳守禮注音字體對照表.put(0x31a3, 0xb00c);
		吳守禮注音字體對照表.put(0x31a4, 0xb025);
		吳守禮注音字體對照表.put(0x31a5, 0xb026);
		吳守禮注音字體對照表.put(0x31a6, 0xb01f);
		吳守禮注音字體對照表.put(0x31a7, 0xb020);
		吳守禮注音字體對照表.put(0x31a8, 0xb03b);
		吳守禮注音字體對照表.put(0x31a9, 0xb01e);
		吳守禮注音字體對照表.put(0x31aa, 0xb039);
		吳守禮注音字體對照表.put(0x31ab, 0xb03c);
		吳守禮注音字體對照表.put(0x31ac, 0xb02f);
		吳守禮注音字體對照表.put(0x31ad, 0xb036);
		吳守禮注音字體對照表.put(0x31ae, 0xb028);
		吳守禮注音字體對照表.put(0x31af, 0xb02b);
		吳守禮注音字體對照表.put(0x31b0, 0xb02d);
		吳守禮注音字體對照表.put(0x31b1, 0xb02e);
		吳守禮注音字體對照表.put(0x31b2, 0xb034);
		吳守禮注音字體對照表.put(0x31b3, 0xb386);
//		吳守禮注音字體對照表.put(0x31b4, 0xb03f);
//		吳守禮注音字體對照表.put(0x31b5, 0xb041);
//		吳守禮注音字體對照表.put(0x31b6, 0xb043);
//		吳守禮注音字體對照表.put(0x31b7, 0xb045);
		吳守禮注音字體對照表.put(0x02ea, 0xb383);
		吳守禮注音字體對照表.put(0x02eb, 0xb382);
		吳守禮注音字體對照表.put(0x3190, 0xb384);
		return 吳守禮注音字體對照表;
	}

	/**
	 * 因為入聲的符號傷細，所以用原本的注音符號，產生入聲編碼佮原本編碼的對照表。 親像「ㆴ」→「ㄅ」、「ㆵ」→「ㄉ」、「ㆶ」→「ㄍ」、「ㆷ」→「ㄏ」。
	 *
	 * @return 入聲編碼佮原本編碼的對照表
	 */
	static private HashMap<Integer, Integer> 產生入聲注音字體對照表()
	{
		HashMap<Integer, Integer> 吳守禮注音字體對照表 = new HashMap<Integer, Integer>();
		/** ㆴ */
		吳守禮注音字體對照表.put(0x31b4, 0x3105);
		/** ㆵ */
		吳守禮注音字體對照表.put(0x31b5, 0x3109);
		/** ㆶ */
		吳守禮注音字體對照表.put(0x31b6, 0x310d);
		/** ㆷ */
		吳守禮注音字體對照表.put(0x31b7, 0x310f);
		return 吳守禮注音字體對照表;
	}

	/**
	 * 提著吳守禮注音佮全字庫、構形資料庫公家機關提來的楷體字體。
	 *
	 * @return　吳守禮注音佮全字庫、構形資料庫公家機關提來的楷體字體
	 */
	static public FontCorrespondTable 提著吳守禮注音摻楷體字體()
	{
		return 吳守禮注音摻楷體字體;
	}

	/**
	 * 提著吳守禮注音佮全字庫、構形資料庫公家機關提來的宋體字體。
	 *
	 * @return　吳守禮注音佮全字庫、構形資料庫公家機關提來的楷體字體
	 */
	static public FontCorrespondTable 提著吳守禮注音摻宋體字體()
	{
		return 吳守禮注音摻宋體字體;
	}
}
