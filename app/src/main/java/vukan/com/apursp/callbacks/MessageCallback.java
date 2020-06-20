package vukan.com.apursp.callbacks;

import java.util.List;

import vukan.com.apursp.models.Message;

public interface MessageCallback {
    void onCallback(List<Message> message);
}
