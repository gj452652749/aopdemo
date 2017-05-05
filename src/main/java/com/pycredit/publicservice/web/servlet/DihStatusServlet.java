package com.pycredit.publicservice.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DihStatusServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // 设置contentType、关闭缓存
        res.setContentType("text/plain;charset=UTF-8");
        res.setHeader("Cache-Control", "private");
        res.setHeader("Pragma", "no-cache");
        // 原始请求可以做一些简单业务的处理
        final PrintWriter writer = res.getWriter();
        writer.println("老板检查当前需要做的工作");
        writer.flush();
        // jobs表示需要做的工作，使用循环模拟初始化
        List<String> jobs = new ArrayList<>();
        for(int i=0;i<10;i++){
            jobs.add("job"+i);
        }
        // 使用request的startAsync方法开启异步处理
        final AsyncContext ac = req.startAsync();
        // 具体处理请求，内部处理启用了新线程，不会阻塞当前线程
        doWork(ac, jobs);
        writer.println("老板布置完工作就走了");
        writer.flush();
    }

    private void doWork(final AsyncContext ac, final List<String> jobs){
        // 设置超时时间1小时
        ac.setTimeout(1*60*60*1000L);
        // 使用新线程具体处理请求 
        ac.start(new Runnable() {
            @Override
            public void run() {
                try {
                  // 从AsyncContext获取到Response进而获取到Writer
                    PrintWriter w = ac.getResponse().getWriter();
                    for(String job:jobs){
                        w.println("\""+job+"\"请求处理中。。。");
                        Thread.sleep(1 * 1000L); 
                        w.flush();
                    }
                  // 发出请求处理完成通知
                    ac.complete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
