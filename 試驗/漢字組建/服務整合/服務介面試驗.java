package 漢字組建.服務整合;

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

import 漢字組建.解析工具.組字式序列解析工具;
import 漢字組建.部件.部件;
import 漢字組建.部件結構調整工具.組字式結構正規化工具;
import cc.adjusting.bolder.FunctinoalBasicBolder;
import cc.adjusting.piece.MergePieceAdjuster;
import cc.core.展開式免查詢;
import cc.core.展開式查詢工具;
import cc.moveable_type.rectangular_area.分離活字加粗;
import cc.setting.ChineseCharacterTypeSetter;
import cc.setting.piece.字型參考設定工具;
import cc.setting.piece.對照字體;
import cc.setting.piece.展開式查通用字型編號;
import cc.setting.piece.無愛查通用字型編號;
import cc.tool.database.字串與控制碼轉換;
import cc.連線服務.組字介面;

public class 服務介面試驗
{

	/** 組宋體用的工具 */
	protected 組字介面 宋體組字工具;

	int 字型大細 = 200;

	@Before
	public void setUp()
	{

		int 普通字型屬性 = 0;
		/** 定義異寫編號數字 */
		int[] 編號陣列 = 字串與控制碼轉換.轉換成控制碼("甲乙丙丁戊己庚辛壬癸子丑寅卯辰巳午未申酉戍亥陰陽乾坤震巽坎離艮兌");

		展開式查詢工具 查詢方式 = new 展開式免查詢();
		// TODO 資料庫連線展開式查詢(連線) 展開式免查詢()
		組字式結構正規化工具 正規化工具 = new 組字式結構正規化工具();
		// 異寫式查詢工具 異寫式查詢 = new 資料庫連線異寫式查詢(連線);
		MergePieceAdjuster 調整工具 = new MergePieceAdjuster(
		// new FunctinoalBasicBolder(new Stroke[] {}, 01),
				1e-1, 5);
		展開式查通用字型編號 展開式查通用字型編號工具 = new 無愛查通用字型編號();
		// TODO 用資料庫查展開式的通用字型編號(連線) 無愛查通用字型編號()
		分離活字加粗 活字加粗 = new 分離活字加粗(
				new FunctinoalBasicBolder(new Stroke[] {}, 01), 1e-1);
		ChineseCharacterTypeSetter 宋體設定工具 = new 字型參考設定工具(展開式查通用字型編號工具, 對照字體
				.提著吳守禮注音摻宋體字體().調整字體參數(普通字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT), 活字加粗);

		宋體組字工具 = new 組字介面(查詢方式, 正規化工具, 編號陣列, 宋體設定工具, 調整工具, 活字加粗, 普通字型屬性, 字型大細);
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
			+ "⿰彳⿱羊羊⿱羊⿰羊羊⿱⿰羊羊⿰羊羊⿰⿱羊羊⿱羊羊⿰羊⿰羊羊⿰⿰羊羊羊⿰丨丨丨"
			+ "⿿⿿⿿˙ㄆㄨˊ⿿⿿ㄅㄧㄚ⿿⿿⿿ㄅㄧㄚˋ⿿⿿⿿ㄅㄧㄚ˪⿿⿿⿿ㄅㄧㄚㆷ⿿⿿⿿ㄅㄧㄚˊ⿿⿿⿿ㄅㄧㄚ˫⿿⿿⿿⿿ㄅㄧㄚ㆐ㆷ⿿⿿⿿⿿ㄅㄧㄚㆷ㆐⿿⿿⿿˙ㄅㄧㄚ⿿⿿⿿ㄅㄧㄚ^"
			+ "⿿⿿⿿˙ㄆㄨˊ⿿ㆠㄧ⿿⿿ㆠㄧˋ⿿⿿ㆠㄧ˪⿿⿿ㆠㄧㆷ⿿⿿ㆠㄧˊ⿿⿿ㆠㄧ˫⿿⿿⿿ㆠㄧ㆐ㆷ⿿⿿⿿ㆠㄧㆷ㆐⿿⿿˙ㆠㄧ⿿⿿ㆠㄧ^"
			+ "⿿⿿⿿˙ㄆㄨˊ⿿⿿⿿˙ㄆㄨˊㆬ⿿ㆬ ⿿ㆬˋ⿿ㆬ˪⿿ㆬㆷ⿿ㆬˊ⿿ㆬ˫⿿⿿ㆬ㆐ㆷ⿿⿿ㆬㆷ㆐⿿˙ㆬ⿿ㆬ^"
			+ "⿰因⿰⿴囗或" + "⿰⿰⿱⿱⿱我薛丞宏愛⿱文莉" + "ㄅㄆㄇㄈㄉㄊㄋㄌㄍㄎㄏㄐㄑㄒㄓㄔㄕㄖㄗㄘㄙ "
			+ "ㄚㄛㄜㄝㄞㄟㄠㄡㄢㄣㄤㄥㄦ ㄧㄨㄩ ㄪㄫㄬ ㄭㄮ "
			+ "ㆠㆡㆢㆣ ㆤㆥㆦㆧㆨㆩㆪㆫㆬㆭㆮㆯㆰㆱㆲㆳ ㆴㆵㆶㆷ ㄅㄉㄍㄎㄏ ˊˇˋ˙˪˫㆐"
			+ "⿱攵力⿱⿰⿰糹言糹攵⿰糹言⿰言糹⿰⿰糹言糹言糹" + "⿰丨丨丨⿱⿰⿰糹言糹攵⿰⿰糹言糹攵";
	static String 圖片存放路徑 = "組字圖片";

	@Test
	public void test() throws IOException
	{
		組字式序列解析工具 ccUtility = new 組字式序列解析工具(全部組字式, new 展開式免查詢());
		Vector<部件> ccArray = ccUtility.解析();

		for (部件 部件 : ccArray)
		{
			部件 組字部件 = (部件) 部件;
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
			InputStream 組字圖片 = new FileInputStream(圖片存放路徑 + "/"
					+ 組字式.replace("⿿", "⿳") + ".png");
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
