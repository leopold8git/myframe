package x.y.web.tags;

import x.y.page.entity.Element;
import x.y.page.entity.Page;
import x.y.page.entity.QueryPage;
import x.y.page.entity.TD;
import x.y.tpl.FreeMarkerSingleton;
import x.y.util.CollectionUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/15.
 */
public class QueryPageTag extends PageTag {

    private String formJson ;

    private String headers;//split by |

    //查询名称
    private String qns;

     public QueryPageTag(){
         System.out.println("创建QueryPageTag");
     }

    @Override
    Class getDefaultClass() {
        return QueryPage.class ;
    }
    @Override
    public void initElement(){
        super.initElement();
        QueryPage queryPage = (QueryPage)this.getElement();
        queryPage.setFormJson(formJson);
        if(headers != null){
            String[] arr = headers.split("[|]");
            queryPage.setHeaders(CollectionUtils.asList(arr));
        }

        queryPage.setQns(qns);
    }

    public String getQns() {
        return qns;
    }

    public void setQns(String qns) {
        this.qns = qns;
    }

    public String getFormJson() {
        return formJson;
    }

    public void setFormJson(String formJson) {
        this.formJson = formJson;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }
}
