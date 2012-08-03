package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.adjusting.bolder.ChineseCharacterTypeBolder;
import cc.core.ChineseCharacterTzu;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * 物件活字調整工具。把活字的資訊全部集中在同一個物件上（<code>Piece</code>， <code>RectangularArea</code>型態
 * ），方便函式傳遞與使用，而且物件上也有相對應操縱的函式。
 * <p>
 * <code>MergePiece</code>設定時只記錄字體資訊，調整時看兩兩部件大小，縮小成同高或同寬後再組合，彈性大。但現階段受加粗邊角問題影響。
 * 
 * @author Ihc
 */
public class MergePieceAdjuster extends SimplePieceAdjuster
{
	/**
	 * 建立物件活字調整工具
	 * 
	 * @param chineseCharacterTypeBolder
	 *            物件活字加寬工具
	 * @param precision
	 *            合併、調整的精細度
	 */
	public MergePieceAdjuster(
			ChineseCharacterTypeBolder chineseCharacterTypeBolder,
			double precision)
	{
		super(chineseCharacterTypeBolder, precision);
	}

	@Override
	public void adjustWen(
			ChineseCharacterMovableTypeWen chineseCharacterMovableTypeWen)
	{
		return;
	}

	@Override
	public void adjustTzu(
			ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = (PieceMovableTypeTzu) chineseCharacterMovableTypeTzu;
		for (int i = 0; i < pieceMovableTypeTzu.getChildren().length; ++i)
		{
			pieceMovableTypeTzu.getChildren()[i].adjust(this);
		}
		switch (((ChineseCharacterTzu) pieceMovableTypeTzu
				.getChineseCharacter()).getType())
		{
		case horizontal:
			horizontalMerging(pieceMovableTypeTzu);
			break;
		case vertical:
			verticalMerging(pieceMovableTypeTzu);
			break;
		case wrap:
			wrapMerging(pieceMovableTypeTzu);
			break;
		}
		return;
	}

