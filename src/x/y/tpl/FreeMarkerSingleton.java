package x.y.tpl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/16.
 */
public class FreeMarkerSingleton {

    private static  FreeMarkerSingleton instance = new FreeMarkerSingleton();

    private  Configuration freeMarkerCfg ;

    private  FreeMarkerSingleton(){
        //只初始化一次
        initFreeMarkerCfg() ;
    }

    private void initFreeMarkerCfg()  {
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();

            freeMarkerCfg = new Configuration(Configuration.VERSION_2_3_22);

            freeMarkerCfg.setDirectoryForTemplateLoading(new File(path));

            freeMarkerCfg.setDefaultEncoding("UTF-8");
            //for handler
            freeMarkerCfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FreeMarkerSingleton  getInstance(){
        return  instance ;
    }

    public void process( String tpl ,Object dataModel , Writer out){
        try {
            Template template = freeMarkerCfg.getTemplate(tpl);
            template.process(dataModel, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
