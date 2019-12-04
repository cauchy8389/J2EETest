package testspringcloud.stream.producer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface SendService {

	@Output("myInput")
	SubscribableChannel sendOrder();
	
	@Output("input")
	SubscribableChannel output();
}
