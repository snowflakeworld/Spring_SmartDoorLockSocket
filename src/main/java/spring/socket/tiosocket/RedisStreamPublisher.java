package spring.socket.tiosocket;

import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.stereotype.Service;

//@Service
public class RedisStreamPublisher {
//    private final RedisTemplate<String, Object> redisTemplate;
//    private static final String STREAM_KEY = "server_app";
//
//    public RedisStreamPublisher(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    public void publish(String message) {
//        StreamOperations<String, Object, Object> ops = redisTemplate.opsForStream();
//        ObjectRecord<String, String> record = ObjectRecord.create(STREAM_KEY, message);
//        RecordId id = ops.add(record);
//        System.out.println("📤 Published to stream [" + STREAM_KEY + "] ID=" + id + " message=" + message);
//    }
}
