package org.martin.inventory.websockets;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import org.martin.inventory.model.Message;
import org.martin.inventory.utils.MessageDecoder;
import org.martin.inventory.utils.MessageEncoder;

@ServerEndpoint(
        value = "/chat/{username}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class)
public class ChatEndpoint {

    private Session session;
    private static Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("username") String username) throws IOException, EncodeException {

        this.session = session;
        chatEndpoints.add(this);
        users.put(session.getId(), username);

        Message message = new Message();
        message.setAuthor(username);
        message.setContent("Connected!");
        message.setTimestamp(Instant.now().getEpochSecond() * 1000);
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, Message message)
            throws IOException, EncodeException {

        message.setAuthor(users.get(session.getId()));
        message.setTimestamp(Instant.now().getEpochSecond() * 1000);
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {

        chatEndpoints.remove(this);
        Message message = new Message();
        message.setAuthor(users.get(session.getId()));
        message.setContent("Disconnected!");
        message.setTimestamp(Instant.now().getEpochSecond() * 1000);
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) { }

    private static void broadcast(Message message)
            throws IOException, EncodeException {

        System.out.println("Broadcast: " + message.getAuthor() + " Message:" + message.getContent());

        synchronized (chatEndpoints) {
            chatEndpoints.forEach(endpoint -> {
                try {
                    endpoint.session.getBasicRemote().
                            sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
