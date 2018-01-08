package com.example.vmashanov.simplelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public final class ItemAdapter extends BaseAdapter {

    private List<Item> list;
    private LayoutInflater layoutInflater;

    public ItemAdapter(Context context, List<Item> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.root_list_item, viewGroup, false);
        }

        TextView titleTextView = view.findViewById(R.id.rootListItemTitle);
        titleTextView.setText(getItemTitle(i));

        return view;
    }

    private String getItemTitle(int i) {
        return list.get(i).getTitle();
    }

    private String getItemDescription(int i) {
        return list.get(i).getDescription();
    }

    private boolean getItemIsDone(int i) {
        return list.get(i).isDone();
    }

    public void remove(int i) {
        list.remove(i);
    }
}
