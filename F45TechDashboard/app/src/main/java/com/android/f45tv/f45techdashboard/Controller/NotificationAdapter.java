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
        View view = layoutInflater.inflate(R.layout.notification,null);
        return new NotificationViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {

        NotificationController notificationController = notificationList.get(position);
        int priority = notificationController.getPriority();
        int source = notificationController.getSource();

        if (priority == 1){
            holder.priority.setText("Low");
        } else if (priority == 2){
            holder.priority.setText("Medium");
        } else if (priority == 3){
            holder.priority.setText("High");
        } else if (priority == 4){
            holder.priority.setText("Urgent");
        }

        if (source == 1){
            holder.source.setText("Email");
        } else if (source == 2){
            holder.source.setText("Portal");
        } else if (source == 3){
            holder.source.setText("Phone");
        } else if (source == 7){
            holder.source.setText("Chat");
        } else if (source == 8){
            holder.source.setText("Mobihelp");
        } else if (source == 9){
            holder.source.setText("Feedback Widget");
        } else if (source == 10){
            holder.source.setText("Outbound Email");
        }
        holder.subject.setText(notificationController.getSubject());


    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder{

        TextView priority, source, subject;
        public NotificationViewHolder(View itemView) {
            super(itemView);

            priority = itemView.findViewById(R.id.priority_entry);
            source = itemView.findViewById(R.id.source_entry);
            subject = itemView.findViewById(R.id.subject_entry);
            itemView.invalidate();
        }
    }
}
