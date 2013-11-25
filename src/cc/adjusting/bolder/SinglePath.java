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
package cc.adjusting.bolder;

import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import cc.moveable_type.rectangular_area.CurveInformation;
import cc.moveable_type.rectangular_area.Point2DWithVector;

/**
 * 路徑線段。並包含相關處理函式。
 * 
 * @author Ihc
 */
public class SinglePath
{
	/** 線段型態 */
	private int type;
	/** 線段起始點 */
	private Point2DWithVector currentPoint;
	/** 線段控制點 */
	private double[] controlPoint;

	/** 建立線段物件 */
	public SinglePath()
	{
		type = PathIterator.SEG_CLOSE;
		currentPoint = new Point2DWithVector();
		controlPoint = new double[6];
	}

	/**
	 * 建立線段物件。
	 * 
	 * @param type
	 *            線段型態
	 * @param currentPoint
	 *            線段起始點
	 * @param controlPoint
	 *            線段控制點
	 */
	public SinglePath(int type, Point2D currentPoint, double[] controlPoint)
	{
		this.controlPoint = new double[6];
		setPath(type, currentPoint, controlPoint);
	}

	/**
	 * 把線段加到路徑中。
	 * 
	 * @param generalPath
	 *            目標路徑
	 */
	public void addTo(GeneralPath generalPath)
	{
		switch (type)
		{
		case PathIterator.SEG_MOVETO:
			generalPath.moveTo(controlPoint[0], controlPoint[1]);
			break;
		case PathIterator.SEG_LINETO:
			generalPath.lineTo(controlPoint[0], controlPoint[1]);
			break;
		case PathIterator.SEG_QUADTO:
			generalPath.quadTo(controlPoint[0], controlPoint[1],
					controlPoint[2], controlPoint[3]);
			break;
		case PathIterator.SEG_CUBICTO:
			generalPath.curveTo(controlPoint[0], controlPoint[1],
					controlPoint[2], controlPoint[3], controlPoint[4],
					controlPoint[5]);
			break;
		case PathIterator.SEG_CLOSE:
			generalPath.closePath();
			break;
		}
	}

	/**
	 * 將線段前部份切掉一小部份以利路徑圓滑工具使用。
	 * 
	 * @return 新的啟始點
	 */
	public Point2DWithVector cutHead()
	{
		reverse();
		Point2DWithVector point2dWithVector = cutTail();
		reverse();
		return point2dWithVector;
	}

