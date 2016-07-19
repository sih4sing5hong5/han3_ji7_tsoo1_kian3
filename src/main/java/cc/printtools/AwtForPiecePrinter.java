package cc.printtools;

import java.awt.Graphics2D;

import cc.movabletype.ChineseCharacterMovableTypeTzu;
import cc.movabletype.ChineseCharacterMovableTypeWen;
import cc.movabletype.PieceMovableTypeTzu;
import cc.movabletype.PieceMovableTypeWen;

/**
 * 物件活字遞迴列印工具。接收物件活字結構（<code>PieceMovableType</code>），並依結構遞迴列印在
 * <code>Graphics2D</code>上。
 * 
 * @author Ihc
 */
public class AwtForPiecePrinter implements ChineseCharacterTypePrinter
{
	/**
	 * 要輸出的目的地
	 */
	private Graphics2D graphics2d;

	/**
	 * 建立物件活字遞迴列印工具
	 * 
	 * @param graphics2d
	 *            要輸出的目的地
	 */
	public AwtForPiecePrinter(Graphics2D graphics2d)
	{
		this.graphics2d = graphics2d;
	}

	@Override
	public void printWen(
			ChineseCharacterMovableTypeWen chineseCharacterMovableTypeWen)
	{
		PieceMovableTypeWen pieceMovableTypeWen = (PieceMovableTypeWen) chineseCharacterMovableTypeWen;
		graphics2d.translate(pieceMovableTypeWen.getPiece().目標範圍()
				.getX(), pieceMovableTypeWen.getPiece().目標範圍().getY());
		pieceMovableTypeWen.getPiece().畫佇(graphics2d);
		graphics2d.translate(-pieceMovableTypeWen.getPiece().目標範圍()
				.getX(), -pieceMovableTypeWen.getPiece().目標範圍().getY());
		return;
	}

	@Override
	public void printTzu(
			ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = (PieceMovableTypeTzu) chineseCharacterMovableTypeTzu;
		graphics2d.translate(pieceMovableTypeTzu.getPiece().目標範圍()
				.getX(), pieceMovableTypeTzu.getPiece().目標範圍().getY());
		for (int i = 0; i < pieceMovableTypeTzu.getChildren().length; ++i)
		{
			pieceMovableTypeTzu.getChildren()[i].print(this);
		}
		graphics2d.translate(-pieceMovableTypeTzu.getPiece().目標範圍()
				.getX(), -pieceMovableTypeTzu.getPiece().目標範圍().getY());
		return;
	}
}
