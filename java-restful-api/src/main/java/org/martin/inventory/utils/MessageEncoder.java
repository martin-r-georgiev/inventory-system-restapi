package org.martin.inventory.utils;

import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.martin.inventory.model.Message;

/**
 * A custom message encoder class that converts Message class objects into JSON string objects
 */
public class MessageEncoder implements Encoder.Text<Message> {

    private static Gson gson = new Gson();

    /**
     * Converts Message class objects into JSON string objects
     * @param message
     * @return JSON String
     * @throws EncodeException
     */
    @Override
    public String encode(Message message) throws EncodeException {
        return gson.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        /* Custom encoder initialization
         * - This method is present as it is part of the Encoder interface implementation.
         * - This method is currently unused.
         */
    }

    @Override
    public void destroy() {
        /* Custom encoder resource cleanup
         * - This method is present as it is part of the Encoder interface implementation.
         * - This method is currently unused.
         */
    }
}