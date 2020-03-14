package cn.bysj.utils;

public class RetrurnModel {

	private String msg;
	private String code;
	private Object data;

	public RetrurnModel() {
		super();
	}

	public RetrurnModel(String code,String msg) {
		this.code=code;
		this.msg=msg;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}



}

