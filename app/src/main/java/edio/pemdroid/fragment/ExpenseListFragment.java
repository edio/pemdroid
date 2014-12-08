package edio.pemdroid.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.renderscript.RSInvalidStateException;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import edio.pemdroid.activity.ExpenseHistoryProvider;
import edio.pemdroid.adapter.ExpenseArrayAdapter;
import edio.pemdroid.model.Expense;

import java.util.List;

public class ExpenseListFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity parentActivity = getActivity();
        if (parentActivity instanceof ExpenseHistoryProvider) {
            List<Expense> expenses = ((ExpenseHistoryProvider) parentActivity).getExpenses();
            setListAdapter(new ExpenseArrayAdapter(parentActivity, expenses));
        } else {
            throw new IllegalStateException("ExpenseListFragment can only be embedded into " +
                    "ExpenseHistoryProvider activity");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + getListAdapter().getItem(position));
    }
}
