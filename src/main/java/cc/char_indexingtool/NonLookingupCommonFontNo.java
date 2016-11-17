package cc.char_indexingtool;

/**
 * 停止使用查通用字型編號的功能。
 * 
 * @author Ihc
 */
public class NonLookingupCommonFontNo implements CommonFontNoSearch
{
	/**
	 * 提供介面，回傳揣無。
	 * 
	 * @param 展開式
	 *            愛揣的字型展開式
	 * @return 毋管按怎，攏回傳<code>null</code>
	 */
	@Override
	public CommonFontNo 查通用字型編號(String 展開式)
	{
		return null;
	}

}
