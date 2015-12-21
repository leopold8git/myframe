/**
 * 
 */
package x.y.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import x.y.entity.CrubEnum;
import x.y.entity.ParamConfig;
import x.y.entity.Result;
import x.y.page.entity.EditPage;
import x.y.page.entity.Page;
import x.y.page.entity.QueryPage;
import x.y.service.CrudService;
import x.y.service.impl.ExcelServiceImpl;
import x.y.service.impl.InsertOrUpdateServiceImpl;
import x.y.util.SpringBeanUtils;
import x.y.util.SpringUtils;
import x.y.util.StringUtils;

/**
 * @author zenglp
 *
 */
@Controller
@Scope("request")
public class DataBinderTestController {
	
	private final Log log = LogFactory.getLog(getClass());
	
	
	public DataBinderTestController(){
		
	}
	
	@RequestMapping("/login.htm")
	public void login(LoginUser user,@RequestParam String userId){
		log.debug("1-userId:"+user.getUserId()+" userPassword:"+user.getUserPassword());
		log.debug("2-userId:"+userId);
	}
}

class LoginUser{
	private String userId;
	private String userPassword;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
