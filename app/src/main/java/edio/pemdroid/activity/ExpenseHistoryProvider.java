package edio.pemdroid.activity;

import edio.pemdroid.model.Expense;

import java.util.List;

public interface ExpenseHistoryProvider {
    List<Expense> getExpenses();
}
