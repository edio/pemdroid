package edio.pemdroid.activity;

import edio.pemdroid.model.Expense;
import edio.pemdroid.model.ExpenseManager;

import java.util.List;

public interface ExpenseHistoryProvider {
    List<Expense> getExpenses();
}
