package com.pycredit.publicservice.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pycredit.pojo.bean.Table;
import com.pycredit.pojo.bean.User;

@Controller
@RequestMapping("/proxy")
public class ProxyController {
	@ResponseBody
	@RequestMapping(value = "/sucessproxy", produces = "text/plain;charset=UTF-8")
	public String sucess(String text,HttpServletRequest request) {// 2
		System.out.println(text);
		return "成功";// 返回首页
	}
	@ResponseBody
	@RequestMapping(value = "/errorproxy", produces = "text/plain;charset=UTF-8")
	public String error(String text,HttpServletResponse response) {// 2
		System.out.println(text);
		String ip=getIp(text);
		PrintWriter writer=null;
		try {
			writer=response.getWriter();
			writer.append("gogogo!");
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			response.reset();
			if(null!=writer)
				writer.close();
		}
		return "失败";// 返回首页
	}
	@ResponseBody
	@RequestMapping(value = "/pojo", produces = "text/plain;charset=UTF-8")
	public String pojo(String tb,String usr) {// 2
		//System.out.println(tb.getTbName()+":"+usr.getUserName());
		System.out.println(tb);
		return "成功";// 返回首页
	}
	public String getIp(String text) {// 2
		System.out.println(text);
		return "127.0.0.1";// 返回首页
	}
}
