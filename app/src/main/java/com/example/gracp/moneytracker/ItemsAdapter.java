package com.example.gracp.moneytracker;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    private List<Item> items = Collections.emptyList();

    public void setItems(List<Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("1","onCreateViewHolder");
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        Log.d("1","onBind");
        Item item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        Log.d("1","getItemCount");
        return items.size();
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView price;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.id_item_name);
            price = itemView.findViewById(R.id.id_item_price);
        }

        public void bind(Item item){
            String priceValue = String.valueOf(item.price)+ " \u20BD";
            SpannableString rubleConvert = new SpannableString(priceValue);
            rubleConvert.setSpan(new ForegroundColorSpan(Color.RED), priceValue.length()-1, priceValue.length(), 0);
            price.setText(rubleConvert);
            name.setText(item.name);
        }
    }
}
