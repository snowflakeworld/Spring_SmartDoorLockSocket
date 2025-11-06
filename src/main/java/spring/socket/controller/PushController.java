package spring.socket.controller;

import com.google.gson.JsonObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;
import spring.socket.config.Global;
import spring.socket.tiosocket.ClientManager;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/ws")
public class PushController {

    @PostMapping("/sendPrivate")
    public String sendPrivate(@Valid @RequestBody Map<String, Object> request) throws Exception {
        String cid = request.get("cid").toString();
        String type = request.get("type").toString();
        String data = request.get("data").toString();
        String message = request.get("message").toString();

        String targetServer = ClientManager.getUserServer(cid);

        if (targetServer == null) {
            return "User " + cid + " is offline";
        }

        JsonObject messageObj = new JsonObject();
        messageObj.addProperty("type", type);
        messageObj.addProperty("data", data);
        messageObj.addProperty("message", message);

        if (targetServer.equals(Global.socketServerId)) {
            // User connected to this server
            ChannelContext ctx = ClientManager.getLocalClient(cid);
            if (ctx != null) {
                WsResponse wsResponse = WsResponse.fromText(messageObj.toString(), "UTF-8");
                Tio.send(ctx, wsResponse);

                return "Message sent to user " + cid;
            }
        } else {
            // User connected to a different server
            // Here you can publish to Redis channel for that server to deliver the message
            ClientManager.redis.publish("server:" + targetServer, cid + "|" + messageObj.toString());
//            redisPublisher.publish(cid + "|" + messageObj.toString());
            return "Message forwarded to server " + targetServer;
        }

        return "User " + cid + " not connected";
    }

    @Scheduled(cron = "0 0/5 * * * ?") // Check socket clients alive state every 5 minutes
    public void checkClientsAlive() {
        ClientManager.checkClientAlive();
    }
}
