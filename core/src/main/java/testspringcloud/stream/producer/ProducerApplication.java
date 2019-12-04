package testspringcloud.stream.producer;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.*;
import testspringcloud.stream.producer.entity.Bar2;
import testspringcloud.stream.producer.entity.Foo2;

import java.util.HashMap;
import java.util.Map;


@EnableEurekaClient
@EnableBinding(value = {SendService.class, Source.class})
@SpringBootApplication(excludeName={"com.github.trang.druid.autoconfigure.DruidAutoConfiguration",
        "com.github.trang.druid.autoconfigure.DruidDataSourceInitializerAutoConfiguration",
        "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
        "org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"})
public class ProducerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ProducerApplication.class).
				properties("spring.config.location=classpath:/springcloud/stream-producer.yml").run(args);
	}

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory,
            KafkaTemplate<Object, Object> template) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory<Object, Object> thekafkaConsumerFactory = kafkaConsumerFactory;
        configurer.configure(factory, kafkaConsumerFactory);
        factory.setErrorHandler(
                new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template))
        );
                //new DeadLetterPublishingRecoverer(template), new FixedBackOff(0L, 2)));

        //事务相关的用以下
//        factory.setBatchListener(true);
//        factory.setMessageConverter(batchConverter());
        return factory;
    }

    @Bean
    public RecordMessageConverter converter() {
        StringJsonMessageConverter converter = new StringJsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("testspringcloud.stream.producer.entity");
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("foo", Foo2.class);
        mappings.put("bar", Bar2.class);
        typeMapper.setIdClassMapping(mappings);
        converter.setTypeMapper(typeMapper);
        return converter;
    }

    //事务相关的用以下
//    @Bean
//    public RecordMessageConverter converter() {
//        return new StringJsonMessageConverter();
//    }
//
//    @Bean
//    public BatchMessagingMessageConverter batchConverter() {
//        return new BatchMessagingMessageConverter(converter());
//    }

    @Bean
    public NewTopic foos() {
        return new NewTopic("foos", 1, (short) 1);
    }

    @Bean
    public NewTopic bars() {
        return new NewTopic("bars", 1, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        //return TopicBuilder.name("topic2").partitions(1).replicas(1).build();
        return new NewTopic("topic2", 1, (short) 1);
    }

    @Bean
    public NewTopic topic3() {
        //return TopicBuilder.name("topic3").partitions(1).replicas(1).build();
        return new NewTopic("topic3", 1, (short) 1);
    }
}
