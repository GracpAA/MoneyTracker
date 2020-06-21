package com.example.gracp.moneytracker.api;

public class BalanceResult extends Result {
    public long totalExpenses;
    public long totalIncome;

    public BalanceResult(long totalExpenses, long totalIncome) {
        this.totalExpenses = totalExpenses;
        this.totalIncome = totalIncome;
    }
}