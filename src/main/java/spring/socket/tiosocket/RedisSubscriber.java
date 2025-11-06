package spring.socket.tiosocket;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;
import spring.socket.config.Global;

public class RedisSubscriber {
    public RedisSubscriber(String serverId) {
        RedisClient redisClient = RedisClient.create("redis://" + Global.redisHost + ":" + Global.redisPort);
        StatefulRedisPubSubConnection<String, String> connection = redisClient.connectPubSub();

        connection.addListener(new RedisPubSubListener<String, String>() {
            @Override
            public void message(String channel, String message) {
                String[] parts = message.split("\\|", 2);
                String userId = parts[0];
                String text = parts[1];

                ChannelContext ctx = ClientManager.getLocalClient(userId);
                if (ctx != null) {
                    Tio.send(ctx, WsResponse.fromText(text, "UTF-8"));
                }
            }

            @Override
            public void message(String pattern, String channel, String message) {
            }

            @Override
            public void subscribed(String channel, long count) {
            }

            @Override
            public void psubscribed(String pattern, long count) {
            }

            @Override
            public void unsubscribed(String channel, long count) {
            }

            @Override
            public void punsubscribed(String pattern, long count) {
            }
        });

        connection.sync().subscribe("server:" + serverId);
    }
}