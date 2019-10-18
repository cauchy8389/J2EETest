package testspringcloud.stream.producer;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import testspringcloud.stream.producer.entity.Bar2;
import testspringcloud.stream.producer.entity.Foo2;

@Component
@KafkaListener(id = "multiGroup", topics = { "foos", "bars" })
public class MultiMethods {


    @KafkaHandler
    public void foo(Foo2 foo) {
        System.out.println("Received: " + foo);
    }

    @KafkaHandler
    public void bar(Bar2 bar) {
        System.out.println("Received: " + bar);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        System.out.println("Received unknown: " + object);
    }
}
