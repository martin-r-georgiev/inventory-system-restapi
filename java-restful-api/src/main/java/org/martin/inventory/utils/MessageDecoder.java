package org.martin.inventory.utils;

import com.google.gson.Gson;
import org.martin.inventory.model.Message;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

    private static Gson gson = new Gson();

    @Override
    public Message decode(String s) throws DecodeException {
        return gson.fromJson(s, Message.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
        // TODO: Delete method if not used
    }

    @Override
    public void destroy() {
        // Close resources
        // TODO: Delete method if not used
    }
}