/*******************************************************************************
 * 著作權所有 (C) 民國102年 意傳文化科技
 * 開發者：薛丞宏
 * 網址：http://意傳.台灣
 * 字型提供：
 * 	全字庫授權說明
 * 		© 2012 中華民國行政院研究發展考核委員會。本字型檔採用創用CC「姓名標示－禁止改作」3.0臺灣版授權條款釋出。您可以在不變更字型內容之條件下，重製、散布及傳輸本字型檔之著作內容。惟應保留本字型名稱及著作權聲明。
 * 		http://www.cns11643.gov.tw/AIDB/copyright.do
 * 		
 * 	「中央研究院漢字部件檢字系統」2.65版釋出聲明
 * 		……，但於「漢字字型」部份，則考量其具有圖形著作的分殊特性，故另行採用「GNU自由文件授權條款1.2版本(GNU Free Documentation License 1.2，以下簡稱『GFDL1.2』)」，以及「創用CC 姓名標示-相同方式分享台灣授權條款2.5版(Creative Commons Attribution-Share Alike 2.5 Taiwan，以下簡稱為『CC-BY-SA 2.5 TW』)」兩種授權方式併行釋出。
 * 		http://cdp.sinica.edu.tw/cdphanzi/declare.htm
 * 		
 * 	吳守禮台語注音字型：
 * 		©2012從宜工作室：吳守禮、吳昭新，以CC01.0通用(CC01.0)方式在法律許可的範圍內，拋棄本著作依著作權法所享有之權利，並宣告將本著作貢獻至公眾領域。將台語注音標註轉化為本字型之工作，由吳昭新與莊德明共同完成。使用者可以複製、修改、發布或展示此作品，亦可進行商業利用，完全不需要經過另行許可。
 * 		http://xiaoxue.iis.sinica.edu.tw/download/WSL_TPS_Font.htm
 * 		
 * 本程式乃自由軟體，您必須遵照Affero通用公眾特許條款（Affero General Public License, AGPL)來修改和重新發佈這一程式，詳情請參閱條文。授權大略如下，若有歧異，以授權原文為主：
 * 	１．得使用、修改、複製並發佈此程式碼，且必須以通用公共授權發行；
 * 	２．任何以程式碼衍生的執行檔或網路服務，必須公開全部程式碼；
 * 	３．將此程式的原始碼當函式庫引用入商業軟體，需公開非關此函式庫的任何程式碼
 * 
 * 此開放原始碼、共享軟體或說明文件之使用或散佈不負擔保責任，並拒絕負擔因使用上述軟體或說明文件所致任何及一切賠償責任或損害。
 * 
 * 漢字組建緣起於本土文化推廣與傳承，非常歡迎各界推廣使用，但希望在使用之餘，能夠提供建議、錯誤回報或修補，回饋給這塊土地。
 * 
 * 謝謝您的使用與推廣～～
 ******************************************************************************/
package cc.筆觸;

import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Vector;

import cc.筆觸工具.CurveInformation;
import cc.筆觸工具.Point2DWithVector;
import cc.筆觸工具.ShapeAnalyst;

/**
 * 物件活字邊長外移筆劃加寬工具。此工具仍有問題，每個邊往外調，不顧及前後的邊，字會不平滑。而且尚有許多問題尚末解決。因為時間因素放棄，
 * 待日後系統完成後再行考慮。
 * 
 * @author Ihc
 */
@Deprecated
public class RadialStroke implements Stroke
{
	/** 要移動的距離 */
	private final double width;

	/**
	 * 建立邊長外移筆劃加寬工具
	 * 
	 * @param width
	 *            要移動的距離
	 */
	public RadialStroke(double width)
	{
		this.width = width;
	}

	@Override
	public Shape createStrokedShape(Shape shape)
	{
		return createStrokedShape(new Area(shape));// Area保證他的順逆時針
	}

