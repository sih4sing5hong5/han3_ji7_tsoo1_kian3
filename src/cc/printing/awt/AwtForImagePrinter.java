package cc.printing.awt;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.image.ImageMoveableType;
import cc.moveable_type.image.ImageMoveableTypeWen;
import cc.printing.ChineseCharacterPrinter;

public class AwtForImagePrinter implements ChineseCharacterPrinter
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
		int length = Math.min(scaler.x, scaler.y);
		Font font = new Font(fontName, fontStyle, length);
		Point position = imageMoveableTypeWen.getPosition();
		graphics2d.translate(position.x, position.y);
		graphics2d.draw(font.createGlyphVector(
				graphics2d.getFontRenderContext(),
				Character.toChars((((ChineseCharacterWen) wen
						.getChineseCharacter()).getCodePoint()))).getOutline());
		graphics2d.translate(-position.x, -position.y);
		return;
	}

	@Override
	public void printTzu(
			ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu)
	{
		Point moving = new Point();
		switch (((ChineseCharacterTzu) chineseCharacterMovableTypeTzu
				.getChineseCharacter()).getType())
		{
		case horizontal:
			moving.x = 0;
			moving.y = ((ImageMoveableType) chineseCharacterMovableTypeTzu
					.getChildren()[0]).getRegion().y;
			break;
		case vertical:
			moving.x = ((ImageMoveableType) chineseCharacterMovableTypeTzu
					.getChildren()[0]).getRegion().x;
			moving.y = 0;
			break;
		case wrap:
			moving.x = ((ImageMoveableType) chineseCharacterMovableTypeTzu
					.getChildren()[0]).getRegion().x >> 2;
			moving.y = ((ImageMoveableType) chineseCharacterMovableTypeTzu
					.getChildren()[0]).getRegion().y >> 2;
			break;
		}
		chineseCharacterMovableTypeTzu.getChildren()[0].print(this);
		graphics2d.translate(moving.x, moving.y);
		chineseCharacterMovableTypeTzu.getChildren()[1].print(this);
		graphics2d.translate(-moving.x, -moving.y);
		return;
	}
}
