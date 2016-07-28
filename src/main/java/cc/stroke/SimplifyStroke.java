package cc.stroke;

import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;

import cc.stroketool.PathTravel;
import cc.stroketool.ShapeAnalyst;

/**
 * 去掉冗點筆劃工具。把部件中幾乎在同一位置的控制點刪除，解決<code>BasicStroke</code> 的缺角問題。但可能還是會衍生出其他問題，待解。
 * 
 * @author Ihc
 * 
 */
public class SimplifyStroke implements Stroke
{
	@Override
	public Shape createStrokedShape(Shape shape)
	{
		return createStrokedShape(new GeneralPath(shape));// Area保證他的順逆時針
	}

	/**
	 * 去掉該路徑的冗點。
	 * 
	 * @param generalPath
	 *            路徑物件
	 * @return 去掉冗點的結果
	 */
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
