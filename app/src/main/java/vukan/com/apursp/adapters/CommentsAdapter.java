package vukan.com.apursp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vukan.com.apursp.R;
import vukan.com.apursp.firebase.Storage;
import vukan.com.apursp.models.Comment;
import vukan.com.apursp.repository.Repository;
import vukan.com.apursp.ui.my_ads.MyAdsFragment;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {
    private Storage storage;
    private Repository repository;
    private List<Comment> comments;

    public CommentsAdapter(List<Comment> comments, MyAdsFragment myAdsFragment){
        this.comments=comments;
        storage=new Storage();
        repository=new Repository();
        storage=new Storage();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType)
    {
        return new CommentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView comment;
        RatingBar ratingBar;

        CommentsViewHolder(@NonNull View itemView){
            super(itemView);
            comment=itemView.findViewById(R.id.comment);
            ratingBar=itemView.findViewById(R.id.ratingBar);
        }
        void bind(int index){
            comment.setText(comments.get(index).getComment());
            ratingBar.setRating(comments.get(index).getGrade());
        }

        @Override
        public void onClick(View view) {

        }
    }

}
