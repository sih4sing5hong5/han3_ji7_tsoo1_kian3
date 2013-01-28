package cc.tool.database;

public class 資料庫命令字串
{
	private StringBuilder 命令;

	public 資料庫命令字串()
	{
		命令 = new StringBuilder();
	}

	public 資料庫命令字串(CharSequence 字元資料)
	{
		命令 = new StringBuilder(字元資料);
	}

	public 資料庫命令字串 加命令(char 字元)
	{
		命令.append(字元);
		return this;
	}

	public 資料庫命令字串 加命令(CharSequence 字元資料)
	{
		命令.append(字元資料);
		return this;
	}

	public 資料庫命令字串 加變數(CharSequence 字元資料)
	{
		if (字元資料 != null)
			命令.append('\'');
		命令.append(字元資料);
		if (字元資料 != null)
			命令.append('\'');
		return this;
	}

	@Override
	public String toString()
	{
		return 命令.toString();
	}
}
