package cc.stroketool;

/**
 * 把每個控制點拿出來看的路徑循訪反應動作介面，搭配上<code>ControlPointReaction</code>就可以方便的依序得到每個控制點的位置。
 * 
 * @author Ihc
 */
public class ControlPointVisit implements PathAction
{
	/** 讀取每個控制點要做的反應 */
	private ControlPointReaction 反應動作;

	/**
	 * 建立控制點循訪物件。
	 * 
	 * @param 反應動作
	 *            讀取每個控制點要做的反應
	 */
	public ControlPointVisit(ControlPointReaction 反應動作)
	{
		this.反應動作 = 反應動作;
	}

	@Override
	public void doActionOnMoveTo(double[] controlPoint)
	{
		反應動作.新控制點(new Point2DWithVector(controlPoint[0], controlPoint[1]));
		return;
	}

	@Override
	public void doActionOnLineTo(double[] controlPoint)
	{
		反應動作.新控制點(new Point2DWithVector(controlPoint[0], controlPoint[1]));
		return;
	}

	@Override
	public void doActionOnQuadTo(double[] controlPoint)
	{
		反應動作.新控制點(new Point2DWithVector(controlPoint[0], controlPoint[1]));
		反應動作.新控制點(new Point2DWithVector(controlPoint[2], controlPoint[3]));
		return;
	}

	@Override
	public void doActionOnCubicTo(double[] controlPoint)
	{
		反應動作.新控制點(new Point2DWithVector(controlPoint[0], controlPoint[1]));
		反應動作.新控制點(new Point2DWithVector(controlPoint[2], controlPoint[3]));
		反應動作.新控制點(new Point2DWithVector(controlPoint[4], controlPoint[5]));
		return;
	}

	@Override
	public void doActionOnCloseTo(double[] controlPoint)
	{
		return;
	}

}
