package cc.core;

/**
 * 部件型態，閣有包含樹狀結構下底的組字式。
 * 
 * @author Ihc
 */
public interface 組字式部件
{
	/**
	 * 提到這个部件下跤的組字式。
	 * 
	 * @return 這个物件下跤的組字式
	 */
	public String 提到組字式();

	/**
	 * 設定這个部件下跤的組字式。
	 * 
	 * @param 組字式
	 *            新的組字式
	 */
	void 設定組字式(String 組字式);

	/**
	 * 建立規的樹狀結構的組字式。
	 * 
	 * @param 組字式建立工具
	 *            所用的組字式建立工具
	 * @return 做好的組字式
	 */
	public String 建立組字式(組字式部件組字式建立工具 組字式建立工具);
}
