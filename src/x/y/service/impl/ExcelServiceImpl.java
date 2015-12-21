package x.y.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import x.y.entity.ExcelPoi;

@Service("excelService")
public class ExcelServiceImpl {

	public void createExcel(HttpServletResponse response,Map params,List<Map> list){
		 ExcelPoi el = new ExcelPoi();
		   try {
			  
		     String header = (String)params.get("header");
		     String sheetName = (String)params.get("SheetName");
		     if (sheetName == null) sheetName = "sheet1";
		     String fileNameEncoding = (String)params.get("fileNameEncoding");
		     if(fileNameEncoding == null || "".equals(fileNameEncoding)){
		    	 fileNameEncoding = "UTF-8";
		     }
		     response.setContentType("application/x-msdownload");
//		     response.setContentType("application/msexcel");
		     response.setHeader("Content-Disposition", String.valueOf(new StringBuffer(String.valueOf("attachment;")).append("filename=").append(new String(header.getBytes(fileNameEncoding),"ISO-8859-1") + ".xls")));
		     el.setOutputSream(response.getOutputStream());
		     el.createSheet(sheetName);
		     el.setHeader(header);
//		     header = new String(header.getBytes("ISO-8859-1"),"UTF-8");
//		     header = new String(header.getBytes("gbk"), "ISO8859-1");
		   
		     
		    
		     String title = (String)params.get("title");
		     String column = (String)params.get("column");
		     String format = (String)params.get("format");
		     String[] formats = format.split("[|]");
		     String[] p = title.split("[|]");
		
		     for (int i = 0; i < p.length; i++) {
		       boolean issum = false;
		       if (formats.length > i) format = formats[i]; else
		         format = "";
		       if ((format.startsWith("int")) || (format.startsWith("money")) || (format.startsWith("0"))) issum = true;
		       el.setTitle(i + 1, p[i], issum);
		     }
		
		     p = column.split("[|]");
		     
		     
		    for (int i = 0; i < list.size(); i++) {
		    	Map map = list.get(i);
		    	for (int j = 0; j < p.length; j++) {
			         if (formats.length > j) format = formats[j]; else {
			           format = "";
			         }
			         String tname = p[j];
			         Object value = map.get(tname);
			         el.setText(i+1, j + 1, value.toString(), format);
		       }
		    	
			}
		
		     el.setTotal();
		     el.setTime();
		     el.write();
		   }
		   catch (Exception e)
		   {
		     e.printStackTrace();
		   }
		   finally {
		     el.close();
		   }
	}
}
