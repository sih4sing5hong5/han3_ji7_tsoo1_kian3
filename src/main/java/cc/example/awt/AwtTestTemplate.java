package cc.example.awt;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 測試的範例樣版。
 * 
 * @author Ihc
 */
abstract public class AwtTestTemplate extends JPanel
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1L;
	/** 視窗寬度 */
	protected static final int WIDTH = 1300;
	/** 視窗高度 */
	protected static final int HEIGHT = 730;
	/** 字型大小 */
	static final int TYPE_SIZE = 60;
	/** 每行字數 */
	static final int LINE_SIZE = 11;
	/** 測試漢字 */
	static String word = /* "    ⿰禾火秋⿰⿰火牙阝"; */"一百二十三尺意"
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
//			+ "⿳⿳⿳˙ㄆㄨˊ⿳⿳ㄅㄧㄚ⿳⿳⿳ㄅㄧㄚ˪⿳⿳⿳ㄅㄧㄚㆷ⿳ㆬㆷ⿳˙ㆬ"
			+ "⿳⿳⿳˙ㄆㄨˊ⿳⿳ㄅㄧㄚ⿳⿳⿳ㄅㄧㄚˋ⿳⿳⿳ㄅㄧㄚ˪⿳⿳⿳ㄅㄧㄚㆷ⿳⿳⿳ㄅㄧㄚˊ⿳⿳⿳ㄅㄧㄚ˫⿳⿳⿳⿳ㄅㄧㄚ㆐ㆷ⿳⿳⿳⿳ㄅㄧㄚㆷ㆐⿳⿳⿳˙ㄅㄧㄚ⿳⿳⿳ㄅㄧㄚ^"
			+ "⿳⿳⿳˙ㄆㄨˊ⿳ㆠㄧ⿳⿳ㆠㄧˋ⿳⿳ㆠㄧ˪⿳⿳ㆠㄧㆷ⿳⿳ㆠㄧˊ⿳⿳ㆠㄧ˫⿳⿳⿳ㆠㄧ㆐ㆷ⿳⿳⿳ㆠㄧㆷ㆐⿳⿳˙ㆠㄧ⿳⿳ㆠㄧ^"
			+ "⿳⿳⿳˙ㄆㄨˊ⿳⿳⿳˙ㄆㄨˊㆬ⿳ㆬ ⿳ㆬˋ⿳ㆬ˪⿳ㆬㆷ⿳ㆬˊ⿳ㆬ˫⿳⿳ㆬ㆐ㆷ⿳⿳ㆬㆷ㆐⿳˙ㆬ⿳ㆬ^";// */;
	/** 全字庫正宋體 */
	static final String 全字庫正宋體 = "全字庫正宋體";
	/** 全字庫正楷體 */
	static final String 全字庫正楷體 = "全字庫正楷體";
	/** 文泉驛正黑 */
	static final String 文泉驛正黑 = "文泉驛正黑";
	/** 文鼎中圓 */
	static final String 文鼎中圓 = "文鼎中圓";
	/** 超研澤中圓 */
	static final String 超研澤中圓 = "超研澤中圓";

	/**
	 * 範例型態視窗的設定佮產生。
	 * 
	 * @param 範例型態
	 *            愛產生的範例型態
	 */
	protected static void run(Container 範例型態)
	{
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		f.setContentPane(範例型態);
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);

	}

	@Override
	public String getName()
	{
		return "漢字測試";
	}

	@Override
	public int getWidth()
	{
		return WIDTH;
	}

	@Override
	public int getHeight()
	{
		return HEIGHT;
	}
}
