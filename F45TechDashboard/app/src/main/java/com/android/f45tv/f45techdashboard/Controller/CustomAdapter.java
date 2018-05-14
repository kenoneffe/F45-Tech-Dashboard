package com.android.f45tv.f45techdashboard.Controller;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.f45tv.f45techdashboard.R;

import java.util.List;

import static android.content.ContentValues.TAG;

public class CustomAdapter extends ArrayAdapter<NotificationController>{
    private Context context;
    private List<NotificationController> notificationList;
    public LayoutInflater inflater;
    ViewHolder holder;
    NotificationController dataModel;

    public CustomAdapter(@NonNull Context appcontext, List<NotificationController> resource) {
        super(appcontext, R.layout.notification ,resource);
        this.context = appcontext;
        this.notificationList = resource;
        inflater = LayoutInflater.from(appcontext);
    }


    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public NotificationController getItem(int i) {
        return this.notificationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        dataModel = this.notificationList.get(position);
        View v = convertView;
        ViewHolder holder = null;
        Log.d(TAG, "getView: POSITION :" + position);
        final View mresult;
        if(v==null){
            v = LayoutInflater.from(context).inflate(R.layout.notification, parent, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.subject.setText(notificationList.get(position).getSubject());
        holder.source.setText(notificationList.get(position).getSource());
        holder.subject.setText(notificationList.get(position).getSubject());

        final ViewHolder finalHolder = holder;
        holder.row_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You Clicked " + dataModel.getId(), Toast.LENGTH_SHORT).show();
                finalHolder.row_entry.setBackground(Drawable.createFromPath("@drawable/layout_roundedclicked"));
            }
        });
        return v;
    }
    private class ViewHolder {
        protected TextView priority;
        protected TextView source;
        protected TextView subject;
        protected TableRow row_entry;

        public ViewHolder(View itemView){
            priority = itemView.findViewById(R.id.priority_entry);
            source = itemView.findViewById(R.id.source_entry);
            subject = itemView.findViewById(R.id.subject_entry);
            row_entry = itemView.findViewById(R.id.row_entry);
        }

    }
}
