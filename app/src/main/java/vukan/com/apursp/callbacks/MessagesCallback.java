package vukan.com.apursp.callbacks;

import java.util.List;

import vukan.com.apursp.models.Conv;

public interface MessagesCallback {
    void onCallback(List<Conv> conv);
}
