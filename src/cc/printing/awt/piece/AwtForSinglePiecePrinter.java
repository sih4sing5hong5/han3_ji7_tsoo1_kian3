package cc.printing.awt.piece;

import java.awt.Graphics2D;

import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeWen;
import cc.moveable_type.rectangular_area.活字單元;
import cc.printing.ChineseCharacterTypePrinter;

/**
 * 物件活字單一列印工具。接收物件活字結構（<code>PieceMovableType</code>），並列印該活字在<code>Graphics2D</code>上。
 * 
 * @author Ihc
 */
public class AwtForSinglePiecePrinter implements ChineseCharacterTypePrinter
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
	public AwtForSinglePiecePrinter(Graphics2D graphics2d)
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
		graphics2d.draw(pieceMovableTypeTzu.getPiece());
		return;
	}

	/**
	 * 列印活字物件
	 * 
	 * @param rectangularArea
	 *            活字物件
	 */
	public void printPiece(活字單元 rectangularArea)
	{
		graphics2d.draw(rectangularArea);
		return;
	}
}
