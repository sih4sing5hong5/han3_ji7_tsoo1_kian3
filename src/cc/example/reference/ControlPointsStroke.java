package cc.example.reference;

/*
 * Copyright (c) 2000 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 2nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book (recommended),
 * visit http://www.davidflanagan.com/javaexamples2.
 */

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

/**
 * This Stroke implementation strokes the shape using a thin line, and also
 * displays the end points and Bezier curve control points of all the line and
 * curve segments that make up the shape. The radius argument to the constructor
 * specifies the size of the control point markers. Note the use of PathIterator
 * to break the shape down into its segments, and of GeneralPath to build up the
 * stroked shape.
 */

public class ControlPointsStroke implements Stroke {
	/** 控制點大小 */
	float radius; // how big the control point markers should be

	/**
	 * 建立顯示控制點的筆劃
	 * 
	 * @param radius
	 *            控制點大小
	 */
	public ControlPointsStroke(float radius) {
		this.radius = radius;
	}

	@Override
	public Shape createStrokedShape(Shape shape) {
		// Start off by stroking the shape with a thin line. Store the
		// resulting shape in a GeneralPath object so we can add to it.
		GeneralPath strokedShape = new GeneralPath(
				new BasicStroke(1.5f).createStrokedShape(shape));

		// Use a PathIterator object to iterate through each of the line and
		// curve segments of the shape. For each one, mark the endpoint and
		// control points (if any) by adding a rectangle to the GeneralPath
		float[] coords = new float[6];
		int cnt = 0;
		for (PathIterator i = shape.getPathIterator(null); !i.isDone(); i
				.next()) {
			int type = i.currentSegment(coords);
			switch (type) {
			case PathIterator.SEG_CUBICTO:
				markPoint(strokedShape, coords[4], coords[5]); // falls through
			case PathIterator.SEG_QUADTO:
				markPoint(strokedShape, coords[2], coords[3]); // falls through
			case PathIterator.SEG_MOVETO:
			case PathIterator.SEG_LINETO:
				markPoint(strokedShape, coords[0], coords[1]); // falls through
			case PathIterator.SEG_CLOSE:
				break;
			}
			++cnt;
		}
		System.out.println(cnt);
		return strokedShape;
	}

	/**
	 * 標上控制點 原註解：Add a small square centered at (x,y) to the specified path
	 * 
	 * @param path
	 *            要加上的路徑
	 * @param x
	 *            水平位置
	 * @param y
	 *            垂直位置
	 */

	void markPoint(GeneralPath path, float x, float y) {
		path.moveTo(x - radius, y - radius); // Begin a new sub-path
		path.lineTo(x + radius, y - radius); // Add a line segment to it
		path.lineTo(x + radius, y + radius); // Add a second line segment
		path.lineTo(x - radius, y + radius); // And a third
		path.closePath(); // Go back to last moveTo position
	}
}