package cc.stroketool;

/**
 * 路徑循訪反應動作介面，配合<code>PathTravel</code>使用，設定當遇到各式的線段方程式，所要執行的動作。
 * 
 * @author Ihc
 */
public interface PathAction
{
	/**
	 * 移動時要做的事。
	 * @param controlPoint 方程式控制點
	 */
	public void doActionOnMoveTo(double[] controlPoint);

	/**
	 * 遇到直線時要做的事。
	 * @param controlPoint 方程式控制點
	 */
	public void doActionOnLineTo(double[] controlPoint);

	/**
	 * 遇到二維曲線時要做的事。
	 * @param controlPoint 方程式控制點
	 */
	public void doActionOnQuadTo(double[] controlPoint);

	/**
	 * 遇到三維曲線時要做的事。
	 * @param controlPoint 方程式控制點
	 */
	public void doActionOnCubicTo(double[] controlPoint);

	/**
	 * 路徑完成段落後要做的事。
	 * @param controlPoint 方程式控制點
	 */
	public void doActionOnCloseTo(double[] controlPoint);
}
