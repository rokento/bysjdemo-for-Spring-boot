package cn.bysj.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="Users")
public class Users {

	@Id
	@Column(name="systemid")
	@Length(max=50 , message="systemid最大长度为50")
	private String systemid;

	@Column(name="name")
	@Length(max=50 , message="name最大长度为50")
	private String name;

	@Column(name="username")
	@Length(max=50 , message="username最大长度为50")
	private String username;

	@Column(name="pwd")
	@Length(max=50 , message="pwd最大长度为50")
	private String pwd;

	@Column(name="sex")
	@Length(max=2 , message="sex最大长度为2")
	private String sex;

	@Column(name="csrq")
	private Date csrq;

	@Column(name="token")
	@Length(max=50 , message="token最大长度为50")
	private String token;

	@Column(name="touxiang")
	private String touxiang;

	public Users() {
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getCsrq() {
		return csrq;
	}

	public void setCsrq(Date csrq) {
		this.csrq = csrq;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTouxiang() {
		return touxiang;
	}

	public void setTouxiang(String touxiang) {
		this.touxiang = touxiang;
	}

	@Override
	public String toString() {
		return "Users [systemid=" + systemid + ", name=" + name + ", username=" + username + ", pwd=" + pwd + ", sex="
				+ sex + ", csrq=" + csrq + ", token=" + token + ", touxiang=" + touxiang + "]";
	}




}