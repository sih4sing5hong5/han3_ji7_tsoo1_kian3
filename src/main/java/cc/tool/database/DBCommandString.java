package cc.tool.database;

/**
 * 方便產生資料庫指令的型態。對變數有特別處理，<code>null</code>嘛袂毋著。
 * 
 * @author Ihc
 */
public class DBCommandString
{
	/** 目前的命令 */
	private StringBuilder 命令;

	/** 建立一个空的命令字串。 */
	public DBCommandString()
	{
		命令 = new StringBuilder();
	}

	/**
	 * 建立一个命令字串，順紲設定頭前固定的命令
	 * 
	 * @param 字元資料
	 *            愛設定的字串
	 */
	public DBCommandString(CharSequence 字元資料)
	{
		命令 = new StringBuilder(字元資料);
	}

	/**
	 * 增加命令一个字元。
	 * 
	 * @param 字元
	 *            愛增加的字元
	 * @return 目前的命令字串
	 */
	public DBCommandString 加命令(char 字元)
	{
		命令.append(字元);
		return this;
	}

	/**
	 * 增加命令一寡字元。
	 * 
	 * @param 字元資料
	 *            愛增加的字串
	 * @return 目前的命令字串
	 */
	public DBCommandString 加命令(CharSequence 字元資料)
	{
		命令.append(字元資料);
		return this;
	}

	/**
	 * 增加一个變數。會自動加引號(<code>'</code>)。若是<code>null</code>，命令會真實用<code>null</code>
	 * ，毋是<code>'null'</code>。
	 * 
	 * @param 字元資料
	 *            愛增加的變數
	 * @return 目前的命令字串
	 */
	public DBCommandString 加變數(CharSequence 字元資料)
	{
		if (字元資料 != null)
			命令.append('\'');
		命令.append(字元資料);
		if (字元資料 != null)
			命令.append('\'');
		命令.append(' ');
		return this;
	}

	@Override
	public String toString()
	{
		return 命令.toString();
	}
}
