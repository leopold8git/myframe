package x.y.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
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

/**
 * @author ASUS
 * 生成验证码图片
 *
 */
@Controller
public class ImageController {

	private final Log log = LogFactory.getLog(getClass());
	
	
	@RequestMapping("/image.htm")
	public void doGet(HttpServletRequest req,HttpServletResponse resp){
		resp.setContentType("image/jpeg");
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0L);
	    BufferedImage bufferedImage = new BufferedImage(112, 42, 1);
	    Graphics graphics = bufferedImage.getGraphics();
	    graphics.setColor(new Color(255, 255, 255));
	    graphics.fillRect(0, 0, 112, 42);
	    graphics.setFont(new Font("Arial", 0, 48));
	    Random r = new Random();
	    int i = 220;
	    i = 0;
	    int k = 0 + r.nextInt(220);
	    int m = 0 + r.nextInt(220);
	    int j = 0 + r.nextInt(220);
	    Color c = new Color(k, m, j);
	    StringBuilder sb = new StringBuilder();
	    String str = null;
	    for (int n = 0; n < 4; n++){
	      str = String.valueOf(r.nextInt(10));
	      sb.append(str);
	      graphics.setColor(c);
	      graphics.drawString(str, n * 28, 38);
	    }
	    HttpSession session = req.getSession();
	    session.setAttribute("vcode", sb.toString());
	    log.debug("vcode:"+sb.toString());
	    graphics.dispose();
	    ServletOutputStream os = null;
	    try{
	      os = resp.getOutputStream();
	      ImageIO.write(bufferedImage, "JPEG", os);
	      os.flush();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{//commons IO
	      IOUtils.closeQuietly(os);
	    }
	}
}
