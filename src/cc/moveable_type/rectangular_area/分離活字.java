package cc.moveable_type.rectangular_area;

import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class 分離活字 // implements 活字單元
{
	private Vector<平面幾何> 字;
	/** 這馬的範圍，是為著閃避「一字問題」 */
	private Rectangle2D.Double 這馬;
	/** 目標範圍，上尾印的時陣愛看印偌大 */
	private Rectangle2D.Double 目標;
}
