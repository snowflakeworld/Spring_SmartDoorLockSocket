package spring.socket.tiosocket;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;

@Service
public class RedisStreamSubscriber implements StreamListener<String, MapRecord<String, String, String>> {

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        System.out.println("📩 Received message from stream: " + message.getValue());
        String[] parts = message.getValue().toString().split("\\|", 2);
        String userId = parts[0];
        String text = parts[1];

        ChannelContext ctx = ClientManager.getLocalClient(userId);
        if (ctx != null) {
            Tio.send(ctx, WsResponse.fromText(text, "UTF-8"));
        }
    }
}
