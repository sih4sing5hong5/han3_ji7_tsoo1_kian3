package cc.core;

/**
 * 予代換工具使用的查詢介面。
 * 
 * @author Ihc
 */
public interface 異寫式查詢工具
{
	/**
	 * 查異寫式伊應該對應到的組字式
	 * 
	 * @param 組字式
	 *            愛查的異寫式
	 * @param 異寫式編號
	 *            異寫式伊佇定義表的編號
	 * @return 查著的結果。若無，回傳<code>null</code>
	 */
	String 查異寫組字式(String 組字式, int 異寫式編號);
}
