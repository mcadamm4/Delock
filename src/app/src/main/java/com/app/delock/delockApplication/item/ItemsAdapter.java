package com.app.delock.delockApplication.item;

/**
 * Created by Marky on 15/03/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.delock.delockApplication.R;

import java.util.ArrayList;

//An adapter is responsible for taking data and making it into a view

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<Item> itemsList;
    private ItemsAdapterListener listener;

    //Define data
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, cost;
        public ImageView thumbnail, overflow;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            cost = view.findViewById(R.id.cost);
            thumbnail = view.findViewById(R.id.thumbnail);
            overflow = view.findViewById(R.id.overflow);
            cardView = view.findViewById(R.id.card_view);
        }
    }

    //Constructor
    public ItemsAdapter(Context mContext, ArrayList<Item> itemsList, ItemsAdapterListener listener) {
        this.mContext = mContext;
        this.itemsList = itemsList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if(!itemsList.isEmpty()) {
            final Item item = itemsList.get(position);

            holder.thumbnail.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(item.imageFiles.get(0))));
            holder.title.setText(item.title);
            holder.cost.setText(String.valueOf(item.itemPrice));
            holder.thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

            holder.overflow.setOnClickListener(view -> showPopupMenu(holder.overflow, position));

            Intent intent = new Intent(mContext, ItemActivity.class);
            intent.putExtra("Item", item);

            holder.cardView.setOnClickListener(view -> {
                mContext.startActivity(intent);
                // listener.onCardSelected(position, holder.thumbnail);
            });

            holder.thumbnail.setOnClickListener(view -> {
                mContext.startActivity(intent);
                // listener.onCardSelected(position, holder.thumbnail);
            });
        }
    }

    // Show popup menu when tapping on 3 dots
    private void showPopupMenu(View view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_item, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }


    // Click listener for popup menu items
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        int position;

        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
//                    listener.onAddToFavoriteSelected(position);
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setItemsList(ArrayList<Item> filteredItems)
    {
        this.itemsList=filteredItems;
    }

    public Filter getFilter() {
        return FilterHelper.newInstance(itemsList,this);
    }

    public interface ItemsAdapterListener {
        void onAddToFavoriteSelected(int position);

        void onCardSelected(int position, ImageView thumbnail);
    }
}