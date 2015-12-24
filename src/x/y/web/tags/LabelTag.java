package x.y.web.tags;

import x.y.page.entity.Label;
import x.y.page.entity.Text;

/**
 * Created by Administrator on 2015/12/16.
 */
public class LabelTag extends ElementTag {

    private String relId;

    @Override
    Class getDefaultClass() {
        return Text.class;
    }

    @Override
    public void initElement(){
        super.initElement();
        Label label = (Label)this.getElement();
        label.setRelId(relId);
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }
}
