package x.y.web.tags;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;
import x.y.page.entity.Element;
import x.y.tpl.FreeMarkerSingleton;
import x.y.web.tags.authz.SecureTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2015/12/16.
 * ��������Ԫ��Tag�̳������
 */
public abstract class ElementTag extends TagSupport {

    protected Element element;

    protected String clz ;

    protected String id;

    protected String name;

    protected String attr;

    protected String fld;
    //对应父类无素id
    protected String fid ;

    public ElementTag(){

    }

    @Override
    public int doStartTag() throws JspException {
        initElement();
        Tag p = this.getParent();
        //过滤掉权限标签
        while(p != null && !(p instanceof ElementTag)){
            p = p.getParent();
        }
        if(StringUtils.isNotBlank(fid) && p != null){
            ElementTag pTag = (ElementTag)p;
            try {
                pTag.handleElement(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return TagSupport.EVAL_BODY_INCLUDE;
        }
        return TagSupport.EVAL_BODY_INCLUDE;
    }

    public Element getElement() {
        if (element == null){
            try{
                if(getDefaultClass() != null){
                        element = (Element)getDefaultClass().newInstance() ;
                }else{
                    if( clz != null && !"".equals(clz)){
                            element = (Element)Class.forName(clz).newInstance();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  element ;
    }


    protected void handleElement(ElementTag sub) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String fid = sub.getFid();

        Field field = ReflectionUtils.findField(this.getDefaultClass(), fid);
        Class clz = field.getType();
        if(clz.isAssignableFrom(List.class)){
            Method m = (Method) this.getDefaultClass().getMethod("get" + StringUtils.capitalize(fid));
            List list = (List) m.invoke(this.getElement());
            list.add(sub.getElement());
        }else{
            field.set(this, sub.getElement());
        }
    }

    protected void initElement(){
        getElement() ;
        element.setName(name);
        element.setId(id);
        element.setFld(fld);
        element.setAttr(attr);
    }

    abstract Class getDefaultClass();

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }


    public void setElement(Element element) {
        this.element = element;
    }

    public String getFld() {
        return fld;
    }

    public void setFld(String fld) {
        this.fld = fld;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    public String toString(){
        return this.element == null ? "":this.element.toString();
    }
    @Override
    public int doEndTag() throws JspException {
        //reset element
        this.element = null ;
        return super.doEndTag();
    }
}