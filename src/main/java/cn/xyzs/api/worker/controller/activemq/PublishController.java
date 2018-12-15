package cn.xyzs.api.worker.controller.activemq;

import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publish")
public class PublishController {
    @Autowired
    private JmsMessagingTemplate jms;
    @Autowired
    private ActiveMQQueue queue;
    @Autowired
    private ActiveMQTopic topic;
    @RequestMapping("/queue")
    public String queue(){
        for (int i = 0; i < 10 ; i++){
            jms.convertAndSend(queue, "queue"+i);
        }

        return "queue 发送成功";
    }

    @JmsListener(destination = "out.queue")
    public void consumerMsg(String msg){
        System.out.println(msg);
    }
    @RequestMapping("/topic")
    public String topic(){
        for (int i = 0; i < 10 ; i++){
            jms.convertAndSend(topic, "topic"+i);
        }
        return "topic 发送成功";
    }
}