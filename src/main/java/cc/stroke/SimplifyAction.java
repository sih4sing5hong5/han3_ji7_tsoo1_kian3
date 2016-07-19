package cc.stroke;

import java.awt.geom.GeneralPath;

import cc.stroketool.PathAction;
import cc.stroketool.Point2DWithVector;

/**
 * 去掉冗點的循訪反應動作介面。
 * 
 * @author Ihc
 */
public class SimplifyAction implements PathAction
{
	/** 去掉冗點的結果 */
	private GeneralPath generalPath;

	/**
	 * 建立去掉冗點循訪反應動作介面。
	 * 
	 * @param rule
	 *            圖形填圖規則
	 */
	public SimplifyAction(int rule)
	{
		generalPath = new GeneralPath(rule);
	}

	@Override
	public void doActionOnMoveTo(double[] controlPoint)
	{
		generalPath.moveTo(controlPoint[0], controlPoint[1]);
		return;
	}

	@Override
	public void doActionOnLineTo(double[] controlPoint)
	{
		Point2DWithVector currentPoint = new Point2DWithVector(
				generalPath.getCurrentPoint());
		System.out.println(currentPoint + " " + controlPoint[0] + " "
				+ controlPoint[1]);
		if (!currentPoint.areTheSameAs(controlPoint[0], controlPoint[1]))
			generalPath.lineTo(controlPoint[0], controlPoint[1]);
		else
			System.out.println("OH");
		return;
	}

	@Override
	public void doActionOnQuadTo(double[] controlPoint)
	{
		Point2DWithVector currentPoint = new Point2DWithVector(
				generalPath.getCurrentPoint());
		if (!currentPoint.areTheSameAs(controlPoint[0], controlPoint[1])
				|| !currentPoint.areTheSameAs(controlPoint[2], controlPoint[3]))
			generalPath.quadTo(controlPoint[0], controlPoint[1],
					controlPoint[2], controlPoint[3]);
		return;
	}

	@Override
	public void doActionOnCubicTo(double[] controlPoint)
	{
		Point2DWithVector currentPoint = new Point2DWithVector(
				generalPath.getCurrentPoint());
		if (!currentPoint.areTheSameAs(controlPoint[0], controlPoint[1])
				|| !currentPoint.areTheSameAs(controlPoint[2], controlPoint[3])
				|| !currentPoint.areTheSameAs(controlPoint[4], controlPoint[5]))
			generalPath.curveTo(controlPoint[0], controlPoint[1],
					controlPoint[2], controlPoint[3], controlPoint[4],
					controlPoint[5]);
		return;
	}

	@Override
	public void doActionOnCloseTo(double[] controlPoint)
	{
		generalPath.closePath();
		return;
	}

	/**
	 * 取得去掉冗點的結果。
	 * 
	 * @return 去掉冗點的結果
	 */
	public GeneralPath getCurrnetPath()
	{
		return generalPath;
	}
}
