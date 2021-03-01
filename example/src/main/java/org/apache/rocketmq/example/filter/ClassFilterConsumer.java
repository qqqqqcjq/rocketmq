package org.apache.rocketmq.example.filter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @date 2020/9/16 17:23
 * @author chengjiaqing
 * @version : 0.1
 */ 
 
  
public class ClassFilterConsumer {

    public static void main(String[] args) throws MQClientException, IOException {
        //01new 一个默认的消息消费者push的
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("xiao");
        //02注册
        consumer.setNamesrvAddr("localhost:9876");
        //03打印过滤器文件
        String filterCode = MixAll.file2String("D:\\ideaWPGit\\rocketmq\\example\\src\\main\\java\\org\\apache\\rocketmq\\example\\filter\\MessageFilter");
        System.out.println(filterCode);
        //04订阅,将过滤器代码传给第三个参数，第二个参数为过滤器类的包名点类名
        consumer.subscribe("topic66","exercise1.filter.MessageFilterImpl",filterCode);
        //05从哪里开始取消息
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //06消费者注册监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                try {
                    String str = new String(msgs.get(0).getBody(),"utf-8");
                    System.out.println(str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //07启动消费者
        consumer.start();
        System.out.println("consumer started .. ");
    }
}