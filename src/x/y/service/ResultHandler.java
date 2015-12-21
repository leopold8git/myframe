package x.y.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 结果处理
 * @author ASUS
 *
 */
public interface ResultHandler {

	Object handlerResult(Map params,Object result,HttpServletRequest request,HttpServletResponse response);
}
