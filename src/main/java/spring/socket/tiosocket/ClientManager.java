package spring.socket.tiosocket;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.tio.core.ChannelContext;
import spring.socket.config.Global;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ClientManager {

    private static final ConcurrentHashMap<String, ChannelContext> localClients = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Long> localClientsTimestamp = new ConcurrentHashMap<>();

    // Redis
    private static final RedisClient redisClient = RedisClient.create("redis://" + Global.redisHost + ":" + Global.redisPort);
    private static final StatefulRedisConnection<String, String> connection = redisClient.connect();
    public static final RedisCommands<String, String> redis = connection.sync();

    public static void addClient(String userId, ChannelContext ctx, String serverId) {
        localClients.put(userId, ctx);
        localClientsTimestamp.put(userId, System.currentTimeMillis());
        redis.set("user:" + userId, serverId);
    }

    public static void removeClient(String userId) {
        localClients.remove(userId);
        localClientsTimestamp.remove(userId);
        redis.del("user:" + userId);
    }

    public static void checkClients(String userId, ChannelContext ctx, String serverId) {
        if (!localClients.containsKey(userId)) {
            addClient(serverId, ctx, userId);
        }
    }

    public static void updateClientAlive(String userId, long timestamp) {
        localClientsTimestamp.put(userId, timestamp);
    }

    public static ChannelContext getLocalClient(String userId) {
        return localClients.get(userId);
    }

    public static Long getLocalClientsTimestamp(String userId) {
        return localClientsTimestamp.get(userId);
    }

    public static String getUserServer(String userId) {
        return redis.get("user:" + userId);
    }

    public static void close() {
        connection.close();
        redisClient.shutdown();
    }

    public static void checkClientAlive() {
        List<String> userIdArr = new ArrayList<>();
        for (String userId : localClients.keySet()) {
            if (localClientsTimestamp.get(userId) + Global.aliveCheckTimeout < System.currentTimeMillis()) {
                userIdArr.add(userId);
            }
        }
        for (String userId : userIdArr) {
            removeClient(userId);
        }

        System.out.println("Cron removed " + userIdArr.size() + " clients.");
    }
}