package cc.core;

/**
 * 部件型態，閣有包含樹狀結構下底的組字式。
 * 
 * @author Ihc
 */
public interface 組字式部件
{
	/**
	 * 提到這个部件下跤的組字式
	 * 
	 * @return 這个物件下跤的組字式
	 */
	public String 提到組字式();

	/**
	 * 設定這个部件下跤的組字式
	 * 
	 * @param 組字式
	 *            新的組字式
	 */
	void 設定組字式(String 組字式);

}
