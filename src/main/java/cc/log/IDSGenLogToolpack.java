package cc.log;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.cal10n.LocLoggerFactory;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;

/**
 * 予規个程式一个統一介面。目前使用臺員型態。 使用這个功能，愛用<code>slf4j-api.jar</code>佮
 * <code>cal10n-api.jar</code>。
 * 
 * @author Ihc
 */
public class IDSGenLogToolpack
{
	/** 記錄語系 */
	private final IMessageConveyor 訊息型態;
	/** 產生工具的物件 */
	private final LocLoggerFactory 記錄工具包;

	/** 建立一个工具包。 */
	public IDSGenLogToolpack()
	{
		訊息型態 = new MessageConveyor(Locale.TAIWAN);
		記錄工具包 = new LocLoggerFactory(訊息型態);
	}

	/**
	 * 揣一个記錄工具。
	 * 
	 * @param 呼叫型態
	 *            愛記錄資料的型態
	 * @return　會當記錄的工具
	 * */
	public Logger 記錄工具(Class<?> 呼叫型態)
	{
		return 記錄工具包.getLocLogger(呼叫型態);
	}
}
