package testspringcloud.stream.consumer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding(value = {ReceiveService.class, Sink.class})
public class ReceiverApplication {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ReceiverApplication.class).
				properties("spring.config.location=classpath:/springcloud/stream-consumer.yml").run(args);
	}

	@StreamListener("myInput")
	public void receive(byte[] msg) {
		System.out.println("接收到的消息：  " + new String(msg));
	}
	
	@StreamListener(Sink.INPUT)
	public void receiveInput(byte[] msg) {
		System.out.println("receiveInput方法，接收到的消息：  " + new String(msg));
	}
}
