package org.apache.rocketmq.example.filter;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @date 2020/9/16 17:24
 * @author chengjiaqing
 * @version : 0.1
 */ 
 
  
public class ClassFilterProducer {

    public static void main(String[] args) throws RemotingException, MQBrokerException, InterruptedException, UnsupportedEncodingException {
        //01new一个默认的message queue 生产者，一定要给它一个名字
        DefaultMQProducer producer = new DefaultMQProducer("libo-1");
        //02给这个生产者设置注册的地址,本地的9876port
        producer.setNamesrvAddr("localhost:9876");
        //03启动producer,启动不代表发送了消息
        try {
            producer.start();
            for(int i=0;i<100;i++) {
                //04准备要发送的Message，指定主题，标签，和消息
                Message msg = new Message("topic3", "TagA", ("李波"+i).getBytes("utf-8"));
                //05设置SwquenceId，用于过滤消息
                msg.putUserProperty("SequenceId", String.valueOf(i));
                //06因为发完消息后是需要有回馈的，发送结果（顾名思义）
                SendResult sendResult = producer.send(msg);
                System.out.println(msg);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        //06关闭producer
        producer.shutdown();

    }
}