	/**
	 * 加寬傳入物件。把每段路徑切開，去處理。
	 * 
	 * @param area
	 *            物件
	 * @return 加寬完的物件
	 */
	public Shape createStrokedShape(Area area)
	{
		Area sum = new Area();// TODO change name
		double[] controlPoint = new double[6];
		Vector<Point2DWithVector> apexMoveVector = new Vector<Point2DWithVector>();
		// SimplePolygon simplePolygon = new SimplePolygon();
		CurveInformation curveInformation = new CurveInformation();
		GeneralPath generalPath = new GeneralPath(GeneralPath.WIND_NON_ZERO);
		Point2D.Double startPoint = null;
		for (PathIterator pathIterator = area.getPathIterator(null); !pathIterator
				.isDone(); pathIterator.next())
		{
			int type = pathIterator.currentSegment(controlPoint);

			System.out.println("main=" + type + " " + controlPoint[0] + " "
					+ controlPoint[1] + " " + controlPoint[2] + " "
					+ controlPoint[3] + " " + controlPoint[4] + " "
					+ controlPoint[5]);
			Point2DWithVector currentPoint = null;
			if (generalPath.getCurrentPoint() != null)
				currentPoint = new Point2DWithVector(
						generalPath.getCurrentPoint());
			Point2DWithVector previous = new Point2DWithVector(), next = new Point2DWithVector();
			switch (type)
			{
			case PathIterator.SEG_MOVETO:
				// simplePolygon.addPoint(controlPoint[0], controlPoint[1]);
				apexMoveVector.add(new Point2DWithVector());
				generalPath.moveTo(controlPoint[0], controlPoint[1]);
				startPoint = new Point2D.Double(controlPoint[0],
						controlPoint[1]);
				break;
			case PathIterator.SEG_LINETO:
				// simplePolygon.addPoint(controlPoint[0], controlPoint[1]);
				// get斜率*2
				currentPoint.setLocation(generalPath.getCurrentPoint());
				if (currentPoint.areTheSameAs(controlPoint[0], controlPoint[1]))
					break;
				previous.setLocation(curveInformation.getLineSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], 0.0));
				next.setLocation(curveInformation.getLineSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], 1.0));
				System.out.println("pre=" + previous);
				apexMoveVector.lastElement().addLocation(previous);
				apexMoveVector.add(next);
				generalPath.lineTo(controlPoint[0], controlPoint[1]);
				break;
			case PathIterator.SEG_QUADTO:
				// simplePolygon.addPoint(controlPoint[0], controlPoint[1]);
				// simplePolygon.addPoint(controlPoint[2], controlPoint[3]);
				// get斜率*2
				currentPoint.setLocation(generalPath.getCurrentPoint());
				previous.setLocation(curveInformation.getQuadSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], controlPoint[2],
						controlPoint[3], 0.0));
				next.setLocation(curveInformation.getQuadSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], controlPoint[2],
						controlPoint[3], 1.0));
				System.out.println("pre=" + previous);
				System.out.println("next=" + next);
				apexMoveVector.lastElement().addLocation(previous);
				apexMoveVector.add(next);
				generalPath.quadTo(controlPoint[0], controlPoint[1],
						controlPoint[2], controlPoint[3]);
				break;
			case PathIterator.SEG_CUBICTO:
				// simplePolygon.addPoint(controlPoint[0], controlPoint[1]);
				// simplePolygon.addPoint(controlPoint[2], controlPoint[3]);
				// simplePolygon.addPoint(controlPoint[4], controlPoint[5]);
				// get斜率*2
				currentPoint.setLocation(generalPath.getCurrentPoint());
				previous.setLocation(curveInformation.getCubicSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], controlPoint[2],
						controlPoint[3], controlPoint[4], controlPoint[5], 0.0));
				next.setLocation(curveInformation.getCubicSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], controlPoint[2],
						controlPoint[3], controlPoint[4], controlPoint[5], 1.0));
				apexMoveVector.lastElement().addLocation(previous);
				apexMoveVector.add(next);
				generalPath.curveTo(controlPoint[0], controlPoint[1],
						controlPoint[2], controlPoint[3], controlPoint[4],
						controlPoint[5]);
				break;
			case PathIterator.SEG_CLOSE:
				if (startPoint.equals(generalPath.getCurrentPoint()))
				{
					apexMoveVector.lastElement().addLocation(
							apexMoveVector.firstElement());
					apexMoveVector.firstElement().setLocation(
							apexMoveVector.lastElement());
				}
				else
				{
					currentPoint.setLocation(generalPath.getCurrentPoint());
					previous.setLocation(curveInformation.getLineSlope(
							currentPoint.getX(), currentPoint.getY(),
							startPoint.getX(), startPoint.getY(), 0.0));
					next.setLocation(curveInformation.getLineSlope(
							currentPoint.getX(), currentPoint.getY(),
							startPoint.getX(), startPoint.getY(), 1.0));
					next.addLocation(apexMoveVector.firstElement());
					apexMoveVector.lastElement().addLocation(previous);
					apexMoveVector.add(next);
					apexMoveVector.firstElement().setLocation(next);
				}
				for (int i = 0; i < apexMoveVector.size(); ++i)
				{
					apexMoveVector.elementAt(i).setUnit();
					apexMoveVector.elementAt(i).rotateRightAngle();
				}
				generalPath.closePath();
				System.out.println("＠＠1");
				new ShapeAnalyst(generalPath);
				System.out.println("＠＠2");

				Area modify = new Area(getBold(generalPath, apexMoveVector));
				sum.add(modify);

				generalPath.reset();
				break;
			}
			if (apexMoveVector.size() > 3)
				System.out.println("apex[3]=" + apexMoveVector.elementAt(3));
		}
		return sum;
	}

	/**
	 * 給一段路徑，把筆劃加寬
	 * 
	 * @param generalPath
	 *            一段路徑
	 * @param apexMoveVector
	 *            每組頭尾控制點要移動的方向
	 * @return 加寬後的路徑
	 */
	private GeneralPath getBold(GeneralPath generalPath,
			Vector<Point2DWithVector> apexMoveVector)
	{
		for (int i = 0; i < apexMoveVector.size(); ++i)
		{
			apexMoveVector.elementAt(i).multiple(width);
			System.out.println("i=" + i + " "
					+ apexMoveVector.elementAt(i).getX() + " "
					+ apexMoveVector.elementAt(i).getY());
		}
		double[] controlPoint = new double[6];
		int index = 0;
		GeneralPath target = new GeneralPath();
		// target.moveTo(1, 2);
		// target.moveTo(2, 3);
		System.out.println("AA1");
		Point2DWithVector originCurrentPoint = new Point2DWithVector();
		Point2DWithVector targetCurrentPoint = new Point2DWithVector();
		for (PathIterator pathIterator = generalPath.getPathIterator(null); !pathIterator
				.isDone(); pathIterator.next())
		{
			// System.out.println("fun");
			int type = pathIterator.currentSegment(controlPoint);
			System.out.println("fun=" + type + " " + controlPoint[0] + " "
					+ controlPoint[1] + " " + controlPoint[2] + " "
					+ controlPoint[3] + " " + controlPoint[4] + " "
					+ controlPoint[5]);
			// System.out.println("P1");
			// ShapeAnalyst shapeAnalyst = new ShapeAnalyst(target);
			// System.out.println("P2");
			// target.getCurrentPoint();
//			Point2DWithVector previous = new Point2DWithVector(), next = new Point2DWithVector();
			Point2DWithVector targetDifference;
			Point2DWithVector originDifference;
			switch (type)
			{
			case PathIterator.SEG_MOVETO:
				Point2DWithVector move = new Point2DWithVector(
						apexMoveVector.elementAt(index));
				move.addLocation(controlPoint[0], controlPoint[1]);
				target.moveTo((float) move.getX(), (float) move.getY());
				System.out.println("moveto=" + move.getX() + ' ' + move.getY());
				originCurrentPoint
						.setLocation(controlPoint[0], controlPoint[1]);
				targetCurrentPoint.setLocation(move);
				break;
			case PathIterator.SEG_LINETO:
				Point2DWithVector line = new Point2DWithVector(
						apexMoveVector.elementAt(index));
				line.addLocation(controlPoint[0], controlPoint[1]);
				System.out.println("lineto=" + line.getX() + " " + line.getY());
				target.lineTo(line.getX(), line.getY());
				originCurrentPoint
						.setLocation(controlPoint[0], controlPoint[1]);
				targetCurrentPoint.setLocation(line);
				break;
			case PathIterator.SEG_QUADTO:
				Point2DWithVector quad = new Point2DWithVector(
						apexMoveVector.elementAt(index));
				quad.addLocation(controlPoint[2], controlPoint[3]);
				originDifference = new Point2DWithVector(controlPoint[2],
						controlPoint[3]);
				originDifference.subLocation(originCurrentPoint);
				targetDifference = new Point2DWithVector(quad);
				targetDifference.subLocation(targetCurrentPoint);
				Point2DWithVector q0 = new Point2DWithVector(controlPoint[0],
						controlPoint[1]);
				Point2DWithVector q0Difference = new Point2DWithVector(q0);
				q0Difference.subLocation(originCurrentPoint);

				System.out.println("q0=" + q0);
				System.out.println("q0Difference=" + q0Difference);
				System.out.println("targetDifference=" + targetDifference);
				System.out.println("originDifference=" + originDifference);
				q0Difference.multipleByPolarSystem(targetDifference);
				// System.out.println("new1 q0Difference="+q0Difference);
				q0Difference.divideByPolarSystem(originDifference);

				System.out.println("new2 q0Difference=" + q0Difference);
				q0.setLocation(originCurrentPoint);
				q0.addLocation(q0Difference);

				System.out.println("quadto=" + q0.getX() + " " + q0.getY()
						+ " " + quad.getX() + " " + quad.getY());
				//
				q0.setLocation(apexMoveVector.elementAt(index - 1));
				q0.addLocation(apexMoveVector.elementAt(index));
				q0.multiple(0.5);
				q0.addLocation(controlPoint[0], controlPoint[1]);
				//
				target.quadTo(q0.getX(), q0.getY(), quad.getX(), quad.getY());

				originCurrentPoint
						.setLocation(controlPoint[2], controlPoint[3]);
				targetCurrentPoint.setLocation(quad);
				break;
			case PathIterator.SEG_CUBICTO:
				Point2DWithVector cubic = new Point2DWithVector(
						apexMoveVector.elementAt(index));
				cubic.addLocation(controlPoint[4], controlPoint[5]);
				originDifference = new Point2DWithVector(controlPoint[4],
						controlPoint[5]);
				originDifference.subLocation(originCurrentPoint);
				targetDifference = new Point2DWithVector(cubic);
				targetDifference.subLocation(targetCurrentPoint);
				Point2DWithVector c0 = new Point2DWithVector(controlPoint[0],
						controlPoint[1]);
				Point2DWithVector c0Difference = new Point2DWithVector(c0);
				Point2DWithVector c1 = new Point2DWithVector(controlPoint[2],
						controlPoint[3]);
				Point2DWithVector c1Difference = new Point2DWithVector(c1);
				c0Difference.subLocation(originCurrentPoint);
				c1Difference.subLocation(originCurrentPoint);

				c0Difference.multipleByPolarSystem(targetDifference);
				c0Difference.divideByPolarSystem(originDifference);
				c1Difference.multipleByPolarSystem(targetDifference);
				c1Difference.divideByPolarSystem(originDifference);
				c0.setLocation(originCurrentPoint);
				c0.addLocation(c0Difference);
				c1.setLocation(originCurrentPoint);
				c1.addLocation(c1Difference);
				while (c0.equals(c0))
					System.err.println("XDDDDDDD");

				target.curveTo(c0.getX(), c0.getY(), c1.getX(), c1.getY(),
						cubic.getX(), cubic.getY());

				originCurrentPoint
						.setLocation(controlPoint[4], controlPoint[5]);
				targetCurrentPoint.setLocation(cubic);
				break;
			case PathIterator.SEG_CLOSE:
				target.closePath();
				break;
			}
			index++;
		}
		System.out.println("AA2");
		// System.out.println("M1");
		// ShapeAnalyst shapeAnalyst = new ShapeAnalyst(target);
		// System.out.println("M2");
		return target;
	}
}
