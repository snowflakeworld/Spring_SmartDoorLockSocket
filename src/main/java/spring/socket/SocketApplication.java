package spring.socket;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.tio.websocket.server.WsServerStarter;
import spring.socket.config.EnvConfig;
import spring.socket.config.Global;
import spring.socket.tiosocket.MyMsgHandler;
import spring.socket.tiosocket.RedisSubscriber;

@SpringBootApplication
@EnableScheduling
public class SocketApplication implements CommandLineRunner {

    public WsServerStarter starter;

    private final EnvConfig envConfig;

    public SocketApplication(EnvConfig envConfig) {
        this.envConfig = envConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(SocketApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Global.redisHost = envConfig.getRedisHost();
        Global.redisPort = envConfig.getRedisPort();
        Global.socketServerId = envConfig.getServerId();
        Global.appServerId = envConfig.getPublishServerId();
        Global.aliveCheckTimeout = envConfig.getAliveCheckTimeout();

        starter = new WsServerStarter(envConfig.getTioPort(), new MyMsgHandler());
        starter.start();
        System.out.println("✅ WebSocket server started on ws://localhost:8083/smart_socket");

        new RedisSubscriber(Global.socketServerId);
    }
}
