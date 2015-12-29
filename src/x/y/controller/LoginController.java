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
	private CrudService queryService;

	
	@RequestMapping(value = "/loginIn.htm", method = RequestMethod.POST)
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
			String md5Pass = DigestUtils.md5Hex(password);
			Map<String,Object> userMap = queryService.getRecordForMap("select * from user where username=? and password=?",new Object[]{username,md5Pass});
			//TODO 初始化 user （含权限）
			if(userMap != null && userMap.size() == 0) {
				User user = new User();
				user.setId((String) userMap.get("id"));
				user.setUsername((String) userMap.get("username"));
				//获取用户角色
				String getRolesSql = "select * from role r,role_user ru where r.id = ru.roleId and ru.userId=?";
				List<Map<String,Object>> roleList = queryService.getRecords(getRolesSql,new Object[]{userMap.get("id")});
				StringBuffer roleStr = new StringBuffer();
				for (Map<String,Object> m : roleList){
					roleStr.append(m.get("id"));
					roleStr.append(",");
				}
				if(roleStr.length()>0){
					String sql = "select * from role r, role_resource rr , resource res where r.id = rr.roleId and res.id=rr.resourceId and r.id in(?)";
					List<Map<String,Object>> roleResources = queryService.getRecords(sql,new Object[]{roleStr.toString().substring(0,roleStr.length()-1)});
					//user
				}

				req.getSession().setAttribute("user", user);
				res.put("code",1);
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
