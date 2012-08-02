package cc.printing.awt.piece;

import java.awt.Graphics2D;

import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
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
		graphics2d.translate(pieceMovableTypeWen.getPiece().getTerritory()
				.getX(), pieceMovableTypeWen.getPiece().getTerritory().getY());
		graphics2d.draw(pieceMovableTypeWen.getPiece());
		graphics2d.translate(-pieceMovableTypeWen.getPiece().getTerritory()
				.getX(), -pieceMovableTypeWen.getPiece().getTerritory().getY());
		return;
	}

	@Override
	public void printTzu(
			ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = (PieceMovableTypeTzu) chineseCharacterMovableTypeTzu;
		graphics2d.translate(pieceMovableTypeTzu.getPiece().getTerritory()
				.getX(), pieceMovableTypeTzu.getPiece().getTerritory().getY());
		for (int i = 0; i < pieceMovableTypeTzu.getChildren().length; ++i)
		{
//			PieceMovableType child = (PieceMovableType) pieceMovableTypeTzu
//					.getChildren()[i];
			pieceMovableTypeTzu.getChildren()[i].print(this);
		}
		graphics2d.translate(-pieceMovableTypeTzu.getPiece().getTerritory()
				.getX(), -pieceMovableTypeTzu.getPiece().getTerritory().getY());
		return;
	}
}
