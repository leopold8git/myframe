package x.y.web.tags;

import x.y.page.entity.Span;
import x.y.page.entity.TextArea;

/**
 * Created by Administrator on 2015/12/16.
 */
public class SpanTag extends ElementTag {

    @Override
    Class getDefaultClass() {
        return Span.class;
    }

    @Override
    public void initElement(){
        super.initElement();
    }
    
}
