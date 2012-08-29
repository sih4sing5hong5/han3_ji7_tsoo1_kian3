package cc.moveable_type.rectangular_area;

/**
 * 設定當遇到<code>GeneralPath</code>裡各式的線段方程式，所要執行的動作，配合<code>控制點循訪</code>及
 * <code>PathTravel</code>使用。
 * 
 * @author Ihc
 */
public interface 控制點反應動作
{
	/**
	 * 遇到控制點所要執行的動作。
	 * 
	 * @param 控制點
	 *            遇到的控制點
	 */
	public void 新控制點(Point2DWithVector 控制點);
}
