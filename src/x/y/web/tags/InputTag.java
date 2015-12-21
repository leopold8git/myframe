package x.y.web.tags;

import x.y.page.entity.Input;

import javax.servlet.jsp.JspException;

/**
 * Created by Administrator on 2015/12/16.
 */
public class InputTag extends ElementTag {

    private String className;

    private String onclick;

    private String value;

    private String type ="text";

    @Override
    Class getDefaultClass() {
        return Input.class;
    }

    @Override
    public int doStartTag() throws JspException {
        return  super.doStartTag();
    }

    @Override
    public void initElement(){
        super.initElement();
        Input input = (Input)this.getElement();
        input.setClassName(className);
        input.setOnclick(onclick);
        input.setValue(value);
        input.setType(type);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
