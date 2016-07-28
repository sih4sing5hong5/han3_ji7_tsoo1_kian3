package cc.stroke;

import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;

import cc.stroketool.PathTravel;

/**
 * 圓滑筆劃工具。把銳角或是較尖的角變成二維曲線，減少基本筆劃工具（<code>BasicStroke</code>
 * ）的問題。測試結果仍有不明問題。看二條相鄰筆劃的角度，決定是否改成曲線。
 * 
 * @author Ihc
 */
public class UnsharpenStroke implements Stroke
{
	@Override
	public Shape createStrokedShape(Shape shape)
	{
		return createStrokedShape(new GeneralPath(shape));
	}

	/**
	 * 圓滑該路徑的頂點。
	 * 
	 * @param generalPath
	 *            路徑物件
	 * @return 圓滑後的結果
	 */
	public Shape createStrokedShape(GeneralPath generalPath)
	{
		UnsharpenAction unsharpenAction = new UnsharpenAction(
				generalPath.getWindingRule());
		PathTravel pathTravel = new PathTravel(unsharpenAction);
		pathTravel.travelOn(generalPath);
		return unsharpenAction.getCurrnetPath();
	}
}
