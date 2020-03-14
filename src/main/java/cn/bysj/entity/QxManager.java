package cn.bysj.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="t_qxmanager")
public class QxManager {

	@Id
	@Column(name="systemid")
	@Length(max=50 , message="systemid最大长度为50")
	private String systemid;

	@Column(name="userid")
	@Length(max=50 , message="userid最大长度为50")
	private String userid;

	@Column(name="fileid")
	@Length(max=50 , message="fileid最大长度为50")
	private String fileid;

	@Column(name="sqsj")
	private Date sqsj;

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

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public Date getSqsj() {
		return sqsj;
	}

	public void setSqsj(Date sqxj) {
		this.sqsj = sqxj;
	}

	@Override
	public String toString() {
		return "QxManager [systemid=" + systemid + ", userid=" + userid + ", fileid=" + fileid + ", sqsj=" + sqsj + "]";
	}


}