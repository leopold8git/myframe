package x.y.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.MethodUtils;

import net.sf.json.JSONObject;

public class StringUtils {

	public static String getMessageFormatStr(String template,String ... values){
		MessageFormat mf = new MessageFormat(template);
		return mf.format(values);
		
	}
	//String s = "我是{abc}秒{name}";
	public static String fillTpl(String tpl,Object obj){
		String regexp = "\\{\\w+\\}";
		Pattern p = Pattern.compile(regexp);
		 Matcher m = p.matcher(tpl);
		 while( m.find()){
			 String text = m.group();
			 if(text != null && text.length()>1){
				 String t = text.substring(1, text.length()-1);
				 if(obj instanceof JSONObject){
					 JSONObject jobj = (JSONObject)obj;
					 if(jobj.containsKey(t)){
						 tpl = tpl.replace(text, jobj.getString(t));
					 }
				 }else if(obj instanceof Map){
					 Map map = (Map)obj;
					 if(map.containsKey(t)){
						 tpl = tpl.replace(text, String.valueOf(map.get(t)));
					 }
				 }else if(obj != null){
					Class clz = obj.getClass();
				 	Object result = null;
					try {
						result = MethodUtils.invokeMethod(obj, "get"+org.apache.commons.lang.StringUtils.capitalize(t), null);
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					if(result == null){result = "";}
					tpl = tpl.replace(text, String.valueOf(result));
				 }
			 }
		 }
		 return tpl;
	}
	
	public static boolean isBlank(String s){
		return s == null || "".equals(s);
	}
	
	public static boolean isNotBlank(String s){
		return !isBlank(s);
	}
	
	public static String nullToString(String s){
		return s == null?"":s;
	}
	
	public static String blankToString(String s){
		return blankToString(s,"");
	}
	
	public static String blankToString(String s,String defaultValue){
		return isBlank(s)?defaultValue:s;
	}
	
	
	public static List stringToList(String s,String seperator){
		List<String> list = new ArrayList<String>();
		if(isNotBlank(s)){
			String[] tempArr = s.split("["+seperator+"]");
			list.addAll(Arrays.asList(tempArr));
		}
		return list;
	}

	public static void main(String[] args) {
		System.out.println(stringToList("a|b","|"));
	}
	
}
