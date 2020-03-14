package cn.bysj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;


@Entity
@Table(name="userwt")
public class UserWt {
	@Id
	@Column(name="systemid")
	@Length(max=50,message="systemid最大长度为50")
	private String systemid;
	@Column(name="userid")
	@Length(max=50,message="userid最大长度为50")
	private String userid;
	@Column(name="wtmc")
	@Length(max=200,message="wtmc最大长度为200")
	private String wtmc;
	@Column(name="wtda")
	@Length(max=200,message="wtda最大长度为200")
	private String wtda;
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getWtmc() {
		return wtmc;
	}
	public void setWtmc(String wtmc) {
		this.wtmc = wtmc;
	}
	public String getWtda() {
		return wtda;
	}
	public void setWtda(String wtda) {
		this.wtda = wtda;
	}
	@Override
	public String toString() {
		return "UserWt [systemid=" + systemid + ", userid=" + userid + ", wtmc=" + wtmc + ", wtda=" + wtda + "]";
	}



}