	/**
	 * 將線段後部份切掉一小部份以利路徑圓滑工具使用。
	 * 
	 * @return 新的結束點
	 */
	public Point2DWithVector cutTail()
	{
		System.out.println("cut~~");
		Point2DWithVector newEnd = null;
		Point2DWithVector tailSlope = getSlope(1.0);
		if (tailSlope != null)
		{
			double newT = 1 - getLengthRatio()/* / tailSlope.getLength() */;
			// TODO 不一定《1

			Point2DWithVector headSlope = getSlope(0.0);
			System.out.println("newT=" + newT);
			CurveInformation curveInformation = new CurveInformation();
			switch (type)
			{
			case PathIterator.SEG_MOVETO:
				break;
			case PathIterator.SEG_LINETO:
				newEnd = curveInformation.getLinePoint(currentPoint.getX(),
						currentPoint.getY(), controlPoint[0], controlPoint[1],
						newT);
				controlPoint[0] = newEnd.getX();
				controlPoint[1] = newEnd.getY();
				break;
			case PathIterator.SEG_QUADTO:
				newEnd = curveInformation.getQuadPoint(currentPoint.getX(),
						currentPoint.getY(), controlPoint[0], controlPoint[1],
						controlPoint[2], controlPoint[3], newT);
				controlPoint[2] = newEnd.getX();
				controlPoint[3] = newEnd.getY();
				// TODO
				// controlPoint[0]＝controlPoint[2]－A*newSlope.x=currentPoint+B*headSlope.x
				// controlPoint[1]＝controlPoint[3]－A*newSlope.y=currentPoint+B*headSlope.y

				// controlPoint[2]-currentPoint=A*newSlope.x+B*headSlope.x
				// controlPoint[3]-currentPoint=A*newSlope.y+B*headSlope.y

				// controlPoint[2]-currentPoint=A*newSlope.x+B*headSlope.x X
				// headSlope.y
				// controlPoint[3]-currentPoint=A*newSlope.y+B*headSlope.y X
				// headSlope.x

				Point2DWithVector newSlope = curveInformation.getQuadSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], controlPoint[2],
						controlPoint[3], newT);
				double A = ((controlPoint[2] - currentPoint.getX()) * headSlope
						.getY())
						- (controlPoint[3] - currentPoint.getY())
						* headSlope.getX();
				A /= newSlope.getX() * headSlope.getY() - newSlope.getY()
						* headSlope.getX(); // TODO DIVIDE 0
				controlPoint[0] = controlPoint[2] - A * newSlope.getX();
				controlPoint[1] = controlPoint[3] - A * newSlope.getY();
				break;
			case PathIterator.SEG_CUBICTO:
				newEnd = curveInformation.getCubicPoint(currentPoint.getX(),
						currentPoint.getY(), controlPoint[0], controlPoint[1],
						controlPoint[2], controlPoint[3], controlPoint[4],
						controlPoint[5], newT);
				// TODO
				controlPoint[4] = newEnd.getX();
				controlPoint[5] = newEnd.getY();
				break;
			case PathIterator.SEG_CLOSE:
				break;
			}

		}
		return newEnd;
	}

	/** 將線段前後點順序顛倒。 */
	private void reverse()
	{
		Point2DWithVector tmp = currentPoint;
		switch (type)
		{
		case PathIterator.SEG_MOVETO:
			break;
		case PathIterator.SEG_LINETO:
			currentPoint = new Point2DWithVector(controlPoint[0],
					controlPoint[1]);
			controlPoint[0] = tmp.getX();
			controlPoint[1] = tmp.getY();
			break;
		case PathIterator.SEG_QUADTO:
			currentPoint = new Point2DWithVector(controlPoint[2],
					controlPoint[3]);
			controlPoint[2] = tmp.getX();
			controlPoint[3] = tmp.getY();
			break;
		case PathIterator.SEG_CUBICTO:
			currentPoint = new Point2DWithVector(controlPoint[4],
					controlPoint[5]);
			controlPoint[4] = tmp.getX();
			controlPoint[5] = tmp.getY();
			double t;
			t = controlPoint[0];
			controlPoint[0] = controlPoint[2];
			controlPoint[2] = t;
			t = controlPoint[1];
			controlPoint[1] = controlPoint[3];
			controlPoint[3] = t;
			break;
		case PathIterator.SEG_CLOSE:
			break;
		}
		return;
	}

	/**
	 * 取得線段型態。
	 * 
	 * @return 線段型態
	 */
	int getType()
	{
		return type;
	}

	/**
	 * 取得線段控制點。
	 * 
	 * @return 線段控制點
	 */
	double[] getControlPoint()
	{
		return controlPoint;
	}

	/**
	 * 取得線段最後一個控制點。
	 * 
	 * @return 最後一個控制點
	 */
	Point2DWithVector getLastPoint()
	{
		Point2DWithVector point2dWithVector = null;
		switch (type)
		{
		case PathIterator.SEG_MOVETO:
			point2dWithVector = new Point2DWithVector(controlPoint[0],
					controlPoint[1]);
			break;
		case PathIterator.SEG_LINETO:
			point2dWithVector = new Point2DWithVector(controlPoint[0],
					controlPoint[1]);
			break;
		case PathIterator.SEG_QUADTO:
			point2dWithVector = new Point2DWithVector(controlPoint[2],
					controlPoint[3]);
			break;
		case PathIterator.SEG_CUBICTO:
			point2dWithVector = new Point2DWithVector(controlPoint[4],
					controlPoint[5]);
			break;
		case PathIterator.SEG_CLOSE:
			break;
		}
		return point2dWithVector;
	}

	/**
	 * 取得線段該點斜率。
	 * 
	 * @param t
	 *            參數
	 * @return 線段斜率
	 */
	public Point2DWithVector getSlope(double t)
	{
		Point2DWithVector point2dWithVector = null;
		CurveInformation curveInformation = new CurveInformation();
		switch (type)
		{
		case PathIterator.SEG_MOVETO:
			break;
		case PathIterator.SEG_LINETO:
			point2dWithVector = curveInformation.getLineSlope(
					currentPoint.getX(), currentPoint.getY(), controlPoint[0],
					controlPoint[1], t);
			break;
		case PathIterator.SEG_QUADTO:
			point2dWithVector = curveInformation.getQuadSlope(
					currentPoint.getX(), currentPoint.getY(), controlPoint[0],
					controlPoint[1], controlPoint[2], controlPoint[3], t);
			break;
		case PathIterator.SEG_CUBICTO:
			point2dWithVector = curveInformation.getCubicSlope(
					currentPoint.getX(), currentPoint.getY(), controlPoint[0],
					controlPoint[1], controlPoint[2], controlPoint[3],
					controlPoint[4], controlPoint[5], t);
			break;
		case PathIterator.SEG_CLOSE:
			break;
		}
		return point2dWithVector;
	}

	/**
	 * 設定線段屬性。
	 * 
	 * @param type
	 *            線段控制點
	 * @param currentPoint
	 *            線段啟始點
	 * @param controlPoint
	 *            線段控制點
	 */
	void setPath(int type, Point2D currentPoint, double[] controlPoint)
	{
		this.type = type;
		this.currentPoint = new Point2DWithVector(currentPoint);
		if (controlPoint != null)
			for (int i = 0; i < 6; ++i)
				this.controlPoint[i] = controlPoint[i];
		return;
	}

	/**
	 * 設定線段型態。
	 * 
	 * @param type
	 *            線段型態
	 */
	void setPath(int type)
	{
		this.type = type;
		return;
	}

	/**
	 * 取得圓滑長度比例。
	 * 
	 * @return 圓滑長度比例
	 */
	public double getLengthRatio()
	{
		return 1e-1;// TODO 參數
	}
}
