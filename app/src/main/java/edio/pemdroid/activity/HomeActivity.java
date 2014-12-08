package edio.pemdroid.activity;

import android.app.ListFragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import edio.pemdroid.R;
import edio.pemdroid.model.Expense;
import edio.pemdroid.model.ExpenseManager;

import java.io.File;
import java.util.List;


public class HomeActivity extends ActionBarActivity implements ExpenseHistoryProvider {

    private ExpenseManager expenseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ListFragment entriesList = (ListFragment) getFragmentManager().findFragmentById(R.id.entries_list_fragment);

        File pem = getPemFolder("pem");
        expenseManager = new ExpenseManager(pem);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                break;
            default:
                throw new IllegalArgumentException("Unhandled action!");
        }

        return super.onOptionsItemSelected(item);
    }

    public File getPemFolder(String path) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), path);
        return file;
    }

    @Override
    public List<Expense> getExpenses() {
        return expenseManager.readAll();
    }

}
