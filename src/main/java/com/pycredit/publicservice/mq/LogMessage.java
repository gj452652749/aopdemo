package com.pycredit.publicservice.mq;

import java.io.Serializable;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONObject;
import com.pycredit.publicservice.mq.message.MqMessage;

public class LogMessage implements Serializable,MqMessage{
	transient int id;
	String callTime;
	String responseTime;
	String ip;
	String exceptionBody;
	String responseBody;
	String requestUrl;
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	
	public String getCallTime() {
		return callTime;
	}
	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getExceptionBody() {
		return exceptionBody;
	}
	public void setExceptionBody(String exceptionBody) {
		this.exceptionBody = exceptionBody;
	}
	
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	@Override
	public String toJsonStr() {
		// TODO Auto-generated method stub
		return new JSONObject().toJSONString(this);
	}
	
}
