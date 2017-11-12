package com.example.gracp.moneytracker;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gracp.moneytracker.api.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.gracp.moneytracker.Item.TYPE_UNKNOWN;

public class ItemsFragment extends Fragment {


    public static final String KEY_TYPE = "TYPE";
    private static final int LOADER_ITEMS = 0;
    private ItemsAdapter adapter;
    private Api api;
    private String type = TYPE_UNKNOWN;
    private List<Item> items = new ArrayList<>();


    public static ItemsFragment createItemsFragment(String type) {
        ItemsFragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ItemsFragment.KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ItemsAdapter();
        api = ((App) getActivity().getApplication()).getApi();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recycler = view.findViewById(R.id.recycler);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                intent.putExtra(AddActivity.EXTRA_TYPE,type);
                startActivityForResult(intent,AddActivity.RESULT);
            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);

        type = getArguments().getString(KEY_TYPE, TYPE_UNKNOWN);
        if (type.equals(TYPE_UNKNOWN)) {
            throw new IllegalStateException("Unknown Fragment Type");
        }


        adapter.setItems(items);

        loadItems();

    }

    private void loadItems() {
        getLoaderManager().initLoader(LOADER_ITEMS, null, new LoaderManager.LoaderCallbacks<List<Item>>() {

            @Override
            public Loader<List<Item>> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<List<Item>>(getContext()) {
                    @Override
                    public List<Item> loadInBackground() {
                        try {
                            List<Item> items = api.items(type).execute().body();
                            return items;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<List<Item>> loader, List<Item> items) {
                if (items == null) {
                    showError("Произошла ошибка");
                } else {
                    adapter.setItems(items);
                }
            }

            @Override
            public void onLoaderReset(Loader<List<Item>> loader) {

            }
        }).forceLoad();
    }

    private void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AddActivity.RESULT && resultCode==RESULT_OK){

            Item item = (Item)data.getSerializableExtra(AddActivity.RESULT_ITEM);
            Toast.makeText(getContext(), item.name+";"+item.price, Toast.LENGTH_SHORT).show();

        }
    }
}
