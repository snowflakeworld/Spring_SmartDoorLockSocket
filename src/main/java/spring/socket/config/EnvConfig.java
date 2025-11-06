package spring.socket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    @Value("${tio.server_id}")
    private String serverId;
    @Value("${tio.publish_server_id}")
    private String publishServerId;
    @Value("${tio.host}")
    private String host;
    @Value("${tio.port}")
    private int tioPort;
    @Value("${tio.ws_uri}")
    private String wsUri;
    @Value("${tio.alive_check_timeout}")
    private Long aliveCheckTimeout;
    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private String redisPort;

    public String getServerId() {
        return serverId;
    }

    public String getPublishServerId() {
        return publishServerId;
    }

    public String getHost() {
        return host;
    }

    public int getTioPort() {
        return tioPort;
    }

    public String getWsUri() {
        return wsUri;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public Long getAliveCheckTimeout() {
        return aliveCheckTimeout;
    }
}
