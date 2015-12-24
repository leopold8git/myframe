package x.y.web.tags;

import x.y.page.entity.Text;
import x.y.page.entity.TextArea;

/**
 * Created by Administrator on 2015/12/16.
 */
public class TextAreaTag extends ElementTag {

    @Override
    Class getDefaultClass() {
        return TextArea.class;
    }

    @Override
    public void initElement(){
        super.initElement();
    }

}
