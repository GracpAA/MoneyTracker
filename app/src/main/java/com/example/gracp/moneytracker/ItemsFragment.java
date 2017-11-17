package com.example.gracp.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.gracp.moneytracker.api.Api;

import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.gracp.moneytracker.Item.TYPE_UNKNOWN;


public class ItemsFragment extends Fragment {

    private static final String TAG = "ItemsFragment";

    private static final int LOADER_ITEMS = 0;

    private static final String KEY_TYPE = "TYPE";
    private String type = TYPE_UNKNOWN;

    private ItemsAdapter adapter;
    private Api api;
    private ActionMode actionMode;

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

        type = getArguments().getString(KEY_TYPE, TYPE_UNKNOWN);

        if (type.equals(TYPE_UNKNOWN)) {
            throw new IllegalStateException("Unknown Fragment Type");
        }

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
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);

        adapter.setListener(new ItemsAdapterListener() {
            @Override
            public void onItemClick(Item item, int position) {
                if (isInActionMode()) {
                    toggleSelection(position);
                }
            }

            @Override
            public void onItemLongClick(Item item, int position) {
                if (isInActionMode()) {
                    return;
                }

                actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
                toggleSelection(position);
            }

            private void toggleSelection(int position) {
                adapter.toggleSelection(position);
            }

            private boolean isInActionMode() {
                return actionMode != null;
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddActivity.startForResult(ItemsFragment.this, type, AddActivity.RESULT);
            }
        });

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
        if (requestCode == AddActivity.RESULT && resultCode == RESULT_OK) {
            Item item = (Item) data.getSerializableExtra(AddActivity.RESULT_ITEM);
            Toast toast = Toast.makeText(getContext(), item.name, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void removeSelectedItems() {
        for (int i = adapter.getSelectedItems().size() - 1; i >= 0; i--) {
            adapter.remove(adapter.getSelectedItems().get(i));
        }

    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.items_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_remove:
                    showDialog();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelections();
            actionMode = null;
        }

    };

    private void showDialog() {
        DialogListener dialogListener = new DialogListener() {
            @Override
            public void onPositiveClick() {
                removeSelectedItems();
                actionMode.finish();
            }

            @Override
            public void onNegativeClick() {
                actionMode.finish();
            }
        };

        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.setListener(dialogListener);
        dialog.show(getFragmentManager(), "Confirmation");
    }
}