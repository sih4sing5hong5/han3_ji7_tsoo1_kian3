package cc.printing.awt;

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

public class AwtForImagePrinter implements ChineseCharacterTypePrinter
{
	private Graphics2D graphics2d;
	private String fontName;
	private int fontStyle;

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
