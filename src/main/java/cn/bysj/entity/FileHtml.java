package cn.bysj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="t_filehtml")
public class FileHtml {

	@Id
	@Column(name="systemid")
	@Length(max=50 , message="systemid最大长度为50")
	private String systemid;

	@Lob
	@Column(name="html")
	private String html;

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}



}