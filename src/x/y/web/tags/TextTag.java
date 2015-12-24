package x.y.web.tags;

import x.y.page.entity.TD;
import x.y.page.entity.Text;

/**
 * Created by Administrator on 2015/12/16.
 */
public class TextTag extends ElementTag {

    private String content;

    @Override
    Class getDefaultClass() {
        return Text.class;
    }

    @Override
    public void initElement(){
        super.initElement();
        Text text = (Text)this.getElement();
        text.setContent(content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
