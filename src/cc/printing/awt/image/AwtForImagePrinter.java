package cc.printing.awt.image;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;

import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.image.ImageMoveableTypeTzu;
import cc.moveable_type.image.ImageMoveableTypeWen;
import cc.printing.ChineseCharacterTypePrinter;

/**
 * 圖片活字遞迴列印工具。接收<code>ImageMovableTypeWen</code>或
 * <code>ImageMovableTypeTzu</code>，依結構遞迴找出字體並列印在<code>Graphics2D</code>上。
 * 
 * @author Ihc
 */
public class AwtForImagePrinter implements ChineseCharacterTypePrinter
{
	/** 要輸出的目的地 */
	private Graphics2D graphics2d;
	/** 列印的字型名稱 */
	private String fontName;
	/** 列印字型的選項 */
	private int fontStyle;

	/**
	 * 建立圖片活字列印工具
	 * 
	 * @param graphics2d
	 *            要輸出的目的地
	 * @param fontName
	 *            列印的字型名稱
	 * @param fontStyle
	 *            列印字型的選項
	 */
	public AwtForImagePrinter(Graphics2D graphics2d, String fontName,
			int fontStyle)
	{
		this.graphics2d = graphics2d;
		this.fontName = fontName;
		this.fontStyle = fontStyle;
	}

	@Override
	public void printWen(ChineseCharacterMovableTypeWen wen)
	{
		ImageMoveableTypeWen imageMoveableTypeWen = (ImageMoveableTypeWen) wen;
		Point scaler = imageMoveableTypeWen.getScaler();
		double xScaler = 1.0, yScaler = 1.0;
		if (scaler.x < scaler.y)
		{
			xScaler = (double) scaler.x / (double) scaler.y;
		}
		else
		{
			yScaler = (double) scaler.y / (double) scaler.x;
		}
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(xScaler, yScaler);
		int length = Math.max(scaler.x, scaler.y);
		Font font = new Font(fontName, fontStyle, length);
		Point position = imageMoveableTypeWen.getPosition();
		graphics2d.translate(position.x, position.y);
		GlyphVector glyphVector = font.createGlyphVector(graphics2d
				.getFontRenderContext(), Character
				.toChars((((ChineseCharacterWen) wen.getChineseCharacter())
						.getCodePoint())));
		glyphVector.setGlyphTransform(0, affineTransform);
		graphics2d.translate(0, scaler.y);
		graphics2d.draw(glyphVector.getOutline());
		graphics2d.translate(0, -scaler.y);
		graphics2d.translate(-position.x, -position.y);
		return;
	}

	@Override
	public void printTzu(
			ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu)
	{
		ImageMoveableTypeTzu imageMoveableTypeTzu = (ImageMoveableTypeTzu) chineseCharacterMovableTypeTzu;
		graphics2d.translate(imageMoveableTypeTzu.getPosition().x,
				imageMoveableTypeTzu.getPosition().y);
		chineseCharacterMovableTypeTzu.getChildren()[0].print(this);
		chineseCharacterMovableTypeTzu.getChildren()[1].print(this);
		graphics2d.translate(-imageMoveableTypeTzu.getPosition().x,
				-imageMoveableTypeTzu.getPosition().y);
		return;
	}
}
