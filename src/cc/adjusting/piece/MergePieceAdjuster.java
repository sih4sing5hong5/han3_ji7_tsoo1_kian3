package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.adjusting.bolder.ChineseCharacterTypeBolder;
import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterTzu;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.RectangularArea;
import cc.moveable_type.rectangular_area.ShapeInformation;

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
	/** 處理包圍關係的活字時所使用的工具 */
	private 包圍整合分派工具 分派工具;

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
		分派工具 = new 包圍整合分派工具();
		分派工具.add(new 上蓋包圍工具(this));
		分派工具.add(new 左下包圍工具(this));
		分派工具.add(new 左上包圍工具(this));
		分派工具.add(new 右上包圍工具(this));
		分派工具.add(new 左右上三邊包圍工具(this));
		分派工具.add(new 左右下三邊包圍工具(this));
		分派工具.add(new 上下左三邊包圍工具(this));
		分派工具.add(new 右上下勾包圍工具(this));
		分派工具.add(new 四面包圍工具(this));
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
	 * @param 物件活字
	 *            要調整的合體活字
	 */
	void horizontalMerging(PieceMovableTypeTzu 物件活字)
	{
		水平拼合模組 模組 = new 水平拼合模組(this);
		二元搜尋貼合工具 貼合工具 = new 二元搜尋貼合工具();
		貼合工具.執行(模組,物件活字.取得活字物件());

		RectangularArea[] 調整結果 = 模組.取得調整後活字物件();
		物件活字.getPiece().重設並組合活字(調整結果);
		return;
	}

	/**
	 * 垂直組合活字
	 * 
	 * @param 物件活字
	 *            要調整的合體活字
	 */
	void verticalMerging(PieceMovableTypeTzu 物件活字)
	{
		垂直拼合模組 模組 = new 垂直拼合模組(this);
		二元搜尋貼合工具 貼合工具 = new 二元搜尋貼合工具();
		貼合工具.執行(模組,物件活字.取得活字物件());

		RectangularArea[] 調整結果 = 模組.取得調整後活字物件();
		物件活字.getPiece().重設並組合活字(調整結果);
		return;
	}

	/**
	 * 包圍組合活字
	 * 
	 * @param 物件活字
	 *            要調整的合體活字
	 */
	void wrapMerging(PieceMovableTypeTzu 物件活字)
	{
		if (!分派工具.組合(物件活字))
		{
			System.out.println("QQ 沒組合工具");
			ChineseCharacterTzu chineseCharacterTzu = (ChineseCharacterTzu) 物件活字
					.getChineseCharacter();
			ChineseCharacter chineseCharacter = chineseCharacterTzu
					.getChildren()[0];
			switch (chineseCharacter.getCodePoint())
			{
			case 2:// TODO
			}
			PieceMovableType out = (PieceMovableType) 物件活字.getChildren()[0], in = (PieceMovableType) 物件活字
					.getChildren()[1];
			物件活字.getPiece().reset();
			物件活字.getPiece().add(out.getPiece());
			物件活字.getPiece().add(in.getPiece());
		}
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
	 *            第一個活字物件
	 * @param second
	 *            第二個活字物件
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
	 * 判斷二個物件活字適合接近的係數，筆劃角度太相同會黏在一起。用來調整部件間的距離，用邊長減少長度除以邊界長度來計算。
	 * 
	 * @param first
	 *            第一個活字物件
	 * @param second
	 *            第二個活字物件
	 * @param boundaryLength
	 *            用來比較消失邊長的邊界長度
	 * @return 適合接近的係數
	 */
	protected double nonsuitableToClose(RectangularArea first,
			RectangularArea second, double boundaryLength)
	{
		// TODO
		ShapeInformation firstInformation = new ShapeInformation(first), secondInformation = new ShapeInformation(
				second);
		RectangularArea rectangularArea = new RectangularArea(first);
		rectangularArea.add(second);
		ShapeInformation shapeInformation = new ShapeInformation(
				rectangularArea);
		// System.out
		// .println("s="
		// + shapeInformation.getApproximativeCircumference()
		// + " len="
		// + boundaryLength
		// + " fi="
		// + firstInformation.getApproximativeCircumference()
		// + " sec="
		// + secondInformation.getApproximativeCircumference()
		// + " fs="
		// + (firstInformation.getApproximativeCircumference() +
		// secondInformation
		// .getApproximativeCircumference()));
		// TODO 人工參數
		return (firstInformation.getApproximativeCircumference()
				+ secondInformation.getApproximativeCircumference() - shapeInformation
				.getApproximativeCircumference()) / boundaryLength;
	}

	/**
	 * 要給<code>AwtForSinglePiecePrinter</code>列印前必須把物件活字依預計位置及大小（
	 * <code>getTerritory()</code>）產生一個新的物件。
	 * 
	 * @param pieceMovableType
	 *            物件活字
	 * @return 格式過後的活字物件資訊
	 */
	public RectangularArea format(PieceMovableType pieceMovableType)
	{
		return getNewPieceByTerritory(new RectangularArea(
				pieceMovableType.getPiece()));
	}

	/**
	 * 把物件活字依預計位置及大小（<code>getTerritory()</code>）產生一個新的活字物件。
	 * 
	 * @param target
	 *            活字物件
	 * @return 格式過後的活字物件資訊
	 */
	public RectangularArea getNewPieceByTerritory(RectangularArea target)
	{
		double widthCoefficient = target.getTerritory().getWidth()
				/ target.getBounds2D().getWidth(), heightCoefficient = target
				.getTerritory().getHeight() / target.getBounds2D().getHeight();
		AffineTransform shrinkTransform = getAffineTransform(widthCoefficient,
				heightCoefficient);
		shrinkPieceByFixingStroke(target, shrinkTransform);
		target.moveBy(target.getTerritory().getX(), target.getTerritory()
				.getY());
		return target;
	}

	/**
	 * 產生一個相同圖形的正方體活字物件。
	 * 
	 * @param rectangularArea
	 *            活字物件
	 * @return 格式過後的活字物件資訊
	 */
	public RectangularArea getPieceWithSquareTerritory(RectangularArea rectangularArea)
	{
		RectangularArea target=new RectangularArea(rectangularArea);
		target.setTerritory(target.getBounds2D());
		double value = Math.min(target.getTerritory().getWidth(), target
				.getTerritory().getHeight());
		target.setTerritoryDimension(value, value);
		return getNewPieceByTerritory(target);
	}
}
