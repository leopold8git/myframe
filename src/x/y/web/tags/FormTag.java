package x.y.web.tags;

import x.y.page.entity.Form;
import x.y.page.entity.Text;

/**
 * Created by Administrator on 2015/12/16.
 */
public class FormTag extends ElementTag {

    private String content;

    @Override
    Class getDefaultClass() {
        return Form.class;
    }

    @Override
    public void initElement(){
        super.initElement();
        Form form = (Form)this.getElement();
        form.setContent(content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
