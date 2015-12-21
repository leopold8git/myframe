package x.y.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author ASUS
 * 用于获取bean的工具类
 */
@Component
public class SpringBeanUtils implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringBeanUtils.applicationContext = applicationContext;
	}

	public static Object getBean(String name){
		return applicationContext.getBean(name);
	}
	
	public static boolean hasBean(String name){
		return applicationContext.containsBean(name);
	}
	
}
