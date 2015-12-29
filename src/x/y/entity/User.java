package x.y.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import x.y.subject.Subject;
import x.y.web.authz.Permission;
import x.y.web.authz.WildcardPermission;
import javax.persistence.*;
/**
 * 用户信息
 * @author ASUS
 * 在初始化用户信息时，要初始化角色、权限信息
 */
@Entity
@Table(name = "user")
public class User implements Subject,Serializable {

	private String id ;

	private String username ;

	private String password ;

	
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

	@Basic
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Basic
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
