package x.y.service;

import x.y.entity.Resource;
import x.y.entity.Role;
import x.y.entity.User;
import x.y.web.authz.Permission;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/29.
 * 用于操作用户
 */
public interface UserService {

        User getByUsernameAndPassword(String username,String password);

        List<Role> getRoles(User user);

        public List<Permission> getPermissions(Role role);
}
