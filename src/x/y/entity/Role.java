package x.y.entity;

import java.util.ArrayList;
import java.util.List;

import org.omg.PortableInterceptor.INACTIVE;
import x.y.web.authz.Permission;

/**
 * 角色
 * @author ASUS
 * 角色中含多个permission
 */
public class Role {

	private Integer roleId;
	
	private String roleName;
	
	private List<Permission> permisionList = new ArrayList<Permission>();

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Permission> getPermisionList() {
		return permisionList;
	}

	public void setPermisionList(List<Permission> permisionList) {
		this.permisionList = permisionList;
	}
	
	
}
