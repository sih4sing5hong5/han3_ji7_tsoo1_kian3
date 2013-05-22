package cc.adjusting.piece;

import java.util.Vector;

import cc.moveable_type.rectangular_area.RectangularArea;

class 注音符號分類
{
	protected 注音符號類別 目前類別;
	protected Vector<RectangularArea> 輕聲;
	protected Vector<RectangularArea> 聲韻號;
	protected Vector<RectangularArea> 調號;

	注音符號分類()
	{
		目前類別 = 注音符號類別.輕聲;
		輕聲 = new Vector<RectangularArea>();
		聲韻號 = new Vector<RectangularArea>();
		調號 = new Vector<RectangularArea>();
	}
}
