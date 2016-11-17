package cc.example.awt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cc.example.reference.ControlPointsStroke;
import cc.stroke.FunctinoalBasicBolder;
import cc.stroke.NullStroke;
import cc.stroke.SimplifyStroke;

/**
 * 加寬工具加寬趨勢分析, 將不同加寬度的字擺放在一起。
 * 
 * @author Ihc
 */
public class AwtStrokeWidthTest extends JPanel
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1L;
	/** 視窗寬度 */
	static final int WIDTH = 1620;
	/** 視窗高度 */
	static final int HEIGHT = 1050;

	@Override
	public void paint(Graphics g1)
	{
		System.out.println("D");
		// if(true)
		// return;

		Graphics2D graphics2D = (Graphics2D) g1;
		Font f = new Font("全字庫正宋體", Font.BOLD, 700);
		GlyphVector gv = f.createGlyphVector(graphics2D.getFontRenderContext(),
				"變");
		System.out.println(gv.getNumGlyphs());
		Area area = new Area(gv.getOutline());
		// area = new Area(new Rectangle2D.Double(100, 200, 100, 200));
		// area = new Area(new QuadCurve2D.Double(0.0, 0.0, 100.0, 0.0, 100,
		// 100));
		// AffineTransform affineTransform = new AffineTransform();
		// affineTransform.setToTranslation(4, 4);
		// // area.transform(affineTransform);
		// area = new Area(new CubicCurve2D.Double(0.0, 0.0, 100.0, 0.0, 100,
		// 100,
		// 0, 100));
		graphics2D.setColor(Color.RED);
		graphics2D.setStroke(new ControlPointsStroke(5));
		graphics2D.translate(10, 610);
//		Stroke stroke = new SimplifyStroke();
		System.out.println("--");
		// ShapeAnalyst shapeAnalyst = new ShapeAnalyst(area);
		System.out.println("--");
//		area = new Area(stroke.createStrokedShape(area));
		// shapeAnalyst = new ShapeAnalyst(area);
		System.out.println("--");
		graphics2D.draw(area);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.translate(750, 000);
		FunctinoalBasicBolder functinoalBasicBolder = new FunctinoalBasicBolder(
				new Stroke[] {  new SimplifyStroke()/*,new UnsharpenStroke(1.0) */}, 1);
		Area a = area;
		for (int i = 10; i >= 0; i -= 2)
		{
			Color color = new Color((10 - i) * 0x001010 + 0xff0000);
			graphics2D.setColor(color);
			graphics2D.setStroke(functinoalBasicBolder.getStroke(i * 5));
//			graphics2D.setStroke(new BasicStroke(i * 5));
			// stroke = new RadialStroke(i * 5);
			// area = new Area();
			graphics2D.draw(area);
			// graphics2D.draw(stroke.createStrokedShape(area));
			graphics2D.setStroke(new NullStroke());
			graphics2D.draw(area);
			// graphics2D.draw(stroke.createStrokedShape(area));
			// graphics2D.translate(10, 0);
		}
		Color color = new Color(12 * 0x001010 + 0xff0000);
		graphics2D.setColor(color);
		// graphics2D.setStroke(new NullStroke());
		graphics2D.draw(a);
		System.out.println("XD");
	}

	/**
	 * 主函式，設定相關視窗資訊。
	 * 
	 * @param args
	 *            呼叫引數
	 */
	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		f.setContentPane(new AwtStrokeWidthTest());
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);
	}

	@Override
	public String getName()
	{
		return "加寬工具加寬趨勢分析";
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
