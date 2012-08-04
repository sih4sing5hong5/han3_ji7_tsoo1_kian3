package cc.moveable_type.image;

import java.awt.Point;

import cc.core.ChineseCharacterTzu;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;

/**
 * 圖片活字樹狀結構的上層節點。不考慮部件的差異，皆視為同樣大小，每個部件當做矩形，記錄預計長寬（<code>region</code>）及位置（
 * <code>position</code> ），目前長寬（<code>scaler</code>）是儲存目前紀錄的。
 * 
 * @author Ihc
 */
public class ImageMoveableTypeTzu extends ChineseCharacterMovableTypeTzu
		implements ImageMoveableType
{
	/**
	 * 建立字活字結構
	 * 
	 * @param parent
	 *            上一層的活字結構。若上層為樹狀的樹根，傳入null
	 * @param chineseCharacterTzu
	 *            字部件結構
	 */
	public ImageMoveableTypeTzu(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterTzu chineseCharacterTzu)
	{
		super(parent, chineseCharacterTzu);
	}

	/** 預計長寬 */
	private Point region;
	/** 預計位置 */
	private Point position;
	/** 預計位置 */
	private Point scaler;

	@Override
	public Point getRegion()
	{
		return region;
	}

	@Override
	public void setRegion(Point region)
	{
		this.region = region;
	}

	@Override
	public Point getPosition()
	{
		return position;
	}

	@Override
	public void setPosition(Point position)
	{
		this.position = position;
	}

	@Override
	public Point getScaler()
	{
		return scaler;
	}

	@Override
	public void setScaler(Point scaler)
	{
		this.scaler = scaler;
	}
}
