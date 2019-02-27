// Adapted from the example given in this tutorial: https://www.youtube.com/watch?v=Vyqz_-sJGFk
package com.example.rpsls;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mUsers;
    private Context mContext;


    public RecyclerViewAdapter(Context context, ArrayList<String> targetUsers) {
        mUsers = targetUsers;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called.");

        holder.targetUser.setText(mUsers.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Clicked on." + mUsers.get(position));
                Toast.makeText(mContext, mUsers.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView targetUser;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            targetUser = itemView.findViewById(R.id.user_placeholder);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
