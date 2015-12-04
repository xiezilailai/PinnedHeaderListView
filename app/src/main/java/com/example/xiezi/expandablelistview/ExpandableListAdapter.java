package com.example.xiezi.expandablelistview;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 蝎子莱莱123 on 2015/12/3.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {


    private List<String> groupArray;
    private List<List<String>> childArray;
    private Context context;
    private ExpandableListView expandableListView;

    public  ExpandableListAdapter(List<String>groupArray,List<List<String>>childArray,Context context,ExpandableListView listView){
        this.groupArray=groupArray;
        this.childArray=childArray;
        this.context=context;
        this.expandableListView=listView;
    }


    @Override
    public int getGroupCount() {
        return groupArray.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childArray.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupArray.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childArray.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout group_view = (RelativeLayout) inflater.inflate(R.layout.group_view, null);
        TextView tv = (TextView) group_view.findViewById(R.id.group_textView);
        tv.setText(groupArray.get(groupPosition));

        return group_view;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout child_view = (RelativeLayout) inflater.inflate(R.layout.child_view, null);
        TextView tv = (TextView) child_view.findViewById(R.id.child_textView);
        tv.setText(childArray.get(groupPosition).get(childPosition));
        return child_view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public TextView getGenericView(String string) {
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 64);
        TextView text = new TextView(context);
        text.setLayoutParams(layoutParams);
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        text.setPadding(36, 0, 0, 0);
        text.setText(string);
        return text;

    }
}