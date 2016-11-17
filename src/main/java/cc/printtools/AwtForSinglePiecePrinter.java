package cc.printtools;

import java.awt.Graphics2D;

import cc.movabletype.ChineseCharacterMovableTypeTzu;
import cc.movabletype.ChineseCharacterMovableTypeWen;
import cc.movabletype.PieceMovableTypeTzu;
import cc.movabletype.PieceMovableTypeWen;
import cc.movabletype.SeprateMovabletype;

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
		pieceMovableTypeWen.getPiece().畫佇(graphics2d);
		return;
	}

	@Override
	public void printTzu(
			ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = (PieceMovableTypeTzu) chineseCharacterMovableTypeTzu;
		pieceMovableTypeTzu.getPiece().畫佇(graphics2d);
		return;
	}

	/**
	 * 列印活字物件
	 * 
	 * @param rectangularArea
	 *            活字物件
	 */
	public void printPiece(SeprateMovabletype rectangularArea)
	{
		rectangularArea.畫佇(graphics2d);
		return;
	}
}
