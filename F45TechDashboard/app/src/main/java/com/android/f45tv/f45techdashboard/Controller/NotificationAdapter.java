package com.android.f45tv.f45techdashboard.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.f45tv.f45techdashboard.R;

import java.util.List;

/*
* Recycler.Adapter
* Recycler.ViewHolder
* */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context context;
    private List<NotificationController> notificationList;

    public NotificationAdapter(Context context, List<NotificationController> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.notification, null);
        return new NotificationViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder{

        TextView priority, source, subject;
        public NotificationViewHolder(View itemView) {
            super(itemView);


        }
    }
}
