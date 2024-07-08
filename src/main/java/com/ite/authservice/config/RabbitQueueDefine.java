package com.ite.authservice.config;


import com.ite.authservice.constants.JobQueue;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueDefine {

    @Autowired
    @Qualifier("amqpAdmin")
    private AmqpAdmin rabbitAdminMain;

    @Bean
    public Queue lockAccountNoticeQueue(){
        return new Queue(JobQueue.LOCK_ACCOUNT_NOTICE_QUEUE,true);
    }
    @Bean
    public Queue createAdminAccountQueue(){ return new Queue(JobQueue.CREATE_ADMIN_ACCOUNT_QUEUE,false);}
    @Bean
    public DirectExchange emailExchange(){
        return new DirectExchange("email-exchange");
    }
    @Bean
    public DirectExchange adminAccountExchange(){ return  new DirectExchange("adminAccount-exchange");}

    @Bean
    public Binding bindingLockAccountNoticeQueueToEmailExchange(Queue lockAccountNoticeQueue, DirectExchange emailExchange){
        return BindingBuilder.bind(lockAccountNoticeQueue).to(emailExchange).with("lockAccountNotice.routing.key");
    }
    @Bean
    public Binding bindingCreateAdminAccountQueueToAdminAccountExchange(Queue createAdminAccountQueue, DirectExchange adminAccountExchange){
        return BindingBuilder.bind(createAdminAccountQueue).to(adminAccountExchange).with("createAdminAccount.routing.key");
    }



}
