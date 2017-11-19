package com.example.gracp.moneytracker;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    private List<Item> items = Collections.emptyList();
    private ItemsAdapterListener listener = null;
    public static SparseBooleanArray itemsSelected = new SparseBooleanArray();

    public void setListener(ItemsAdapterListener listener) {
        this.listener = listener;
    }



    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("1", "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        Log.d("1", "onBind");
        Item item = items.get(position);
        holder.bind(item, position, itemsSelected.get(position, false), listener);
    }

    @Override
    public int getItemCount() {
        Log.d("1", "getItemCount");
        return items.size();
    }

    public void clearSelections(){
        itemsSelected.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return itemsSelected.size();
    }

    public Item remove(int pos) {
        final Item item = items.remove(pos);
        notifyItemRemoved(pos);
        return item;
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(itemsSelected.size());
        for (int i = 0; i < itemsSelected.size(); i++) {
            items.add(itemsSelected.keyAt(i));
        }
        return items;
    }
    public void toggleSelection(int pos) {
        if (itemsSelected.get(pos, false)) {
            itemsSelected.delete(pos);
        } else {
            itemsSelected.put(pos, true);
        }
        notifyItemChanged(pos);

    }
    public Item getItemByPosition(int pos) {
        return items.get(pos);
    }

    public void updateId(Item item, int id) {
        items.add(item);
    }


    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView price;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.id_item_name);
            price = itemView.findViewById(R.id.id_item_price);
        }

        public void bind(final Item item, final int position, boolean selected, final ItemsAdapterListener listener) {
            String priceValue = String.valueOf(item.price) + " \u20BD";
            SpannableString rubleConvert = new SpannableString(priceValue);
            rubleConvert.setSpan(new ForegroundColorSpan(Color.WHITE), priceValue.length() - 1, priceValue.length(), 0);
            price.setText(rubleConvert);
            name.setText(item.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item, position);

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(item, position);
                    return true;
                }
            });

            itemView.setActivated(selected);
        }
    }
}