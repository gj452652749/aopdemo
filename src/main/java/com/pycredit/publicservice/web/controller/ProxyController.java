package com.pycredit.publicservice.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/proxy")
public class ProxyController {
	@ResponseBody
	@RequestMapping(value = "/proxy", produces = "text/plain;charset=UTF-8")
	public String db(String text) {// 2
		System.out.println(text);
		return "成功";// 返回首页
	}
}
