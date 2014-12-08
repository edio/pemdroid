package edio.pemdroid.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edio.pemdroid.R;
import edio.pemdroid.model.Expense;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class ExpenseArrayAdapter extends ArrayAdapter<Expense> {
    private final DecimalFormat fmt = new DecimalFormat("0.00");

    public ExpenseArrayAdapter(Context context, List<Expense> expenses) {
        super(context, 0, expenses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expense item = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_expense, parent, false);
        TextView description = (TextView) rowView.findViewById(R.id.adapter_expense_description);
        description.setText(item.getDescription());
        TextView total = (TextView) rowView.findViewById(R.id.adapter_expense_total);
        total.setText(fmt.format(item.getTotal()));
        TextView category = (TextView) rowView.findViewById(R.id.adapter_expense_category);
        category.setText(item.getCategory());
        TextView timestamp = (TextView) rowView.findViewById(R.id.adapter_expense_date);
        Date date = item.getTimestamp();
        CharSequence relativeDateTimeString = DateUtils
                .getRelativeDateTimeString(getContext(), date.getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0);
        timestamp.setText(relativeDateTimeString);

        return rowView;
    }
}
