package com.app.delock.delockApplication.item;

import android.annotation.SuppressLint;
import android.widget.Filter;

import java.util.ArrayList;
public class FilterHelper extends Filter {
    private static ArrayList<Item> originalList;
    private static ArrayList<Item> currentList;
    @SuppressLint("StaticFieldLeak")
    private static ItemsAdapter adapter;

    public static FilterHelper newInstance(ArrayList<Item> currentList, ItemsAdapter adapter) {
        FilterHelper.adapter = adapter;
        FilterHelper.currentList = currentList;
        return new FilterHelper();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults=new FilterResults();

        if(constraint != null && constraint.length()>0) {
            constraint=constraint.toString().toUpperCase();

            ArrayList<Item> foundFilters = new ArrayList<>();
            String item;

            for (int i=0;i<currentList.size();i++) {
                item = currentList.get(i).title;
                //SEARCH
                if(item.toUpperCase().contains(constraint)) {
                    foundFilters.add(currentList.get(i));
                }
            }
            filterResults.count=foundFilters.size();
            filterResults.values=foundFilters;
        } else {
            filterResults.count=currentList.size();
            filterResults.values=currentList;
        }
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.setItemsList((ArrayList<Item>) filterResults.values);
        adapter.notifyDataSetChanged();
    }
}