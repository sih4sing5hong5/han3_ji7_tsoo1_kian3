package cc.core;

/**
 * 加下組字式的文部件。
 * 
 * @author Ihc
 */
public class 組字式文部件 extends ChineseCharacterWen implements 組字式部件
{
	/** 這个文部件下跤的組字式 */
	private String 組字式;

	/**
	 * 初使化一个新的文部件。
	 * 
	 * @param 面頂彼个字部件
	 *            樹狀結構面頂彼个字部件
	 * @param 控制碼
	 *            這个文部件字的統一碼控制碼
	 */
	組字式文部件(ChineseCharacterTzu 面頂彼个字部件, int 控制碼)
	{
		super(面頂彼个字部件, 控制碼);
	}

	@Override
	public String 提到組字式()
	{
		return 組字式;
	}

	@Override
	public void 設定組字式(String 組字式)
	{
		this.組字式 = 組字式;
		return;
	}
}
