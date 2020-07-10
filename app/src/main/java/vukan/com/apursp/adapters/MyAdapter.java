package vukan.com.apursp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import vukan.com.apursp.models.Conv;
import vukan.com.apursp.ui.user_messages.UserMessagesFragment;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

  ArrayList<Conv> list;
  Context context;

  public MyAdapter(ArrayList<Conv> list, Context context)

  {
    this.list=list;
    this.context=context;
  }
  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
