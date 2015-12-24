package x.y.web.tags;

import x.y.page.entity.Select;
import x.y.page.entity.Text;

/**
 * Created by Administrator on 2015/12/16.
 */
public class SelectTag extends ElementTag {

    private String dataUrl;

    @Override
    Class getDefaultClass() {
        return Select.class;
    }

    @Override
    public void initElement(){
        super.initElement();
        Select select = (Select)this.getElement();
        select.setDataUrl(dataUrl);
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }
}
