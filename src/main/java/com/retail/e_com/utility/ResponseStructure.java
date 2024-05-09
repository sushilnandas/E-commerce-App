package com.retail.e_com.utility;

import org.springframework.stereotype.Component;

@Component
public class ResponseStructure<T> {
	
	private int status;
	private String messgae;
	private T data;
	public int getStatus() {
		return status;
	}
	public ResponseStructure<T> setStatus(int status) {
		this.status = status;
		return this;
	}
	public String getMessgae() {
		return messgae;
	}
	public ResponseStructure<T> setMessgae(String messgae) {
		this.messgae = messgae;
		return this;
	}
	public T getData() {
		return data;
	}
	public ResponseStructure<T> setData(T data) {
		this.data = data;
		return this;
	}
	

}
