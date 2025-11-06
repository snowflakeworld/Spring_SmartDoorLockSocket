package spring.socket.tiosocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;
import spring.socket.config.Global;

@Service
public class MyMsgHandler implements IWsMsgHandler {
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) {
        String userId = (String) channelContext.getAttribute("userId");
        if (userId != null) {
            ClientManager.removeClient(userId);
        }
        return null;
    }

    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) {
        String userId = (String) channelContext.getAttribute("userId");
        System.out.println("Message Arrived: " + userId + " " + text);
        ClientManager.checkClients(userId, channelContext, Global.socketServerId);

        try {
            Gson gson = new Gson();
            JsonObject msgObject = gson.fromJson(text, JsonObject.class);
            String type = msgObject.get("type").getAsString();

            if (type.equals("ping")) {
                ClientManager.updateClientAlive(userId, System.currentTimeMillis());

                JsonObject respObj = new JsonObject();
                respObj.addProperty("type", "pong");
                Tio.send(channelContext, WsResponse.fromText(respObj.toString(), "UTF-8"));


                ClientManager.redis.publish("server:" + Global.appServerId, userId + "|" + text);
//                redisPublisher.publish(userId + "|" + text);
            } else if (type.equals("batteryInfo")) {
                ClientManager.redis.publish("server:" + Global.appServerId, userId + "|" + text);
//                redisPublisher.publish(userId + "|" + text);
            } else if (type.equals("openHistory")) {
                ClientManager.redis.publish("server:" + Global.appServerId, userId + "|" + text);
//                redisPublisher.publish(userId + "|" + text);
            }
        } catch (Exception ignored) {

        }
        return null;
    }

    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
//        String userId = httpRequest.getHeader("userId");
        String userId = httpRequest.getParam("userId"); // e.g., ws://host:port/mysocket?userId=123
        if (userId != null) {
            ClientManager.addClient(userId, channelContext, Global.socketServerId);
            channelContext.setAttribute("userId", userId);
        }
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        System.out.println(httpRequest.getConnection());
    }

    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) {
        return null;
    }


}
