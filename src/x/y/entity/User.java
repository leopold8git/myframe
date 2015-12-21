package x.y.entity;

import java.util.ArrayList;
import java.util.List;

import x.y.subject.Subject;
import x.y.web.authz.Permission;
import x.y.web.authz.WildcardPermission;
/**
 * 用户信息
 * @author ASUS
 *
 */
public class User implements Subject {
	
	private boolean authenticated;
	//一个用户可以有多个角色
	private List<Role> roleList = new ArrayList<Role>();

	@Override
	public boolean isPermitted(String permission) {
		WildcardPermission wc = new WildcardPermission(permission);
		for (int i = 0; i < roleList.size(); i++) {
			Role r = roleList.get(i);
			List<Permission> pList = r.getPermisionList();
			for (int j = 0; j < pList.size(); j++) {
				Permission p = pList.get(j);
				if(p.implies(wc)){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean[] isPermitted(String... permissions) {
		boolean b[] = new boolean[permissions.length];
		for (int k = 0; k < permissions.length; k++) {
			b[k] = isPermitted(permissions[k]);
		}
		return b;
	}

	@Override
	public boolean isPermittedAll(String... permissions) {
		boolean b[] = isPermitted(permissions);
		for (int i = 0; i < b.length; i++) {
			if(!b[i]) return false;
		}
		return true;
	}


	@Override
	public boolean hasRole(String roleIdentifier) {
		for (int i = 0; i < roleList.size(); i++) {
			Role r = roleList.get(i);
			if(roleIdentifier != null && roleIdentifier.equals(r.getRoleId())){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean[] hasRoles(List<String> roleIdentifiers) {
		if(roleIdentifiers == null)return null;
		boolean b[] = new boolean[roleIdentifiers.size()];
		for (int i = 0; i < roleIdentifiers.size(); i++) {
			b[i] = hasRole(roleIdentifiers.get(i));
		}
		return b;
	}

	@Override
	public boolean hasAllRoles(List<String> roleIdentifiers) {
		if(roleIdentifiers == null) return false;
		boolean b[] = hasRoles(roleIdentifiers);
		for (int i = 0; i < b.length; i++) {
			if(!b[i]) return false;
		}
		return true;
	}

	@Override
	public boolean isAuthenticated() {
		return this.authenticated;
	}
	
	public void setAuthenticated(boolean authenticated){
		this.authenticated = authenticated;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}
