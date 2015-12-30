package x.y.entity;

import java.util.ArrayList;
import java.util.List;

import x.y.web.authz.Permission;

import javax.persistence.*;

/**
 * 角色
 * @author ASUS
 * 角色中含多个permission
 */
@Entity
@Table(name = "role")
public class Role {

	private int roleId;
	
	private String roleName;
	
	private List<Permission> permisionList = new ArrayList<Permission>();

	private List<User> userList  =new ArrayList<User>();

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Basic
	@Column(name = "roleName")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@ManyToMany
	@JoinTable(name="role_resource",joinColumns=@JoinColumn(name="roleId"))
	public List<Permission> getPermisionList() {
		return permisionList;
	}

	public void setPermisionList(List<Permission> permisionList) {
		this.permisionList = permisionList;
	}

	@ManyToMany(mappedBy="roleList")
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
