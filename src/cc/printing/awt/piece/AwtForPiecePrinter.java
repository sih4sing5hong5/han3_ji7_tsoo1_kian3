package cc.printing.awt.piece;

import java.awt.Graphics2D;

import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeWen;
import cc.printing.ChineseCharacterTypePrinter;

public class AwtForPiecePrinter implements ChineseCharacterTypePrinter
{
	private Graphics2D graphics2d;

	public AwtForPiecePrinter(Graphics2D graphics2d)
	{
		this.graphics2d = graphics2d;
	}

	@Override
	public void printWen(
			ChineseCharacterMovableTypeWen chineseCharacterMovableTypeWen)
	{
		PieceMovableTypeWen pieceMovableTypeWen = (PieceMovableTypeWen) chineseCharacterMovableTypeWen;
		graphics2d.draw(pieceMovableTypeWen.getPiece());
		return;
	}

	@Override
	public void printTzu(
			ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = (PieceMovableTypeTzu) chineseCharacterMovableTypeTzu;
		for (int i = 0; i < pieceMovableTypeTzu.getChildren().length; ++i)
		{
			PieceMovableType child = (PieceMovableType) pieceMovableTypeTzu
					.getChildren()[i];
			graphics2d.translate(child.getPiece().getTerritory().getX(), child
					.getPiece().getTerritory().getY());
			pieceMovableTypeTzu.getChildren()[i].print(this);
			graphics2d.translate(-child.getPiece().getTerritory().getX(),
					-child.getPiece().getTerritory().getY());
		}
		return;
	}
}
