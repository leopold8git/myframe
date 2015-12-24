package x.y.web.tags;

import x.y.page.entity.EditPage;
import x.y.page.entity.QueryPage;
import x.y.util.CollectionUtils;

/**
 * Created by Administrator on 2015/12/15.
 */
public class EditPageTag extends PageTag {

    private String formJson ;

     public EditPageTag(){
     }

    @Override
    Class getDefaultClass() {
        return EditPage.class ;
    }
    @Override
    public void initElement(){
        super.initElement();
        EditPage editPage = (EditPage)this.getElement();
        editPage.setFormJson(formJson);
    }

    public String getFormJson() {
        return formJson;
    }

    public void setFormJson(String formJson) {
        this.formJson = formJson;
    }

}
