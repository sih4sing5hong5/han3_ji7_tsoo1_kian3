package cc.moveable_type.rectangular_area;

public interface PathAction
{
	public void doActionOnMoveTo(double[] controlPoint);

	public void doActionOnLineTo(double[] controlPoint);

	public void doActionOnQuadTo(double[] controlPoint);

	public void doActionOnCubicTo(double[] controlPoint);

	public void doActionOnCloseTo(double[] controlPoint);
}
