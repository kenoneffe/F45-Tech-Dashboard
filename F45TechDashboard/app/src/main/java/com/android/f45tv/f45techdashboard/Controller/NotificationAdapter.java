package com.android.f45tv.f45techdashboard.Controller;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.f45tv.f45techdashboard.R;

import java.util.ArrayList;
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


    public void setNotificationList(List<NotificationController> notificationList) {
        this.notificationList = notificationList;
        notifyDataSetChanged();
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.notification,parent, false);
        return new NotificationViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final NotificationViewHolder holder, final int position) {

        NotificationController notificationController = notificationList.get(position);
        String priority = notificationController.getPriority();
        String source = notificationController.getSource();
        int id = notificationController.getId();
        holder.setIsRecyclable(true);

        holder.priority.setText(notificationController.getPriority());
        holder.priority.invalidate();
        holder.source.setText(notificationController.getSource());
        holder.source.invalidate();
        holder.subject.setText(notificationController.getSubject());
        holder.subject.invalidate();

        holder.row_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.row_entry.setBackground(Drawable.createFromPath("@drawable/layout_roundedclicked"));

            }
        });


    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder{

        TextView priority, source, subject;
        TableRow row_entry;
        RecyclerView rv;
        public NotificationViewHolder(View itemView) {
            super(itemView);
            priority = itemView.findViewById(R.id.priority_entry);
            source = itemView.findViewById(R.id.source_entry);
            subject = itemView.findViewById(R.id.subject_entry);
            row_entry = itemView.findViewById(R.id.row_entry);
            rv = itemView.findViewById(R.id.recyclerViewId);
            itemView.invalidate();
        }
    }
}
