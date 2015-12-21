package x.y.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import x.y.dao.BaseDao;
import x.y.service.CrudService;

@Controller
public class TransController {

	private final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private CrudService updateService;
	
	@Autowired
	private BaseDao baseDao;
	
	@Transactional
	@RequestMapping("/trans.htm")
	public void doTrans(Model model){
		baseDao.updateRecords("update PhoneBrand set brandName=? where brandId=?", new Object[]{"苹果手机2",11});
		baseDao.updateRecords("updat PhoneBrand set brandName=? where brandId=?", new Object[]{"三星手机2",12});
	}
	/**
	 * 异常处理 
	 * @param ex
	 * @param request
	 * 要注意spring默认配置了AnnotationMethodExceptionResolver
	 * @return
	 */
	  @ExceptionHandler(RuntimeException.class)
	  public String handleIOException(Exception ex, HttpServletRequest request) {
		 ex.printStackTrace();
	    return "exception";
	  }
	  
	  

}
