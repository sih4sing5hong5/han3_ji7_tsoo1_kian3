package cc.stroke;

import java.awt.Stroke;

/**
 * 使用<code>FunctionalBasicStroke</code>的物件活字筆劃加寬工具包。
 * 
 * @author Ihc
 */
public class FunctinoalBasicBolder implements ChineseCharacterTypeBolder
{
	/** 筆劃加寬前的預先處理 */
	private Stroke[] functionalStroke;
	/** 筆劃加寬的分段數 */
	private int times;

	/**
	 * 建立物件活字筆劃加寬工具。
	 */
	public FunctinoalBasicBolder()
	{
		this(null, 1);
	}

	/**
	 * 建立物件活字筆劃加寬工具。
	 * 
	 * @param times
	 *            筆劃加寬的分段數
	 */
	public FunctinoalBasicBolder(int times)
	{
		this(null, times);
	}

	/**
	 * 建立物件活字筆劃加寬工具。
	 * 
	 * @param functionalStroke
	 *            筆劃加寬前的預先處理
	 */
	public FunctinoalBasicBolder(Stroke[] functionalStroke)
	{
		this(functionalStroke, 1);
	}

	/**
	 * 建立物件活字筆劃加寬工具。
	 * 
	 * @param functionalStroke
	 *            筆劃加寬前的預先處理
	 * @param times
	 *            筆劃加寬的分段數
	 */
	public FunctinoalBasicBolder(Stroke[] functionalStroke, int times)
	{
		this.functionalStroke = functionalStroke;
		this.times = times;
	}

	@Override
	public Stroke getStroke(double width)
	{
		double smallWidth = width;
		if (times > 0)
			smallWidth /= times;
		return new FunctionalBasicStroke(functionalStroke, times, smallWidth);
	}
}
