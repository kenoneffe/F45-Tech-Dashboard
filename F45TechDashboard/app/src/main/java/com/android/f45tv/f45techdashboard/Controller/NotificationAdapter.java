package com.android.f45tv.f45techdashboard.Controller;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.f45tv.f45techdashboard.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/*
* Recycler.Adapter
* Recycler.ViewHolder
* */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{

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
        final NotificationController notificationController = notificationList.get(position);
        final int adapterPosition = holder.getAdapterPosition();
        holder.priority.setText(notificationController.getPriority());
        holder.source.setText(notificationController.getSource());
        holder.subject.setText(notificationController.getSubject());
        notificationController.setPosition(adapterPosition);
        holder.row_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationController.setRead(true);
                if (notificationController.getRead()){
//                    holder.row_entry.setBackground(Drawable.createFromPath("@drawable/layout_roundedclicked"));
                    holder.row_entry.setBackground(ContextCompat.getDrawable(context, R.drawable.layout_roundedclicked));
                    notifyDataSetChanged();
                }
//                int num = notificationController.getPosition();
//                Toast.makeText(context, "Read Ticket #"+(num + 1) , Toast.LENGTH_SHORT).show();
            }
        });

        if(!notificationController.getRead()){
            Log.d(TAG, "onBindViewHolder: it went inside.");
            holder.row_entry.setBackground(ContextCompat.getDrawable(context, R.drawable.layout_rounded));
            //holder.row_entry.setBackground(Drawable.createFromPath("@drawable/layout_rounded"));
        }

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
            setIsRecyclable(false);
            itemView.setOnClickListener(this);
            itemView.invalidate();
        }
        @Override
        public void onClick(View view) {
            NotificationController notificationController = notificationList.get(getAdapterPosition());
            int position = getAdapterPosition();

        }
    }


}
