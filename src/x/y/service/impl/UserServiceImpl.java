package x.y.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import x.y.dao.BaseDao;
import x.y.entity.Resource;
import x.y.entity.Role;
import x.y.entity.User;
import x.y.service.UserService;
import x.y.web.authz.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 2016/1/2.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    protected BaseDao baseDao;

    @Override
    public User getByUsernameAndPassword(String username,String password) {
        Map userMap = baseDao.queryForMap("select * from user where username=? and password=?", new Object[]{username, password});

        User user = null;

        if(userMap != null && !userMap.isEmpty())  {
            user = new User();
            user.setUserId(Integer.valueOf(userMap.get("userId")+""));
            user.setUsername(String.valueOf(userMap.get("username")));
            user.setPassword(String.valueOf(userMap.get("password")));
            user.setRoleList(getRoles(user));
        }

        return user;
    }

    @Override
    public List<Role> getRoles(User user) {
        String sql = "select r.* from user_role ur , role r where ur.roleId=r.roleId and ur.userId=?";
        List<Map<String,Object>> roleList = baseDao.queryRecords(sql,new Object[]{user.getUserId()});
        List<Role> roles = new ArrayList<Role>();
        for (Map roleMap : roleList) {
            Role role = new Role();
            role.setRoleId(Integer.valueOf(roleMap.get("roleId") + ""));
            role.setRoleName(String.valueOf(roleMap.get("roleName")));
            role.setPermisionList(getPermissions(role));
            roles.add(role);
        }
        return roles;
    }

    @Override
    public List<Permission> getPermissions(Role role) {
        String sql = "select r.* from role_resource rr , resource r where rr.roleRsourceId=r.resourceId and rr.roleId=?";
        List<Map<String,Object>> resourceList = baseDao.queryRecords(sql,new Object[]{role.getRoleId()});
        List<Permission> resources = new ArrayList<Permission>();
        for (Map resourceMap : resourceList) {
            Resource resource = new Resource();
            resource.setResourceId(Integer.valueOf(resourceMap.get("resourceId") + ""));
            resource.setResourceName(String.valueOf(resourceMap.get("resourceName")));
            resources.add(resource);
        }
        return resources;
    }
}
