package x.y.entity;

import x.y.web.authz.Permission;

/**
 * Created by Administrator on 2015/12/29.
 */
public class Resource implements Permission {

    private String resourceId ;

    private String resourceName ;

    @Override
    public boolean implies(Permission p) {
        if(p instanceof  Resource){
            Resource r = (Resource)p;
            if(resourceName != null && resourceName.equals(r.getResourceName())){
                return  true;
            }
        }
        return  false ;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
