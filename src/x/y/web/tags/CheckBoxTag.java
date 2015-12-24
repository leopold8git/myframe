package x.y.web.tags;

import x.y.page.entity.CheckBox;
import x.y.page.entity.Text;

/**
 * Created by Administrator on 2015/12/16.
 */
public class CheckBoxTag extends ElementTag {


    @Override
    Class getDefaultClass() {
        return CheckBox.class;
    }

    @Override
    public void initElement(){
        super.initElement();
    }

}
