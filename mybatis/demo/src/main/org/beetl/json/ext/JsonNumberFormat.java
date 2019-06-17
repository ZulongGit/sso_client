package org.beetl.json.ext;

import java.text.DecimalFormat;

import org.beetl.json.DirectOutputValue;
import org.beetl.json.Format;


/*格式化Number类型，pattern参考DecimalFormat,如
 * ${0.345,numberFormat="##.#%"}
 * 
 * @author jeolli
 *
 */
public class JsonNumberFormat implements Format
{

	public DirectOutputValue format(Object data, String pattern)
	{
		DecimalFormat df = null;
		if (pattern == null)
		{
			df = new DecimalFormat();
		}
		else
		{
			df = new DecimalFormat(pattern);
		}

		String f =  df.format(data);
		return new DirectOutputValue(f);

	}

}
