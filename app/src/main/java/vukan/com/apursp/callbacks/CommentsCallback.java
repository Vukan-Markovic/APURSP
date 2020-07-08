package vukan.com.apursp.callbacks;

import java.util.List;

import vukan.com.apursp.models.Comment;

public interface CommentsCallback {
    void onCallback(List<Comment> comments);
}