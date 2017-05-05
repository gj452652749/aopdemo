package com.pycredit.publicservice.xml.aop;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pycredit.publicservice.mq.LogMessage;
import com.pycredit.publicservice.mq.LogProducer;
import com.pycredit.publicservice.mq.message.MqMessage;

@Component
@Aspect
public class LogAspect {
	public LogAspect() {
		// TODO Auto-generated constructor stub
		System.out.println("going on: "+this.getClass().getClassLoader());
	}
	public void pointCut() {
		System.out.println("sss");
	}

//	@After("pointCut()")
//	public void after(JoinPoint joinPoint) {
//		System.out.println("after aspect executed");
//	}
//
//	@Before("pointCut()")
//	public void before(JoinPoint joinPoint) {
//		//如果需要这里可以取出参数进行处理
//		//Object[] args = joinPoint.getArgs();
//		System.out.println("before aspect executing");
//	}
//
//	@AfterReturning(pointcut = "pointCut()", returning = "returnVal")
//	public void afterReturning(JoinPoint joinPoint, Object returnVal) {
//		System.out.println("afterReturning executed, return result is "
//				+ returnVal);
//	}

	public String around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("xml around start..");
		Date callTime=new Date();
		long begin=System.currentTimeMillis();
		LogMessage message =new LogMessage();
		HttpServletRequest request=null;
		String responseBody=null;
		String exceptionBody=null;
		//找到入参中的request
		Object[] paraValues=pjp.getArgs();
		for(Object ele:paraValues) {
			if(ele instanceof HttpServletRequest) {
				request=(HttpServletRequest) ele;
				break;
			}
		}
		try {
			responseBody= (String) pjp.proceed();
		} catch (Throwable ex) {
			System.out.println("error in around");
			exceptionBody=ex.getMessage();
			throw ex;
		}finally{
		message.setCallTime(callTime.toString());
		if(null!=request)
			message.setIp(AgentInfo.getRemoteAddrIp(request));
		message.setResponseBody(responseBody);
		message.setExceptionBody(exceptionBody);
		message.setResponseTime(String.valueOf(System.currentTimeMillis()-begin));
		message.setRequestUrl(request.getRequestURL().toString()+"?"+request.getQueryString());
		LogProducer.INSTANCE.produceMessage(message);
		System.out.println("around end");
		}
		return responseBody;
	}

//	@AfterThrowing(pointcut = "pointCut()", throwing = "error")
//	public void afterThrowing(JoinPoint jp, Throwable error) {
//		System.out.println("error:" + error);
//	}
	static class AgentInfo {
		public static String getRemoteAddrIp(HttpServletRequest request) {
	        String ipFromNginx = getHeader(request, "X-Real-IP");
	        //System.out.println("ipFromNginx:" + ipFromNginx);
	        //System.out.println("getRemoteAddr:" + request.getRemoteAddr());
	        return (null==ipFromNginx) ? request.getRemoteAddr() : ipFromNginx;
	    }
		private static String getHeader(HttpServletRequest request, String headName) {
	        String value = request.getHeader(headName);
	        return !StringUtils.isEmpty(value) && !"unknown".equalsIgnoreCase(value) ? value : null;
	    }
	}
}