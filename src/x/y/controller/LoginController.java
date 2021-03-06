package x.y.controller;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import x.y.entity.UploadFile;
import x.y.entity.User;
import x.y.service.CrudService;
import x.y.service.UserService;
import x.y.subject.Subject;
import x.y.util.FileUtils;
import x.y.util.SpringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private UserService userService;

	
	@RequestMapping(value = "/loginIn.htm", method = RequestMethod.GET)
    public String loginIn(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		resp.setContentType("text/json;charset=utf-8");
		JSONObject res = new JSONObject();
		res.put("code",-1);
		Map reqMap = SpringUtils.getRequestPramaMap(req);
		Map params = SpringUtils.getParams(reqMap, req);
		String username = (String) params.get("username");
		String password = (String) params.get("password");
		String repassword = (String) params.get("repassword");
		if(StringUtils.isNotBlank(password) && password.equals(repassword)){
			//md5加密
			String md5Password = DigestUtils.md5Hex(password);
			User user = userService.getByUsernameAndPassword(username,md5Password);
			if(user != null){
				req.getSession().setAttribute(Subject.sessionKey, user);
				res.put("code", 1);
			}

		}
		resp.getWriter().print(res);
		resp.getWriter().close();
		return  null ;
    }

	@RequestMapping(value = "/loginOut.htm", method = RequestMethod.POST)
	public String loginOut(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		req.getSession().removeAttribute("user");
		resp.setContentType("text/json;charset=utf-8");
		JSONObject res = new JSONObject();
		res.put("code", -1);
		resp.getWriter().print(res);
		resp.getWriter().close();
		return  null ;
	}

	
}
