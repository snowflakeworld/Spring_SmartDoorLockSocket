package spring.socket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStreamCommands;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import spring.socket.tiosocket.RedisStreamSubscriber;

import javax.annotation.PostConstruct;
import java.time.Duration;

//@Configuration
public class RedisStreamConfig {
    /*private final RedisConnectionFactory connectionFactory;
    private final RedisStreamSubscriber subscriber;
    private static final String STREAM_KEY = "server_socket";
    private static final String GROUP_NAME = "server_socket_group";
    private static final String CONSUMER_NAME = "consumer_socket";

    public RedisStreamConfig(RedisConnectionFactory connectionFactory, RedisStreamSubscriber subscriber) {
        this.connectionFactory = connectionFactory;
        this.subscriber = subscriber;
    }

    @PostConstruct
    public void createGroupIfNotExists() {
        RedisStreamCommands commands = connectionFactory.getConnection().streamCommands();
        try {
            commands.xGroupCreate(STREAM_KEY.getBytes(), GROUP_NAME, ReadOffset.latest());
            System.out.println("✅ Created consumer group: " + GROUP_NAME);
        } catch (Exception e) {
            System.out.println("ℹ️ Group may already exist: " + e.getMessage());
        }
    }

    @Bean
    public Subscription subscription() {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .<String, String>builder()
                .pollTimeout(Duration.ofSeconds(2))
                .build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container = StreamMessageListenerContainer.create(connectionFactory, options);

        Subscription subscription = container.receiveAutoAck(
                org.springframework.data.redis.connection.stream.Consumer.from(GROUP_NAME, CONSUMER_NAME),
                StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed()),
                subscriber
        );

        container.start();
        return subscription;
    }
*/

}
