package x.y.entity;

import x.y.web.authz.Permission;

import javax.persistence.*;

/**
 * Created by Administrator on 2015/12/29.
 */
@Entity
@Table(name = "role")
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
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Basic
    @Column(name = "resourceName")
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
