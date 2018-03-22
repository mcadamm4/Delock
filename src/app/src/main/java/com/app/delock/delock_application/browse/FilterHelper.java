package com.app.delock.delock_application.browse;

import android.annotation.SuppressLint;
import android.widget.Filter;

import com.app.delock.delock_application.item.Item;
import com.app.delock.delock_application.item.ItemsAdapter;

import java.util.ArrayList;
public class FilterHelper extends Filter {
    private static ArrayList<Item> originalList;
    private static ArrayList<Item> currentList;
    @SuppressLint("StaticFieldLeak")
    private static ItemsAdapter adapter;

    public static FilterHelper newInstance(ArrayList<Item> currentList, ItemsAdapter adapter) {
        FilterHelper.adapter=adapter;
        FilterHelper.currentList = currentList;
        return new FilterHelper();
    }

    /*
    - Perform actual filtering.
     */
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults=new FilterResults();

        if(constraint != null && constraint.length()>0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();

            //HOLD FILTERS WE FIND
            ArrayList<Item> foundFilters=new ArrayList<>();

            String item;

            //ITERATE CURRENT LIST
            for (int i=0;i<currentList.size();i++)
            {
                item= currentList.get(i).getName();


                //SEARCH
                if(item.toUpperCase().contains(constraint))
                {
                    //ADD IF FOUND
                    foundFilters.add(currentList.get(i));
                }
            }

            //SET RESULTS TO FILTER LIST
            filterResults.count=foundFilters.size();
            filterResults.values=foundFilters;
        }else
        {
            //NO ITEM FOUND.LIST REMAINS INTACT
            filterResults.count=currentList.size();
            filterResults.values=currentList;
        }

        //RETURN RESULTS
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.setItemsList((ArrayList<Item>) filterResults.values);
        adapter.notifyDataSetChanged();
    }
}