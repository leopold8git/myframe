package x.y.web.tags;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;
import x.y.page.entity.Element;
import x.y.tpl.FreeMarkerSingleton;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
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
    //����ָ������ǩ����ֶ�����
    protected String fid ;

    public ElementTag(){
        System.out.println("��ʼ��:"+this.getClass().getName());
    }

    @Override
    public int doStartTag() throws JspException {
        initElement();
        if(StringUtils.isNotBlank(fid) && this.getParent() != null){
            ElementTag pTag = (ElementTag)this.getParent();
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

    //������ø���ķ���
    protected void handleElement(ElementTag sub) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String fid = sub.getFid();
        //��ȡ������ֶ�
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
        //���element
        this.element = null ;
        return super.doEndTag();
    }
}