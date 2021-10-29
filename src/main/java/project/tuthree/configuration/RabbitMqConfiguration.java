package project.tuthree.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Named;


@Configuration
public class RabbitMqConfiguration {

    /** rabbitMQ */
    private final String chatQueue = "chat";
    private final String chatExchange = "chat-exchange";
    private final String routingkey = "messages.*";

    private final String keywordQueue = "keyword";
    private final String keywordExchange = "keyword-exchange";
    private final String keywrodRoutingkey = "keyword.*";

    @Bean(name = "chat")
    Queue chatQueue() {
        return new Queue(chatQueue, true, false, false);
    }

    @Bean(name = "keyword")
    Queue keywordQueue() {
        return new Queue(keywordQueue, true, false, false);}

    @Bean(name = "chatTopicExchange")
    TopicExchange topicExchange() {
        return new TopicExchange(chatExchange);
    }

    @Bean(name = "keywordTopicExchange")
    TopicExchange keywordExchange() {
        return new TopicExchange(keywordExchange);
    }

    @Bean
    Binding chatQueueBinding(@Named("chat") Queue queue, @Named("chatTopicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }

    @Bean
    Binding keywordQueueBinding(@Named("keyword") Queue queue, @Named("keywordTopicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(keywrodRoutingkey);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(factory);
        rabbitTemplate.setMessageConverter(messageConverter);

        return rabbitTemplate;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUsername("tuthree1234");
        factory.setPassword("1234");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5672);
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory factory) {
        return new RabbitAdmin(factory);
    }

}
