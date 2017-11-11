package com.example.gracp.moneytracker;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gracp.moneytracker.api.Api;

import java.util.ArrayList;
import java.util.List;

import static com.example.gracp.moneytracker.Item.TYPE_UNKNOWN;

public class ItemsFragment extends Fragment {


    public static final String KEY_TYPE = "TYPE";

    private ItemsAdapter adapter;
    private Api api;
    private String type = TYPE_UNKNOWN;
    public static List<Item> items = new ArrayList<>();


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
        api = ((App)getActivity().getApplication()).getApi();
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
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);

        type = getArguments().getString(KEY_TYPE, TYPE_UNKNOWN);
        if (type.equals(TYPE_UNKNOWN)) {
            throw new IllegalStateException("Unknown Fragment Type");
                   }


        adapter.setItems(items);

        loadItems();

    }

    private void loadItems(){
        new LoadItemsTask(new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case ITEMS_LOADED:
                        adapter.setItems((List<Item>)msg.obj); break;

                    case ERROR: showError((String)msg.obj);
                }
            }
        }).start();
    }

    private final static int ITEMS_LOADED = 0;
    private final static int ERROR = 1;


    private class LoadItemsTask implements Runnable{

        private Handler handler;
        private Thread thread;

        public LoadItemsTask(Handler handler){

            thread = new Thread(this);
            this.handler = handler;
        }

        public void start(){
            thread.start();
        }

        @Override
        public void run() {
            try {
                List<Item> items = api.items(type).execute().body();
                handler.obtainMessage(ITEMS_LOADED, items).sendToTarget();
            } catch (Exception e) {
                handler.obtainMessage(ERROR, e.getMessage()).sendToTarget();
            }
        }
    }

    private void showError(String error){
        Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
    }

}
