package pt.ulisboa.tecnico.cmu.proj;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmu.proj.quiz.ChildItemsInfo;
import pt.ulisboa.tecnico.cmu.proj.quiz.GroupItemsInfo;

/**
 * Created by ist426300 on 08-05-2018.
 */

public class MyExpandableListAdapter implements ExpandableListAdapter {

    private Context context;
    private ArrayList<GroupItemsInfo> teamName;

    public MyExpandableListAdapter(Context context, ArrayList<GroupItemsInfo> deptList) {
        this.context = context;
        this.teamName = deptList;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return teamName.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ChildItemsInfo> productList = teamName.get(groupPosition).getChilds();
        return productList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return teamName.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ChildItemsInfo> productList = teamName.get(groupPosition).getChilds();
        return productList.get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupItemsInfo headerInfo = (GroupItemsInfo) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.group_items, null);
        }

        TextView heading = (TextView) convertView.findViewById(R.id.question);
        heading.setText(headerInfo.getQuestion().trim());
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildItemsInfo detailInfo = (ChildItemsInfo) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_items, null);
        }
        TextView childItem_1 = (TextView) convertView.findViewById(R.id.option);
        childItem_1.setText(detailInfo.getPosition().trim());

        TextView childItem_2 = (TextView) convertView.findViewById(R.id.choice);
        childItem_2.setText(detailInfo.getName().trim());

            CheckBox childItem_3 = (CheckBox) convertView.findViewById(R.id.check);
        childItem_3.setChecked(detailInfo.getFlag());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}