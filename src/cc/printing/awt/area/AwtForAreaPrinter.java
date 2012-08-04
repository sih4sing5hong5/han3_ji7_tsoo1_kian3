package cc.printing.awt.area;

import java.awt.Graphics2D;

import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.area.AreaMovableType;
import cc.moveable_type.area.AreaMovableTypeTzu;
import cc.moveable_type.area.AreaMovableTypeWen;
import cc.printing.ChineseCharacterTypePrinter;

/**
 * 區塊活字遞迴列印工具。接收<code>AreaMovableTypeWen</code>或 <code>AreaMovableTypeTzu</code>
 * ，依結構遞迴列印在<code>Graphics2D</code>上。
 * 
 * @author Ihc
 */
public class AwtForAreaPrinter implements ChineseCharacterTypePrinter
{
	/** 要輸出的目的地 */
	private Graphics2D graphics2d;

	/**
	 * 建立區塊活字列印工具
	 * 
	 * @param graphics2d
	 *            要輸出的目的地
	 */
	public AwtForAreaPrinter(Graphics2D graphics2d)
	{
		this.graphics2d = graphics2d;
	}

	@Override
	public void printWen(ChineseCharacterMovableTypeWen wen)
	{
		AreaMovableTypeWen areaMovableTypeWen = (AreaMovableTypeWen) wen;
		// double fontWidth = areaMovableTypeWen.getBound().getWidth(),
		// fontHeight = areaMovableTypeWen
		// .getBound().getHeight();
		// AffineTransform affineTransform = new AffineTransform();
		// affineTransform.setToScale(fontWidth / 100, fontHeight / 100);
		// Area area = new Area( areaMovableTypeWen.getArea());
		// area.transform(affineTransform);
		// graphics2d.draw(area);
		graphics2d.draw(areaMovableTypeWen.getArea());
		return;
	}

	@Override
	public void printTzu(ChineseCharacterMovableTypeTzu tzu)
	{
		AreaMovableTypeTzu areaMovableTypeTzu = (AreaMovableTypeTzu) tzu;
		for (int i = 0; i < areaMovableTypeTzu.getChildren().length; ++i)
		{
			AreaMovableType child = (AreaMovableType) areaMovableTypeTzu
					.getChildren()[i];
			graphics2d.translate(child.getBound().getX(), child.getBound()
					.getY());
			areaMovableTypeTzu.getChildren()[i].print(this);
			graphics2d.translate(-child.getBound().getX(), -child.getBound()
					.getY());
		}
		return;
	}
}
