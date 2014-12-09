package edio.pemdroid.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import edio.pemdroid.R;
import edio.pemdroid.activity.ExpenseManagerAware;
import edio.pemdroid.model.Expense;
import edio.pemdroid.model.ExpenseManager;
import edio.pemdroid.model.Month;

public class AddExpenceFragment extends Fragment {

    private ToggleButton expenseIncome;

    public static interface OnAddedListener {
        /**
         * Called when AddExpenceFragment successfully adds Expense
         *
         * @param expense
         * @param year
         * @param month
         */
        void onExpenseAdded(Expense expense, int year, Month month);
    }

    private EditText description;
    private EditText centsAmount;
    private ImageButton accept;
    private OnAddedListener onAddedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expence, container, false);
        description = (EditText) view.findViewById(R.id.add_expense_description);

        centsAmount = (EditText) view.findViewById(R.id.add_expense_total);
        centsAmount.setOnEditorActionListener(new MyOnEditorActionListener());

        accept = (ImageButton) view.findViewById(R.id.add_expense_accept);
        accept.setOnClickListener(new AcceptListener());

        expenseIncome = (ToggleButton) view.findViewById(R.id.action_expense_income);
        expenseIncome.setOnClickListener(new ExpenseIncomeClickListener());

        return view;
    }

    public void addExpense() {
        if (validate()) {
            doAddExpense();
            clear();
        }
    }

    private void doAddExpense() {
        String descriptionText = String.valueOf(description.getText());
        String totalText = String.valueOf(centsAmount.getText());
        int cents = (int) Math.round(Double.parseDouble(totalText) * 100);
        int spent = 0, earned = 0;
        if (expenseIncome.isChecked()) {
            earned = cents;
        } else {
            spent = cents;
        }
        Expense exp = new Expense(System.currentTimeMillis(), descriptionText, earned, spent, null);
        ExpenseManager activeExpenseManager = ((ExpenseManagerAware) getActivity()).getActiveExpenseManager();
        activeExpenseManager.write(exp);
        onAddedListener.onExpenseAdded(exp, 0, Month.JAN);
    }

    private boolean validate() {
        boolean valid = true;

        // we allow empty description, since original pem allows that
        String descriptionText = String.valueOf(description.getText());
        if (descriptionText.contains(",")) {
            description.setError("Commas are not allowed");
            description.requestFocus();
            valid &= false;
        }

        String totalText = String.valueOf(centsAmount.getText());
        if (totalText.isEmpty()) {
            centsAmount.setError("Amount can't be empty");
            if (valid) centsAmount.requestFocus(); // request focus if not requested already
            valid &= false;
        }

        return valid;
    }

    public void setOnAddedListener(OnAddedListener onAddedListener) {
        this.onAddedListener = onAddedListener;
    }

    public void clear() {
        description.setText(null);
        centsAmount.setText(null);
    }

    private class MyOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addExpense();
            }
            return false;
        }
    }

    private class AcceptListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            addExpense();
        }
    }

    private class ExpenseIncomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            centsAmount.requestFocus();
        }
    }

}
