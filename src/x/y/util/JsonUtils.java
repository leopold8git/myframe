package x.y.util;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 项目名称：deminportal
 * 类描述：
 * 创建者：zenglianping
 * 创建时间：2014-6-13下午3:22:08
 * 版本号 
 * 
 */
public class JsonUtils {

	/**
	 * 将json 数组中的key 变为小写，配合jvc界面取值
	 * @param array
	 * @return
	 */
	public static JSONArray toKeyLowerCase(JSONArray array){
		JSONArray resArray = new JSONArray();
		if(array != null){
			for (Iterator it=array.iterator();it.hasNext();) {
				JSONObject temp = new JSONObject();
				JSONObject obj = (JSONObject)it.next();
				for (Iterator it2=obj.keys();it2.hasNext();) {
					String key = (String) it2.next();
					Object value = obj.get(key);
					temp.element(key.toLowerCase(), value);
				}
				resArray.add(temp);
			}
		}
		return resArray;
	}
}
