package cc.core;

/**
 * 若用戶已經展開，就免做矣。
 * 
 * @author Ihc
 * 
 */
public class 展開式免查詢 implements 展開式查詢工具
{
	@Override
	public String 查詢展開式(String 待查文字)
	{
		return 待查文字;
	}
}
