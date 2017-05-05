package org.springframework.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.alibaba.rocketmq.shade.io.netty.handler.codec.http.HttpResponseStatus;

public class ResponseWrapper extends HttpServletResponseWrapper {
	 private PrintWriter cachedWriter;
	 private CharArrayWriter bufferedWriter;
	 
	 public ResponseWrapper(HttpServletResponse response) {
	  super(response);
	  // 这个是我们保存返回结果的地方
	  bufferedWriter = new CharArrayWriter();
	  // 这个是包装PrintWriter的，让所有结果通过这个PrintWriter写入到bufferedWriter中
	  cachedWriter = new PrintWriter(bufferedWriter);
	 }
	 
	 @Override
	 public PrintWriter getWriter() {
	  return cachedWriter;
	 }
	 
	 /**
	  * 获取原始的HTML页面内容。
	  * @return
	  */
	 public String getResult() {
	  return bufferedWriter.toString();
	 }
	}
