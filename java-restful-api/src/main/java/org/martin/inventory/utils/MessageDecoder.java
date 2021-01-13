package org.martin.inventory.utils;

import com.google.gson.Gson;
import org.martin.inventory.model.Message;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * A custom message decoder class that converts JSON string objects into Message class objects
 */
public class MessageDecoder implements Decoder.Text<Message> {

    private static Gson gson = new Gson();

    /**
     * Converts JSON string objects into Message class objects
     * @param json
     * @return Message Object
     * @throws DecodeException
     */
    @Override
    public Message decode(String json) throws DecodeException {
        return gson.fromJson(json, Message.class);
    }

    @Override
    public boolean willDecode(String json) {
        return (json != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        /* Custom decoder initialization
         * - This method is present as it is part of the Decoder interface implementation.
         * - This method is currently unused.
         */
    }

    @Override
    public void destroy() {
        /* Custom decoder resource cleanup
         * - This method is present as it is part of the Decoder interface implementation.
         * - This method is currently unused.
         */
    }
}