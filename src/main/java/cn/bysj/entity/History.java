package cn.bysj.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/****
 * 浏览历史表
 * @author 
 *
 */
@Entity
@Table(name="history")
public class History {

	@Id
	@Column(name="systemid")
	@Length(max=50 , message="systemid最大长度为50")
	private String systemid;

	@Column(name="fileid")
	@Length(max=50 , message="fileid最大长度为50")
	private String fileid;

	@Column(name="userid")
	@Length(max=50 , message="userid最大长度为50")
	private String userid;

	@Column(name="cjsj")
	private Date cjsj;

	@Override
	public String toString() {
		return "History [systemid=" + systemid + ", fileid=" + fileid + ", userid=" + userid + ", cjsj=" + cjsj + "]";
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}



}