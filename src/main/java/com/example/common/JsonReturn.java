package com.example.common;

import com.google.gson.Gson;

public class JsonReturn {
	
	private int code;
	private String message;
	
	public JsonReturn(){
		super();
	}
	
	public int getCode() {
		return code;
	}
	public JsonReturn setCode(int code) {
		this.code = code;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public JsonReturn setMessage(String message) {
		this.message = message;
		return this;
	}
	
	@Override
	public String toString () {
		Gson gson = new Gson();
        return gson.toJson(this);
	}
}
