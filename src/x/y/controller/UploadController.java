package x.y.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import x.y.entity.UploadFile;
import x.y.util.FileUtils;

@Controller
public class UploadController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/upload.htm", method = RequestMethod.POST)
    public String handleFormUpload(@RequestParam("file") MultipartFile file,HttpServletRequest req) {
		String path = req.getSession().getServletContext().getRealPath("/");
		logger.debug("path:"+path);
		String uploadDir = path+"upload";
		FileUtils.createDirIfNotExist(uploadDir);
        if (!file.isEmpty()) {
            try {
				//byte[] bytes = file.getBytes();
            	FileUtils.saveFileToDir(file.getInputStream(), uploadDir+"/"+file.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
            // store the bytes somewhere
           return "redirect:jsp/uploadSuccess.jsp";
       } else {
           return "redirect:jsp/uploadFailure.jsp";
       }
    }
	
	@RequestMapping(value = "/multiUpload.htm", method = RequestMethod.POST)
    public String handleFormMultiUpload( @RequestParam("file") CommonsMultipartFile files[],HttpServletRequest req,Model model) {
		String path = req.getSession().getServletContext().getRealPath("/");
		String contextPath = req.getContextPath();
		String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+contextPath;

		logger.debug("path:"+path);
		String uploadDir = path+"upload";
		String imgDir = basePath+"/upload";
		FileUtils.createDirIfNotExist(uploadDir);
        if (files != null) {
            try {
				//byte[] bytes = file.getBytes();
            	List fileList = new ArrayList();
            	for (int i = 0; i < files.length; i++) {
            		String originalFilename = files[i].getOriginalFilename();
            		if(!"".equals(originalFilename)){
            			UploadFile uFile = new UploadFile();
            			uFile.setFileName(originalFilename);
            			if(originalFilename.length()>1){
            				uFile.setFileType(originalFilename.substring(originalFilename.lastIndexOf(".")+1));
            			}
            			uFile.setSize(files[i].getSize());
            		String filePath = uploadDir+File.separator+files[i].getOriginalFilename();
//            		String imgPath = imgDir+File.separator+files[i].getOriginalFilename();
            		FileUtils.saveFileToDir(files[i].getInputStream(),filePath );
//            		fileList.add(imgPath.replaceAll("\\\\", "/"));
            		fileList.add(uFile);
            		}
        		}
            	model.addAttribute("fileList",fileList);
			} catch (IOException e) {
				e.printStackTrace();
			}
            // store the bytes somewhere
           return "uploadSuccess";
       } else {
           return "uploadFailure";
       }
    }
	
}
