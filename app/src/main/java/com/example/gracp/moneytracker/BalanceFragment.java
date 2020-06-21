package com.example.gracp.moneytracker;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gracp.moneytracker.api.Api;
import com.example.gracp.moneytracker.api.BalanceResult;


import java.io.IOException;


public class BalanceFragment extends Fragment {

    private TextView balance;
    private TextView expense;
    private TextView income;
    private DiagramView diagram;

    private Api api;
    private static final int LOADER_BALANCE = 3;

    public static BalanceFragment createFragment() {
        return new BalanceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = ((App) getActivity().getApplication()).getApi();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        balance = view.findViewById(R.id.balance);
        expense = view.findViewById(R.id.expense);
        income = view.findViewById(R.id.income);
        diagram = view.findViewById(R.id.diagram);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            loadBalance();
        }

    }

    private void loadBalance() {
        getLoaderManager().initLoader(LOADER_BALANCE, null, new LoaderManager.LoaderCallbacks<BalanceResult>() {

            @SuppressLint("StaticFieldLeak")
            @Override
            public Loader<BalanceResult> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<BalanceResult>(getContext()) {
                    @Override
                    public BalanceResult loadInBackground() {
                        try {
                            return api.balance().execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<BalanceResult> loader, BalanceResult data) {
                balance.setText(getString(R.string.price, data.totalIncome - data.totalExpenses));
                expense.setText(getString(R.string.price, data.totalExpenses));
                income.setText(getString(R.string.price, data.totalIncome));
                diagram.update(data.totalExpenses, data.totalIncome);;
            }

            @Override
            public void onLoaderReset(Loader<BalanceResult> loader) {

            }
        }).forceLoad();
    }

}