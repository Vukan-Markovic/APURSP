package vukan.com.apursp.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vukan.com.apursp.firebase.Storage;
import vukan.com.apursp.models.Comment;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {
    private Storage storage;
    private List<Comment> comments;

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    static class CommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public CommentsAdapter(List<Comment> comments) {

    }

    @NonNull
    @Override
    public CommentsAdapter.CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.CommentsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