	/**
	 * 水平組合活字
	 * 
	 * @param pieceMovableTypeTzu
	 *            要調整的合體活字
	 */
	void horizontalMerging(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		PieceMovableType left = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], right = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];
		PieceMovableType greater = null, smaller = null;
		if (left.getPiece().getBounds2D().getHeight() > right.getPiece()
				.getBounds2D().getHeight())
		{
			greater = left;
			smaller = right;
		}
		else
		{
			greater = right;
			smaller = left;
		}
		// TODO 要哪一個尚未決定，不知為何正方形收縮記憶體會不足
		// AffineTransform shrinkTransform =
		// getAffineTransform(smaller.getPiece()
		// .getBounds2D().getHeight()
		// / greater.getPiece().getBounds2D().getHeight());
		AffineTransform shrinkTransform = getAffineTransform(1.0, smaller
				.getPiece().getBounds2D().getHeight()
				/ greater.getPiece().getBounds2D().getHeight());
		RectangularArea greaterPiece = greater.getPiece();
		shrinkPieceByFixingStroke(greaterPiece, shrinkTransform);

		double miniPos = 0.0, maxiPos = left.getPiece().getBounds2D()
				.getWidth();
		while (miniPos + getPrecision() < maxiPos)
		{
			double middlePos = 0.5 * (miniPos + maxiPos);
			right.getPiece().moveToOrigin();
			right.getPiece().moveTo(middlePos, 0);
			if (areIntersected(left.getPiece(), right.getPiece()))
				miniPos = middlePos;
			else
				maxiPos = middlePos;
		}
		right.getPiece().moveToOrigin();
		right.getPiece().moveTo(miniPos, 0);
		pieceMovableTypeTzu.getPiece().reset();
		pieceMovableTypeTzu.getPiece().add(left.getPiece());
		pieceMovableTypeTzu.getPiece().add(right.getPiece());
		right.getPiece().moveToOrigin();
		return;
	}

	/**
	 * 垂直組合活字
	 * 
	 * @param pieceMovableTypeTzu
	 *            要調整的合體活字
	 */
	void verticalMerging(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		PieceMovableType up = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], down = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];
		PieceMovableType greater = null, smaller = null;
		if (up.getPiece().getBounds2D().getWidth() > down.getPiece()
				.getBounds2D().getWidth())
		{
			greater = up;
			smaller = down;
		}
		else
		{
			greater = down;
			smaller = up;
		}
		// AffineTransform shrinkTransform =
		// getAffineTransform(smaller.getPiece()
		// .getBounds2D().getWidth()
		// / greater.getPiece().getBounds2D().getWidth());
		AffineTransform shrinkTransform = getAffineTransform(smaller.getPiece()
				.getBounds2D().getWidth()
				/ greater.getPiece().getBounds2D().getWidth(), 1.0);
		RectangularArea greaterPiece = greater.getPiece();
		shrinkPieceByFixingStroke(greaterPiece, shrinkTransform);

		double miniPos = 0.0, maxiPos = up.getPiece().getBounds2D().getHeight();
		while (miniPos + getPrecision() < maxiPos)
		{
			double middlePos = 0.5 * (miniPos + maxiPos);
			down.getPiece().moveToOrigin();
			down.getPiece().moveTo(0, middlePos);
			if (areIntersected(up.getPiece(), down.getPiece()))
				miniPos = middlePos;
			else
				maxiPos = middlePos;
		}
		down.getPiece().moveToOrigin();
		down.getPiece().moveTo(0, miniPos);
		pieceMovableTypeTzu.getPiece().reset();
		pieceMovableTypeTzu.getPiece().add(up.getPiece());
		pieceMovableTypeTzu.getPiece().add(down.getPiece());
		down.getPiece().moveToOrigin();
		return;
	}

	/**
	 * 包圍組合活字
	 * 
	 * @param pieceMovableTypeTzu
	 *            要調整的合體活字
	 */
	void wrapMerging(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		// TODO 暫時替代用
		PieceMovableType out = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], in = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];
		pieceMovableTypeTzu.getPiece().reset();
		pieceMovableTypeTzu.getPiece().add(out.getPiece());
		pieceMovableTypeTzu.getPiece().add(in.getPiece());
		return;
	}

	/**
	 * 給縮放值，取得相對應的縮放矩陣
	 * 
	 * @param scaler
	 *            縮放值
	 * @return 縮放矩陣
	 */
	protected AffineTransform getAffineTransform(double scaler)
	{
		return getAffineTransform(scaler, scaler);
	}

	/**
	 * 給水平、垂直縮放值，取得相對應的縮放矩陣
	 * 
	 * @param scalerX
	 *            水平縮放值
	 * @param scalerY
	 *            垂直縮放值
	 * @return 縮放矩陣
	 */
	protected AffineTransform getAffineTransform(double scalerX, double scalerY)
	{
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(scalerX, scalerY);
		return affineTransform;
	}

	/**
	 * 判斷二個物件活字是否重疊。用在調整部件間的距離，<code>Area</code>內建的減去實作。
	 * 
	 * @param first
	 *            第一個物件活字
	 * @param second
	 *            第二個物件活字
	 * @return 是否重疊
	 */
	protected boolean areIntersected(RectangularArea first,
			RectangularArea second)
	{
		RectangularArea rectangularArea = new RectangularArea(first);
		rectangularArea.subtract(second);
		return !rectangularArea.equals(first);
	}

	/**
	 * 要給<code>AwtForSinglePiecePrinter</code>列印前必須把物件活字依預計位置及大小（
	 * <code>getTerritory()</code>）產生一個新的物件。
	 * 
	 * @param pieceMovableType
	 *            物件活字
	 * @return 活字的物件資訊
	 */
	public RectangularArea format(PieceMovableType pieceMovableType)
	{
		RectangularArea target = new RectangularArea(
				pieceMovableType.getPiece());
		double widthCoefficient = target.getTerritory().getWidth()
				/ target.getBounds2D().getWidth(), heightCoefficient = target
				.getTerritory().getHeight() / target.getBounds2D().getHeight();
		AffineTransform shrinkTransform = getAffineTransform(widthCoefficient,
				heightCoefficient);
		shrinkPieceByFixingStroke(target, shrinkTransform);
		target.moveTo(target.getTerritory().getX(), target.getTerritory()
				.getY());
		return target;
	}

//	@Override
//	protected void shrinkPieceByFixingStroke(RectangularArea rectangularArea,
//			AffineTransform affineTransform)
//	{
//		// super.shrinkPieceByFixingStroke(rectangularArea, affineTransform);
//		rectangularArea.transform(affineTransform);
//	}

	// TODO 調整函式順序
}
