package cc.char_indexingtool;

/**
 * 資料庫的字型資料。
 * 
 * @author Ihc
 */
public class CommonFontNo
{
	/** 字型的統一碼（Unicode） */
	private int 統一碼;
	/** 字型佇佗一个構型資料庫的字體 */
	private int 構型資料庫字體號碼;
	/** 構型資料庫內底的字型編號 */
	private int 構型資料庫字型號碼;
	/** 字型的全字庫號碼（CNS11643） */
	private int 全字庫號碼;
	/** 字型的大五碼（Big5） */
	private int 大五碼;

	/**
	 * 建立一个通用字型資料。
	 * 
	 * @param 統一碼
	 *            字型的統一碼（Unicode）
	 * @param 構型資料庫字體號碼
	 *            字型佇佗一个構型資料庫的字體
	 * @param 構型資料庫字型號碼
	 *            構型資料庫內底的字型編號
	 * @param 全字庫號碼
	 *            字型的全字庫號碼（CNS11643）
	 * @param 大五碼
	 *            字型的大五碼（Big5）
	 */
	public CommonFontNo(int 統一碼, int 構型資料庫字體號碼, int 構型資料庫字型號碼, int 全字庫號碼, int 大五碼)
	{
		this.統一碼 = 統一碼;
		this.構型資料庫字體號碼 = 構型資料庫字體號碼;
		this.構型資料庫字型號碼 = 構型資料庫字型號碼;
		this.全字庫號碼 = 全字庫號碼;
		this.大五碼 = 大五碼;
	}

	/**
	 * 建立一个通用字型資料。
	 * 
	 * @param 統一碼
	 *            字型的統一碼（Unicode）
	 * @param 構型資料庫字體號碼
	 *            字型佇佗一个構型資料庫的字體
	 * @param 構型資料庫字型號碼
	 *            構型資料庫內底的字型編號
	 * @param 全字庫號碼
	 *            字型的全字庫號碼（CNS11643）
	 */
	public CommonFontNo(int 統一碼, int 構型資料庫字體號碼, int 構型資料庫字型號碼, int 全字庫號碼)
	{
		this(統一碼, 構型資料庫字體號碼, 構型資料庫字型號碼, 全字庫號碼, -1);
	}

	/**
	 * 建立一个通用字型資料。
	 * 
	 * @param 統一碼
	 *            字型的統一碼（Unicode）
	 * @param 構型資料庫字體號碼
	 *            字型佇佗一个構型資料庫的字體
	 * @param 構型資料庫字型號碼
	 *            構型資料庫內底的字型編號
	 */
	public CommonFontNo(int 統一碼, int 構型資料庫字體號碼, int 構型資料庫字型號碼)
	{
		this(統一碼, 構型資料庫字體號碼, 構型資料庫字型號碼, -1, -1);
	}

	/**
	 * 建立一个通用字型資料。
	 * 
	 * @param 統一碼
	 *            字型的統一碼（Unicode）
	 */
	public CommonFontNo(int 統一碼)
	{
		this(統一碼, -1, -1, -1, -1);
	}

	/**
	 * 查看覓字型資料內底有統一碼（Unicode）無。
	 * 
	 * @return 字型資料有統一碼（Unicode）無？
	 */
	public boolean 有統一碼無()
	{
		return 統一碼 != -1;
	}

	/**
	 * 查字型的統一碼（Unicode）。
	 * 
	 * @return 字型的統一碼（Unicode）。若無號碼，會回傳-1
	 */
	public int 提統一碼()
	{
		return 統一碼;
	}

	/**
	 * 查看覓字型資料內底有構型資料庫的字體編號無。
	 * 
	 * @return 字型資料有構型資料庫的字體編號無？
	 */
	public boolean 有構型資料庫字體號碼無()
	{
		return 構型資料庫字體號碼 != -1;
	}

	/**
	 * 查字型的構型資料庫的字體編號。
	 * 
	 * @return 字型的構型資料庫的字體編號。若無號碼，會回傳-1
	 */
	public int 提構型資料庫字體號碼()
	{
		return 構型資料庫字體號碼;
	}

	/**
	 * 查看覓字型資料內底有構型資料庫的字型編號無。
	 * 
	 * @return 字型資料有構型資料庫的字型編號無？
	 */
	public boolean 有構型資料庫字型號碼無()
	{
		return 構型資料庫字型號碼 != -1;
	}

	/**
	 * 查字型的構型資料庫的字型編號。
	 * 
	 * @return 字型的構型資料庫的字型編號。若無號碼，會回傳-1
	 */
	public int 提構型資料庫字型號碼()
	{
		return 構型資料庫字型號碼;
	}

	/**
	 * 查看覓字型資料內底有構型資料庫的號碼資料無。
	 * 
	 * @return 字型資料有構型資料庫的號碼資料無？
	 */
	public boolean 有構型資料庫號碼資料無()
	{
		return 有構型資料庫字體號碼無() && 有構型資料庫字型號碼無();
	}

	/**
	 * 查看覓字型資料內底有全字庫號碼（CNS11643）無。
	 * 
	 * @return 字型資料有全字庫號碼（CNS11643）無？
	 */
	public boolean 有全字庫號碼無()
	{
		return 全字庫號碼 != -1;
	}

	/**
	 * 查字型的統一碼（Unicode）。
	 * 
	 * @return 字型的統一碼（Unicode）。若無號碼，會回傳-1
	 */
	public int 提全字庫號碼()
	{
		return 全字庫號碼;
	}

	/**
	 * 查看覓字型資料內底有大五碼（Big5）無。
	 * 
	 * @return 字型資料有大五碼（Big5）無？
	 */
	public boolean 有大五碼無()
	{
		return 大五碼 != -1;
	}

	/**
	 * 查字型的大五碼（Big5）。
	 * 
	 * @return 字型的大五碼（Big5）。若無號碼，會回傳-1
	 */
	public int 提大五碼()
	{
		return 大五碼;
	}
}
