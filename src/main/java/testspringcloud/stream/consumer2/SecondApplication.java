package testspringcloud.stream.consumer2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@SpringBootApplication
@EnableBinding(value = {ReceiveService.class})
public class SecondApplication {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(SecondApplication.class).
				properties("spring.config.location=classpath:/springcloud/stream-consumer-second.yml").run(args);
	}

	@StreamListener("myInput")
	public void receive(byte[] msg) {
		System.out.println("Second Consumer 接收到的消息：  " + new String(msg));
	}

}
