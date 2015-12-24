package x.y.web.tags;

import x.y.page.entity.Option;
import x.y.page.entity.Text;

/**
 * Created by Administrator on 2015/12/16.
 */
public class OptionTag extends ElementTag {

    private String attrVal ;
    private String namefld ;
    private String valuefld ;
    private String value;

    @Override
    Class getDefaultClass() {
        return Option.class;
    }

    @Override
    public void initElement(){
        super.initElement();
        Option option = (Option)this.getElement();
        option.setAttrVal(attrVal);
        option.setNamefld(namefld);
        option.setValuefld(valuefld);
        option.setValue(value);
    }

    public String getAttrVal() {
        return attrVal;
    }

    public void setAttrVal(String attrVal) {
        this.attrVal = attrVal;
    }

    public String getNamefld() {
        return namefld;
    }

    public void setNamefld(String namefld) {
        this.namefld = namefld;
    }

    public String getValuefld() {
        return valuefld;
    }

    public void setValuefld(String valuefld) {
        this.valuefld = valuefld;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
