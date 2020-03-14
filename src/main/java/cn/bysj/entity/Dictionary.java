package cn.bysj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="t_dictionary")
public class Dictionary {

	@Id
	@Column(name="id")
	@Length(max=50 , message="id最大长度为50")
	private String id;

	@Column(name="kind")
	@Length(max=50 , message="kind最大长度为50")
	private String kind;

	@Column(name="pid")
	@Length(max=50 , message="pid最大长度为50")
	private String pid;

	@Column(name="kind_code")
	@Length(max=50 , message="kind_code最大长度为50")
	private String kind_code;

	@Column(name="detail")
	@Length(max=50 , message="detail最大长度为50")
	private String detail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getKind_code() {
		return kind_code;
	}

	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "Dictionary [id=" + id + ", kind=" + kind + ", pid=" + pid + ", kind_code=" + kind_code + ", detail="
				+ detail + "]";
	}



}