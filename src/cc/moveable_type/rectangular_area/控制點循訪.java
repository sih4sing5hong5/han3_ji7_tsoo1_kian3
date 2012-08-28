package cc.moveable_type.rectangular_area;

public class 控制點循訪 implements PathAction
{
	private 控制點反應動作 反應動作;

	public 控制點循訪(控制點反應動作 反應動作)
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
