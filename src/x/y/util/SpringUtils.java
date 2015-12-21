package x.y.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import x.y.entity.ParamConfig;

public class SpringUtils {

	public static  Map getRequestPramaMap(HttpServletRequest req) throws UnsupportedEncodingException{
		Map map = new HashMap();
		Enumeration e = req.getParameterNames();
		while(e.hasMoreElements()){
			String name = (String) e.nextElement();
			String v = req.getParameter(name);
			v  = new String(v.getBytes("ISO-8859-1"),"UTF-8");
			map.put(name,v);
		}
		return map;
	}
	
	public static  ParamConfig getParamConfig(HttpServletRequest req) throws IOException{
		Map map = null;
		ParamConfig paramConfig = null;
		String pc = req.getParameter("d");
		if(pc != null){
			Object bean = SpringBeanUtils.getBean(pc+"");
			paramConfig = (ParamConfig)bean;
		}
		return paramConfig;
	}
	
	public static  Map getParams(HttpServletRequest req) throws IOException{
		Map map = getRequestPramaMap(req);
		ParamConfig pc = getParamConfig(req);
		if(pc != null){
			map.putAll(pc.getConfigMap());
		}
		return map;
	}
	
	public static  Map getParams(Map map ,HttpServletRequest req) throws IOException{
		Map newMap = new HashMap();
		newMap.putAll(map);
		ParamConfig pc = getParamConfig(req);
		if(pc != null){
			newMap.putAll(pc.getConfigMap());
		}
		return newMap;
	}
}
