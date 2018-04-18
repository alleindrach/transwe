package com.transwe.web.dto;

public class BaseOutput {
	public  static final Integer SUCCESS=0;
	public  static final Integer FAILED=-1;
	private int state;
	private String code;
	private Object data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
