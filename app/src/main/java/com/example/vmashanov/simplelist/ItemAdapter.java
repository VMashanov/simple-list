package com.example.vmashanov.simplelist;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public final class ItemAdapter extends BaseAdapter {

    private List<Item> list;
    private LayoutInflater layoutInflater;
    private boolean isRoot;

    public ItemAdapter(Context context, boolean root, List<Item> list) {
        this.list = list;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.isRoot = root;
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
        return  isRoot == true ? rootLayout(i, view, viewGroup) : childrenLayout(i, view, viewGroup);
    }

    private View rootLayout (int i, View view, ViewGroup viewGroup) {
        view = viewIsNotPresent(R.layout.root_list_item, view, viewGroup);

        TextView titleTextView = view.findViewById(R.id.rootListItemTitle);
        titleTextView.setText(getItemTitle(i));

        return view;
    }

    private View childrenLayout (int i, View view, ViewGroup viewGroup) {
//        view = viewIsNotPresent(R.layout.list_item, view, viewGroup);
//
//        TextView titleTextView = view.findViewById(R.id.childrenListItemTitle);
//        titleTextView.setText(getItemTitle(i));
//
//        TextView descriptionTextView = view.findViewById(R.id.childrenListItemDescription);
//        descriptionTextView.setText(getItemDescription(i));
//
//        return view;
        view = viewIsNotPresent(R.layout.root_list_item, view, viewGroup);

        TextView titleTextView = view.findViewById(R.id.rootListItemTitle);

        boolean isDone = getItemIsDone(i);

        if (isDone) {
            titleTextView.setText(Html.fromHtml("<s>" + getItemTitle(i) + "</s>"));
        } else {
            titleTextView.setText(getItemTitle(i));
        }

        return view;
    }

    private View viewIsNotPresent(int layout, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(layout, viewGroup, false);
        }

        return view;
    }

    private String getItemTitle(int i) {
        return list.get(i).getTitle();
    }

    private String getItemDescription(int i) {
        return list.get(i).getDescription();
    }

    public boolean getItemIsDone(int i) {
        return list.get(i).isDone();
    }
    public void setItemIsDone(int i) { list.get(i).setDone(true); }

    public void remove(int i) {
        list.remove(i);
    }
}
