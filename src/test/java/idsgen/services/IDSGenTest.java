package idsgen.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import cc.ccomponent_adjuster.ExpSequenceNoLookup;
import cc.ccomponent_adjuster.ExpSequenceLookup;
import cc.char_indexingtool.ChineseCharacterTypeSetter;
import cc.char_indexingtool.FontRefSettingTool;
import cc.char_indexingtool.FontCorrespondTable;
import cc.char_indexingtool.CommonFontNoSearch;
import cc.char_indexingtool.NonLookingupCommonFontNo;
import cc.layouttools.MergePieceAdjuster;
import cc.stroke.FunctinoalBasicBolder;
import cc.stroketool.MkeSeparateMovableType_Bolder;
import idsrend.CharComponentStructureAdjuster.IDSnormalizer;
import idsrend.charcomponent.CharComponent;
import idsrend.parser.IDSParser;
import idsrend.services.IDSrendService;

public class IDSGenTest
{

	/** 組宋體用的工具 */
	protected IDSrendService 宋體組字工具;

	int 字型大細 = 200;

	@Before
	public void setUp()
	{

		int 普通字型屬性 = 0;

		ExpSequenceLookup 查詢方式 = new ExpSequenceNoLookup();
		// TODO ExpSequenceLookup_byDB(連線) ExpSequenceNoLookup()
		IDSnormalizer 正規化工具 = new IDSnormalizer();
		// 異寫式查詢工具 異寫式查詢 = new 資料庫連線異寫式查詢(連線);
		MergePieceAdjuster 調整工具 = new MergePieceAdjuster(
		// new FunctinoalBasicBolder(new Stroke[] {}, 01),
				1e-1, 5);
		CommonFontNoSearch 展開式查通用字型編號工具 = new NonLookingupCommonFontNo();
		// TODO CommonFontNoSearchbyDB(連線) NonLookingupCommonFontNo()
		MkeSeparateMovableType_Bolder 活字加粗 = new MkeSeparateMovableType_Bolder(
				new FunctinoalBasicBolder(new Stroke[] {}, 01), 1e-1);
		ChineseCharacterTypeSetter 宋體設定工具 = new FontRefSettingTool(展開式查通用字型編號工具, FontCorrespondTable
				.提著吳守禮注音摻宋體字體().調整字體參數(普通字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT), 活字加粗);

		宋體組字工具 = new IDSrendService(查詢方式, 正規化工具, 宋體設定工具, 調整工具, 活字加粗, 普通字型屬性, 字型大細);
	}

	/** 測試漢字 */
	static String 全部組字式 = /* "    ⿰禾火秋⿰⿰火牙阝"; */"一百二十三尺意"
			+ "秋漿國變務森"
			+ "⿰禾火⿱將水⿴囗或"
			+ "⿱⿰⿰糹言糹攵⿰矛⿱攵力⿱攵力⿱木⿰木木"
			+ "⿱立⿱日十⿱⿱立日十章輻⿰車⿱一⿱口田⿰⿱十⿱田十⿱一⿱口田"
			+ "⿱口⿰口口⿱夕夕，"
			+ "⿴辶⿴宀⿱珤⿰隹⿰貝招⿴辶咼辶"
			+ "⿴冖儿⿴冖几宀寶建⿴廴聿"
			+ "⿴厂猒⿴广夏⿴疒丙⿴尸古⿴戶方⿴户方⿴户 ⿱户　⿰户　"
			+ "⿴气亥⿴气乃⿴气亞"
			+ "⿴冂⿱一口⿴冂⿱儿口⿴門一⿴門日⿴門月⿴鬥月⿴鬥市⿴鬥一"
			+ "⿱⿴乃又皿盈孕⿴乃子⿱乃子"
			+ "⿴凵乂⿴凵⿰幺⿰丨幺⿴凵⿰豕⿰丨豕"
			+ "⿴勹日⿰⿴弓土畺⿴匚甲⿴⼖⿱口⿰口口⿰懸一⿱闊一⿰懸丨⿱闊丨"

			+ "⿺辶⿵宀⿱珤⿰隹⿰貝招⿺辶咼辶"
			+ "⿵冖儿⿵冖几宀寶建⿺廴聿"
			+ "⿸厂猒⿸广夏⿸疒丙⿸尸古⿸戶方⿸户方⿸户 ⿱户　⿰户　"
			+ "⿹气亥⿹气乃⿹气亞"
			+ "⿵冂⿱一口⿵冂⿱儿口⿵門一⿵門日⿵門月⿵鬥月⿵鬥市⿵鬥一"
			+ "⿱⿵乃又皿盈孕⿵乃子⿱乃子"
			+ "⿶凵乂⿶凵⿰幺⿰丨幺⿶凵⿰豕⿰丨豕"
			+ "⿹勹日⿰⿹弓土畺⿷匚甲⿷⼖⿱口⿰口口"

