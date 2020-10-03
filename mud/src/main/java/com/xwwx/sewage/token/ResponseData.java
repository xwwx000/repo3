package com.xwwx.sewage.token;

import java.util.HashMap;
import java.util.Map;

/**
 * *接口响应封装
 * @author 可乐罐
 *
 */
public class ResponseData {
	public static final String ERRORS_KEY = "errormsg";
	private final String message;
	private final int code;
	private final Map<String, Object> data = new HashMap<String, Object>();

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	public Map<String, Object> getData() {
		return data;
	}
	
	public ResponseData putDataValue(String key, Object value) {
		data.put(key, value);
		return this;
	}
	
	private ResponseData(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static ResponseData ok() {
		return new ResponseData(200, "ok");
	}
	
	public static ResponseData error() {
		return new ResponseData(201, "error");
	}	
	
	public static ResponseData notFound() {
		return new ResponseData(404, "Not Found");
	}
	
	public static ResponseData badRequest() {
		return new ResponseData(400, "Bad Request");
	}
	
	public static ResponseData forbidden() {
		return new ResponseData(403, "Forbidden");
	}
	
	public static ResponseData unauthorized() {
		return new ResponseData(401, "unauthorized");
	}
	
	public static ResponseData serverInternalError() {
		return new ResponseData(500, "Server Internal Error");
	}
}
