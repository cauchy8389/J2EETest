package testspringcloud.stream.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import testspringcloud.stream.producer.entity.Bar1;
import testspringcloud.stream.producer.entity.Foo1;

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

	@Autowired
	private KafkaTemplate<Object, Object> template;

	@GetMapping(path = "/send/foo/{what}")
	public void sendFoo(@PathVariable String what) {
		this.template.send("foos", new Foo1(what));
	}

	@GetMapping(path = "/send/bar/{what}")
	public void sendBar(@PathVariable String what) {
		this.template.send("bars", new Bar1(what));
	}

	@GetMapping(path = "/send/unknown/{what}")
	public void sendUnknown(@PathVariable String what) {
		this.template.send("bars", what);
	}

    @GetMapping(path = "/send/foos/{what}")
    public void sendFooGroup(@PathVariable String what) {
        this.template.executeInTransaction(kafkaTemplate -> {
            StringUtils.commaDelimitedListToSet(what).stream()
                    .map(s -> new Foo1(s))
                    .forEach(foo -> kafkaTemplate.send("topic2", foo));
            return null;
        });
    }
}
