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
package cc.stroke;

import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

import cc.stroketool.PathAction;
import cc.stroketool.Point2DWithVector;

/**
 * 圓滑筆劃的循訪反應動作介面。
 * 
 * @author Ihc
 */
public class UnsharpenAction implements PathAction
{
	/** 圓滑後結果 */
	private GeneralPath generalPath;
	/** 前一個線段 */
	private SinglePath previousPath;

	/**
	 * 建立圓滑筆劃的循訪反應動作介面。
	 * 
	 * @param rule
	 *            要移動的距離
	 */
	public UnsharpenAction(int rule)
	{
		generalPath = new GeneralPath(rule);
		previousPath = new SinglePath();
	}

	@Override
	public void doActionOnMoveTo(double[] controlPoint)
	{
		// generalPath.moveTo(controlPoint[0], controlPoint[1]);
		previousPath.setPath(PathIterator.SEG_MOVETO, new Point2DWithVector(),
				controlPoint);
		return;
	}

	@Override
	public void doActionOnLineTo(double[] controlPoint)
	{
		if (previousPath.getLastPoint() != null)
		{
			System.out.println("!");
			Point2DWithVector currentPoint = previousPath.getLastPoint();
			if (currentPoint.areTheSameAs(controlPoint[0], controlPoint[1]))
			{
				System.out.println("!!");
				return;
			}
		}
		SinglePath currentPath = new SinglePath(PathIterator.SEG_LINETO,
				previousPath.getLastPoint(), controlPoint);
		checkAngleAndAddPath(currentPath);
		return;
	}

	@Override
	public void doActionOnQuadTo(double[] controlPoint)
	{
		if (previousPath.getLastPoint() != null)
		{
			Point2DWithVector currentPoint = previousPath.getLastPoint();
			if (currentPoint.areTheSameAs(controlPoint[0], controlPoint[1])
					&& currentPoint.areTheSameAs(controlPoint[2],
							controlPoint[3]))
				return;
		}
		SinglePath currentPath = new SinglePath(PathIterator.SEG_QUADTO,
				previousPath.getLastPoint(), controlPoint);
		checkAngleAndAddPath(currentPath);
		return;
	}

	@Override
	public void doActionOnCubicTo(double[] controlPoint)
	{
		if (previousPath.getLastPoint() != null)
		{
			Point2DWithVector currentPoint = previousPath.getLastPoint();
			if (currentPoint.areTheSameAs(controlPoint[0], controlPoint[1])
					&& currentPoint.areTheSameAs(controlPoint[2],
							controlPoint[3])
					&& currentPoint.areTheSameAs(controlPoint[4],
							controlPoint[5]))
				return;
		}
		SinglePath currentPath = new SinglePath(PathIterator.SEG_CUBICTO,
				previousPath.getLastPoint(), controlPoint);
		checkAngleAndAddPath(currentPath);
		return;
	}

	@Override
	public void doActionOnCloseTo(double[] controlPoint)
	{
		previousPath.addTo(generalPath);
		previousPath.setPath(PathIterator.SEG_CLOSE);
		generalPath.closePath();
		return;
	}

	/**
	 * 取得去掉冗點的結果
	 * 
	 * @return 去掉冗點的結果
	 */
	public GeneralPath getCurrnetPath()
	{
		return generalPath;
	}

	/**
	 * 檢查是否兩線段角度，看情況加入圓角。
	 * 
	 * @param currentPath
	 *            新進來的線段
	 */
	private void checkAngleAndAddPath(SinglePath currentPath)
	{
		Point2DWithVector previousSlope = previousPath.getSlope(1.0);
		Point2DWithVector currentSlope = currentPath.getSlope(0.0);
		System.out.println("@@ " + previousSlope + " " + currentSlope);
		if (previousSlope != null)
		{
			previousSlope.setUnit();
			currentSlope.setUnit();
			if (previousSlope.innerProduct(currentSlope) < 0.9) // TODO 人工參數
			{
				System.out.println("@@");
				Point2DWithVector apex = previousPath.getLastPoint();
				previousPath.cutTail();
				Point2DWithVector tail = currentPath.cutHead();
				previousPath.addTo(generalPath);
				generalPath.quadTo(apex.getX(), apex.getY(), tail.getX(),
						tail.getY());
				// previousSlop
			}
			else
				previousPath.addTo(generalPath);
		}
		else
			previousPath.addTo(generalPath);
		previousPath = currentPath;
		return;
	}
}
