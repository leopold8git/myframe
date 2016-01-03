package x.y.web.tags;

import x.y.page.entity.*;
import x.y.tpl.FreeMarkerSingleton;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Arrays;

/**
 * Created by Administrator on 2015/12/15.
 */
public class PageTag extends ElementTag {

    private final String TEMPLATE_PATH = "template/";

    private String template ;

    private String js;

    private String style;

    private String title;

    //�����������
    private String viewName;

    private String jsLink ; //用 | 分隔

    private String styleLink ;  //用 | 分隔


    @Override
    public int doStartTag() throws JspException {
        initElement();
        return  super.doStartTag();

    }

    @Override
    Class getDefaultClass() {
        return Page.class ;
    }
    @Override
    public void initElement(){
        super.initElement();
        Page page = (Page)this.getElement();
        page.setJs(js);
        page.setStyle(style);
        page.setTitle(title);
        page.setViewName(viewName);
        if(jsLink != null){
            page.setJsLink(Arrays.asList(jsLink.split("[|]")));
        }
        if(styleLink != null){
            page.setStyleLink(Arrays.asList(styleLink.split("[|]")));
        }
    }


    @Override
    public int doEndTag() throws JspException {
//        return super.doEndTag();
        JspWriter out = pageContext.getOut();
        String ftl = TEMPLATE_PATH+template ;
        FreeMarkerSingleton.getInstance().process(ftl,element,out);
        return super.doEndTag();
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTEMPLATE_PATH() {
        return TEMPLATE_PATH;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getJsLink() {
        return jsLink;
    }

    public void setJsLink(String jsLink) {
        this.jsLink = jsLink;
    }

    public String getStyleLink() {
        return styleLink;
    }

    public void setStyleLink(String styleLink) {
        this.styleLink = styleLink;
    }
}
