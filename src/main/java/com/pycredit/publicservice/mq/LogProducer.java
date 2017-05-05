package com.pycredit.publicservice.mq;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.pycredit.publicservice.mq.message.MqMessage;

public enum LogProducer {
	INSTANCE;
	DefaultMQProducer producer ;
	private final String [] topics;
	private LogProducer() {
		//初始化rocketMq连接资源
		producer = new DefaultMQProducer("please_rename_unique_group_name");

        //在本地搭建好broker后,记得指定nameServer的地址
        producer.setNamesrvAddr("127.0.0.1:9876");

        try {
			producer.start();
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         topics= new String[]{LogConfig.MqTaobaoTradeTopic, LogConfig.MqTmallTradeTopic};
	}
	public void produceLog(int no) {
		//logExecutor.submit(new LogRunnable(no));
	}
	//此处可以直接抛出RuntimeException("xxx")
	public void checkNo(int no) throws NoException{
		//System.out.println(no);
		if(no<5) throw new NoException();
	}
	public void doProduce(int no) throws NoException{
			checkNo(no);				
	}
	public void produceMessage(MqMessage message) {
		logExecutor.submit(new LogRunnable(message));
	}
	ExecutorService logExecutor = Executors.newSingleThreadExecutor();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++)
			LogProducer.INSTANCE.doProduce(new Random().nextInt(10));
	}
	class LogRunnable implements Runnable {
		MqMessage message;
		
		public LogRunnable(MqMessage message) {
			super();
			this.message = message;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			byte[] body =LogUtils.writeKryoObject(message);
			Message msgToBorker =new Message(LogConfig.MqPayTopic,body);
			try {
				producer.send(msgToBorker);
			} catch (MQClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemotingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MQBrokerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("bad No!	"+message);
		}
		
	}
}
