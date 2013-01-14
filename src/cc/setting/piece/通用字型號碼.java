package cc.setting.piece;

public class 通用字型號碼
{
	private int 統一碼;
	private int 構型資料庫字體號碼;
	private int 全字庫號碼;
	private int 大五碼;

	public 通用字型號碼(int 統一碼, int 構型資料庫字體號碼, int 全字庫號碼, int 大五碼)
	{
		this.統一碼 = 統一碼;
		this.構型資料庫字體號碼 = 構型資料庫字體號碼;
		this.全字庫號碼 = 全字庫號碼;
		this.大五碼 = 大五碼;
	}

	public 通用字型號碼(int 統一碼, int 構型資料庫字體號碼, int 全字庫號碼)
	{
		this(統一碼, 構型資料庫字體號碼, 全字庫號碼, -1);
	}

	public 通用字型號碼(int 統一碼, int 構型資料庫字體號碼)
	{
		this(統一碼, 構型資料庫字體號碼, -1, -1);
	}

	public 通用字型號碼(int 統一碼)
	{
		this(統一碼, -1, -1, -1);
	}

	public boolean 有統一碼無()
	{
		return 統一碼 != -1;
	}

	public int 提統一碼()
	{
		return 統一碼;
	}

	public boolean 有構型資料庫字體號碼無()
	{
		return 構型資料庫字體號碼 != -1;
	}

	public int 提構型資料庫字體號碼()
	{
		return 構型資料庫字體號碼;
	}

	public boolean 有全字庫號碼無()
	{
		return 全字庫號碼 != -1;
	}

	public int 提全字庫號碼()
	{
		return 全字庫號碼;
	}

	public boolean 有大五碼無()
	{
		return 大五碼 != -1;
	}

	public int 提大五碼()
	{
		return 大五碼;
	}
}
