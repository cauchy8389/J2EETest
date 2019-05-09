package testspringcloud.stream.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

	@Autowired
	SendService sendService;


	@RequestMapping(value = "/send", method = RequestMethod.GET)
	@ResponseBody
	public String sendRequest() {
		// 创建消息
		Message msg = MessageBuilder.withPayload("Hello World".getBytes()).build();
		// 发送消息	
		sendService.sendOrder().send(msg);
		return "SUCCESS";
	}
	
	@RequestMapping(value = "/test-source", method = RequestMethod.GET)
	@ResponseBody
	public String testSource() {
		// 创建消息
		Message msg = MessageBuilder.withPayload("Hello World 2".getBytes()).build();
		sendService.output().send(msg);
		return "SUCCESS";
	}
}
