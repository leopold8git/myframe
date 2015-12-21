package x.y.util;

import x.y.tpl.FreeMarkerSingleton;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public class FileUtils {
	
	
	public static void saveFileToDir(InputStream is , String dir) {
		
	if(is != null && is instanceof FileInputStream){
			
		FileInputStream fis = (FileInputStream)is;
			
        FileOutputStream fo = null;

        FileChannel in = null;

        FileChannel out = null;

        try {

            fo = new FileOutputStream(dir);

            in = fis.getChannel();//得到对应的文件通道

            out = fo.getChannel();//得到对应的文件通道

            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                is.close();

                in.close();

                fo.close();

                out.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }else if(is != null && is instanceof ByteArrayInputStream){
    	ByteArrayInputStream bais = (ByteArrayInputStream)is;
    	FileOutputStream fos = null;
    	 try {
    		 fos = new FileOutputStream(dir);
    		 int temp;
    		 while((temp = bais.read())!=-1){
    			 fos.write(temp);
    		 }
    		 fos.flush();
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             try {
            	 fos.close();
                 is.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }

         }
    	
    }
}
	
	public static File createDirIfNotExist(String filePath){
		File f = new File(filePath);
		if(!f.exists()){
			f.mkdirs();
		}
		return f;
	}
	
	//download
	public HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public void downloadNet(HttpServletResponse response,String remoteUrl,String savePath) throws MalformedURLException {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL(remoteUrl);

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(savePath);

            byte[] buffer = new byte[1024];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从classpath中获取文件
    public  static  InputStream getInputStreamFromClassPath(String fileName){
        InputStream is = null;
        String path = null;
        try {
            path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
            is = new FileInputStream(path+fileName) ;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return is ;
    }

    //获取文件内容
    public static String getFileContent(InputStream is){
        StringBuffer result = new StringBuffer();
        BufferedReader br = null ;
        try{
            br = new BufferedReader(new InputStreamReader(is));//构造一个BufferedReader类来读取文件
            String temp = null;
            while((temp = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(temp).append("\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    public static void main(String[] args)  {
        try {
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("word","oooooo");
            Writer out = new OutputStreamWriter(System.out);
            FreeMarkerSingleton.getInstance().process("template/query.ftl", root, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}