			+ "⿰彳⿱羊羊⿱羊⿰羊羊⿱⿰羊羊⿰羊羊⿰⿱羊羊⿱羊羊⿰羊⿰羊羊⿰⿰羊羊羊⿰丨丨丨"
			+ "⿿⿿⿿˙ㄆㄨˊ⿿⿿ㄅㄧㄚ⿿⿿⿿ㄅㄧㄚˋ⿿⿿⿿ㄅㄧㄚ˪⿿⿿⿿ㄅㄧㄚㆷ⿿⿿⿿ㄅㄧㄚˊ⿿⿿⿿ㄅㄧㄚ˫⿿⿿⿿⿿ㄅㄧㄚ㆐ㆷ⿿⿿⿿⿿ㄅㄧㄚㆷ㆐⿿⿿⿿˙ㄅㄧㄚ⿿⿿⿿ㄅㄧㄚ^"
			+ "⿿⿿⿿˙ㄆㄨˊ⿿ㆠㄧ⿿⿿ㆠㄧˋ⿿⿿ㆠㄧ˪⿿⿿ㆠㄧㆷ⿿⿿ㆠㄧˊ⿿⿿ㆠㄧ˫⿿⿿⿿ㆠㄧ㆐ㆷ⿿⿿⿿ㆠㄧㆷ㆐⿿⿿˙ㆠㄧ⿿⿿ㆠㄧ^"
			+ "⿿⿿⿿˙ㄆㄨˊ⿿⿿⿿˙ㄆㄨˊㆬ⿿ㆬ ⿿ㆬˋ⿿ㆬ˪⿿ㆬㆷ⿿ㆬˊ⿿ㆬ˫⿿⿿ㆬ㆐ㆷ⿿⿿ㆬㆷ㆐⿿˙ㆬ⿿ㆬ^"
			+ "⿰因⿰⿴囗或" + "ㄅㄆㄇㄈㄉㄊㄋㄌㄍㄎㄏㄐㄑㄒㄓㄔㄕㄖㄗㄘㄙ "
			+ "ㄚㄛㄜㄝㄞㄟㄠㄡㄢㄣㄤㄥㄦ ㄧㄨㄩ ㄪㄫㄬ ㄭㄮ "
			+ "ㆠㆡㆢㆣ ㆤㆥㆦㆧㆨㆩㆪㆫㆬㆭㆮㆯㆰㆱㆲㆳ ㆴㆵㆶㆷ ㄅㄉㄍㄎㄏ ˊˇˋ˙˪˫㆐"
			+ "⿱攵力⿱⿰⿰糹言糹攵⿰糹言⿰言糹⿰⿰糹言糹言糹" + "⿰丨丨丨⿱⿰⿰糹言糹攵⿰⿰糹言糹攵";

	@Ignore @Test
	public void test() throws IOException
	{
		IDSParser ccUtility = new IDSParser(全部組字式, new ExpSequenceNoLookup());
		Vector<CharComponent> ccArray = ccUtility.解析();

		for (CharComponent charComponent : ccArray)
		{
			CharComponent 組字部件 = (CharComponent) charComponent;
			String 組字式 = 組字部件.樹狀結構組字式();
			System.out.println(組字式);
			BufferedImage 字型圖片 = new BufferedImage(字型大細, 字型大細,
					BufferedImage.TYPE_INT_ARGB);
			宋體組字工具.組字(組字式, 字型圖片.getGraphics());
			assertTrue(bufferedImagesEqual(字型圖片, 字型圖片));
			// Equals(11, 211);
			// OutputStream 輸出檔案 = null;
			// try
			// {
			// new File(圖片存放路徑).mkdirs();
			// 輸出檔案 = new FileOutputStream(圖片存放路徑+"/"+組字式+".png");
			// } catch (FileNotFoundException e)
			// {
			// e.printStackTrace();
			// }
			ByteArrayOutputStream 輸出檔案 = new ByteArrayOutputStream();
			ImageIO.write(字型圖片, "png", 輸出檔案);
			String 路徑 = "/"
					+ 組字式.replace("⿿", "⿳").replace("⿵", "⿴").replace("⿶", "⿴")
							.replace("⿷", "⿴")
							.replace("⿸", "⿴")
							.replace("⿹", "⿴")
							.replace("⿺", "⿴") + ".png";
			InputStream 組字圖片 = IDSGenTest.class.getResourceAsStream(路徑);
			for (byte 字元 : 輸出檔案.toByteArray())
			{
				assertEquals((字元), (byte) (組字圖片.read()));
			}
			assertEquals(組字圖片.read(), -1);

			組字圖片.close();
		}
	}

	boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2)
	{
		if (img1.getWidth() == img2.getWidth()
				&& img1.getHeight() == img2.getHeight())
		{
			for (int x = 0; x < img1.getWidth(); x++)
			{
				for (int y = 0; y < img1.getHeight(); y++)
				{
					if (img1.getRGB(x, y) != img2.getRGB(x, y))
						return false;
				}
			}
		}
		else
		{
			return false;
		}
		return true;
	}
}
