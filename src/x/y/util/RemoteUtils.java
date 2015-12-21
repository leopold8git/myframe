package x.y.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 项目名称：deminportal
 * 类描述：获取远程数据的工具类
 * 创建者：zenglianping
 * 创建时间：2014-6-11上午10:53:43
 * 版本号 
 * 
 */
public class RemoteUtils {

	public static Object getRemoteData(String url,Map params){
		return getRemoteData(url, params,0);
	}
	
	public static Object getRemoteData(String url,Map params,int timeOut){
		return getRemoteData(url,params,timeOut,null,null);
	}
	
	public static Object getRemoteData(String url,Map params,int timeOut,String encode){
		return getRemoteData(url,params,timeOut,null,encode);
	}
	
	public static Object getRemoteData(String url,Map params,int timeOut,String extraParamPairs,String encode){
		Object res = null;
		String fullUrl = null;
		if(url != null){
			StringBuffer sb = new StringBuffer();
			sb.append(url);
			if(url.indexOf("?")==-1){
				 sb.append("?");
			}else{
				sb.append("&");
			}
			
			if(params != null){
				for(Iterator<Map.Entry<String, Object>> it=params.entrySet().iterator();it.hasNext();){
					 Entry<String, Object> entry = it.next();
					 String key = entry.getKey();
					 Object value=null;
					try {
						value = URLEncoder.encode(entry.getValue()+"", "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					 String pair = (key+"="+value);
					 sb.append(pair);
					 sb.append("&");
				}
			}
			fullUrl = sb.toString().substring(0, sb.toString().length()-1);
			
			
			if(extraParamPairs != null){
				if(fullUrl.indexOf("?") != -1){
					fullUrl = fullUrl+"&"+extraParamPairs;
				}else{
					fullUrl = fullUrl+"?"+extraParamPairs;
				}
				
			}
		}
		
		System.out.println("fullUrl:"+fullUrl);
		if(fullUrl != null){
			if(fullUrl.length() <= 1024){
				try {
					if(timeOut >0){
						res = getHtmlSource(fullUrl.toString(),timeOut,encode==null?"UTF-8":encode);
					}else{
						res = getHtmlSource(fullUrl.toString(),encode==null?"UTF-8":encode);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}else{
				res = getHtmlSourcePost(fullUrl.toString());
			}
			
		}
		
		
		return res;
	}
	
	public static String getHtmlSource(String url, int Timeout, String encoding) throws Exception{
		return  getHtmlSource( url,  Timeout,  encoding,false);
	}
	
	
	public static String getHtmlSource(String url, int Timeout, String encoding,boolean addUserAgent)
			throws Exception {
		if (encoding == null)
			encoding = "gbk";

		String sCurrentLine = "";
		String sTotalString = "";
		URL l_url = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) l_url
				.openConnection();
		
			connection.setConnectTimeout(Timeout);
			connection.setReadTimeout(Timeout);
			if(addUserAgent){
				connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			}
		connection.connect();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), encoding));

		while ((sCurrentLine = reader.readLine()) != null) {
			sTotalString = sTotalString + sCurrentLine + "\r\n";
		}
		reader.close();
		//connection.disconnect();
		return sTotalString;
		
	}
	
	 public static String getHtmlSource(String url, String encoding) throws Exception {
		 	return getHtmlSource(url, 3000, encoding);
	  }
	 
	 public static String getHtmlSourcePost(String url) {
		 return getHtmlSourcePost(url, null,3000);
	 }
	 
	 public static String getHtmlSourcePost(String url, String param,int timeout) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setConnectTimeout(timeout);
	            conn.setReadTimeout(timeout);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	    } 
	 
}
