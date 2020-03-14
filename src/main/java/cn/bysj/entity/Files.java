package cn.bysj.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="t_files")
public class Files {
	@Id
	@Column(name="systemid")
	@Length(max=50 , message="systemid最大长度为50")
	private String systemid;

	@Column(name="userid")
	@Length(max=50 , message="userid最大长度为50")
	private String userid;

	@Column(name="cn_name")
	@Length(max=50 , message="cn_name最大长度为50")
	private String cn_name;

	@Column(name="filename")
	@Length(max=50 , message="filename最大长度为50")
	private String filename;

	@Column(name="filepath")
	@Length(max=100 , message="filepath最大长度为100")
	private String filepath;

	@Column(name="sort")
	@Length(max=50 , message="sort最大长度为50")
	private String sort;

	@Column(name="tags")
	@Length(max=50 , message="tags最大长度为50")
	private String tags;

	@Column(name="cjsj")
	private Date cjsj;

	@Column(name="open")
	@Length(max=50 , message="open最大长度为50")
	private String open;

	@Column(name="html")
	@Length(max=50 , message="html最大长度为50")
	private String html;

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

	public String getCn_name() {
		return cn_name;
	}

	public void setCn_name(String cn_name) {
		this.cn_name = cn_name;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}


	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@Override
	public String toString() {
		return "Files [systemid=" + systemid + ", userid=" + userid + ", cn_name=" + cn_name + ", filename=" + filename
				+ ", filepath=" + filepath + ", sort=" + sort + ", tags=" + tags + ", cjsj=" + cjsj + ", open=" + open
				+ "]";
	}



}