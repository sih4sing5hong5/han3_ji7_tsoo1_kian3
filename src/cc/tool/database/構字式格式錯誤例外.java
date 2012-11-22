package cc.tool.database;

/**
 * 若構字式有錯，則丟出此例外。
 * 
 * @author Ihc
 */
public class 構字式格式錯誤例外 extends Exception
{
	/** 序列化編號 */
	private static final long serialVersionUID = -1824136338718268279L;

	/** 建構函式。 */
	public 構字式格式錯誤例外()
	{
		super();
	}

	/**
	 * 帶著錯誤訊息的建構函式。
	 * 
	 * @param 錯誤訊息
	 *            發生此例外的原因
	 */
	public 構字式格式錯誤例外(String 錯誤訊息)
	{
		super(錯誤訊息);
	}
}
