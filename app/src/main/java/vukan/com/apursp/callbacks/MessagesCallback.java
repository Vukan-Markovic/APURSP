package vukan.com.apursp.callbacks;

import java.util.List;

import vukan.com.apursp.models.Conv;
import vukan.com.apursp.models.Message;

public interface MessagesCallback {
    void onCallback(List<Conv> conv);
}
