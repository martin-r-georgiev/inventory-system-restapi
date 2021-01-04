package org.martin.inventory.websockets;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.martin.inventory.model.Message;
import org.martin.inventory.utils.MessageDecoder;
import org.martin.inventory.utils.MessageEncoder;

public class ChatWebSocket extends WebSocketApplication {

    public static final Set<WebSocket> sockets = Collections.synchronizedSet(new HashSet<>());
    public static HashMap<String, String> users = new HashMap<>();

    @Override
    public void onConnect(WebSocket socket) {
        sockets.add(socket);
        System.out.println("New user joined connected!");
        super.onConnect(socket);
    }

    @Override
    public void onMessage(WebSocket current, String text) {
        synchronized (sockets) {
            sockets.forEach(socket -> {
                if (socket.isConnected()) {
                    socket.send(text);
                }
            });
        }
    }

    @Override
    public void onMessage(WebSocket socket, byte[] bytes) {
        socket.send(bytes);
    }
}

//@ServerEndpoint(
//        value = "/chat/",
//        decoders = MessageDecoder.class,
//        encoders = MessageEncoder.class )
//public class ChatEndpoint {
//
//    private Session session;
//    private static Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
//    private static HashMap<String, String> users = new HashMap<>();
//
////    @PathParam("username") String username
//
//    @OnOpen
//    public void onOpen(
//            Session session) throws IOException, EncodeException {
//
//        this.session = session;
//        chatEndpoints.add(this);
//        users.put(session.getId(), "Martin");
//
//        Message message = new Message();
//        message.setAuthor("Martin");
//        message.setContent("Connected!");
//        broadcast(message);
//    }
//
//    @OnMessage
//    public void onMessage(Session session, Message message)
//            throws IOException, EncodeException {
//
//        message.setAuthor(users.get(session.getId()));
//        broadcast(message);
//    }
//
//    @OnClose
//    public void onClose(Session session) throws IOException, EncodeException {
//
//        chatEndpoints.remove(this);
//        Message message = new Message();
//        message.setAuthor(users.get(session.getId()));
//        message.setContent("Disconnected!");
//        broadcast(message);
//    }
//
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        // Used for error handling
//        // TODO: Delete method if not used
//    }
//
//    private static void broadcast(Message message)
//            throws IOException, EncodeException {
//
//        chatEndpoints.forEach(endpoint -> {
//            synchronized (endpoint) {
//                try {
//                    endpoint.session.getBasicRemote().
//                            sendObject(message);
//                } catch (IOException | EncodeException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//}
