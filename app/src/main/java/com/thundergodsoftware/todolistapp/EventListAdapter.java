package com.thundergodsoftware.todolistapp;

/**
 * Created by Marcus on 1/16/2017.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class EventListAdapter extends BaseAdapter {

    public ArrayList<HashMap<String,String>> list;
    Activity activity;
    TextView txtEvent;
    TextView txtDate;

    public EventListAdapter(Activity activity, ArrayList<HashMap<String,String>> list ) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View  getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        if ( convertView == null ) {
            convertView = inflater.inflate(R.layout.row_eventlist, null );
            txtEvent = (TextView) convertView.findViewById( R.id.event);
            txtDate = (TextView) convertView.findViewById( R.id.date);
        }
        HashMap<String,String> map = list.get(position);
        txtEvent.setText(map.get(TodoItem.EVENTLIST_NAME_COLUMN));
        txtDate.setText(map.get(TodoItem.EVENTLIST_DATE_COLUMN));

        return convertView;
    }
}
