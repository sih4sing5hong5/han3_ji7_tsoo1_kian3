package cc.adjusting.bolder;

import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Vector;

import cc.moveable_type.rectangular_area.CurveInformation;
import cc.moveable_type.rectangular_area.Point2DWithVector;
import cc.moveable_type.rectangular_area.ShapeAnalyst;
import cc.moveable_type.rectangular_area.SimplePolygon;

public class SimplifyStroke implements Stroke
{
	private final double width;

	public SimplifyStroke(double width)
	{
		this.width = width;
	}

	@Override
	public Shape createStrokedShape(Shape shape)
	{
		return createStrokedShape(new GeneralPath(shape));// Area保證他的順逆時針
	}

	public Shape createStrokedShape(GeneralPath generalPath)
	{
		SimplifyAction simplifyAction = new SimplifyAction(
				generalPath.getWindingRule());
		PathTravel pathTravel = new PathTravel(simplifyAction);
		pathTravel.travelOn(generalPath);
		new ShapeAnalyst(simplifyAction.getCurrnetPath());
		return simplifyAction.getCurrnetPath();
	}

}