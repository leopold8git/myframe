package x.y.util;

import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectUtils {

	/**
	 * 判断类里（超类、接口）是否有某个公共方法
	 * @param clz
	 * @return
	 */
	public static boolean hasPublicMethod(Class clz,String methodName){
		boolean b = false;
		Method[] methods = clz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			if(m.getName() != null && m.getName().equals(methodName)){
				b = true;
				break;
			}
		}
		return b;
	}
	public static Method getPublicMethod(Class clz,String methodName){
		Method[] methods = clz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			if(m.getName() != null && m.getName().equals(methodName)){
				return m;
			}
		}
		return null;
	}
}
