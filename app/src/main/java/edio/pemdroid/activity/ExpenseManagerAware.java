package edio.pemdroid.activity;

import edio.pemdroid.model.ExpenseManager;

public interface ExpenseManagerAware {
    ExpenseManager getActiveExpenseManager();
}
