package com.example.electricitybillcalculator;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HistoryAdapter implements ListAdapter {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<ItemHistory> hisList;
    private Context context;

    public HistoryAdapter(Context context, ArrayList<ItemHistory> list) {
        this.hisList = list;
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return hisList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemHistory itemHistory = hisList.get(i);
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.list_history, null);
            TextView month = view.findViewById(R.id.his_month);
            TextView price = view.findViewById(R.id.his_price);
            TextView unit = view.findViewById(R.id.his_unit);
            ImageView icon = view.findViewById(R.id.his_icon);

            month.setText(itemHistory.getMonth());
            price.setText(df.format(itemHistory.getPrice()));
            unit.setText(df.format(itemHistory.getUnit()));
            icon.setImageResource(itemHistory.getIcon());
        }
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return hisList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
