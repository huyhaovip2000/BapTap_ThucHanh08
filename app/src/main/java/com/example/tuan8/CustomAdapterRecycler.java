package com.example.tuan8;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapterRecycler extends RecyclerView.Adapter<CustomAdapterRecycler.ViewHolder> {
    List<User> mNames;
    private OnClickListner listener;
    public CustomAdapterRecycler(List<User> names, OnClickListner listener) {
        mNames = names;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = mNames.get(position);
        holder.tvName.setText(user.getFirstName()+" "+user.getLastName());
        holder.mUser=  user;

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        User mUser;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.itemClicklistener(mUser);
                }
            });
        }
    }
    public void resetList(List<User> users) {
        mNames = users;
        notifyDataSetChanged();
    }
}
