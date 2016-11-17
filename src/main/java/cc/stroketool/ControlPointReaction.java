package cc.stroketool;

/**
 * 設定當遇到<code>GeneralPath</code>裡各式的線段方程式，所要執行的動作，配合<code>ControlPointVisit</code>及
 * <code>PathTravel</code>使用。
 * 
 * @author Ihc
 */
public interface ControlPointReaction
{
	/**
	 * 遇到控制點所要執行的動作。
	 * 
	 * @param 控制點
	 *            遇到的控制點
	 */
	public void 新控制點(Point2DWithVector 控制點);
}
