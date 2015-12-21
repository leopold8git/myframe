package x.y.web.tags;

import x.y.page.entity.Input;
import x.y.page.entity.TD;

/**
 * Created by Administrator on 2015/12/16.
 */
public class TDTag extends ElementTag {

    private String content;

    @Override
    Class getDefaultClass() {
        return TD.class;
    }

    @Override
    public void initElement(){
        super.initElement();
        TD td = (TD)this.getElement();
        td.setContent(content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
