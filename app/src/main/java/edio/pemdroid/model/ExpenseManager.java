package edio.pemdroid.model;

import java.io.*;
import java.util.*;

public class ExpenseManager {
    private final File homeDir;
    private final FileFilter yearFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            return file.isDirectory() & file.getName().matches("\\d\\d");
        }
    };
    private final FileFilter monthFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            return file.isFile() & Month.isPemMonth(file.getName());
        }
    };

    /**
     * Creates {@linkplain edio.pemdroid.model.ExpenseManager}
     *
     * @param homeDir File representing pem home directory. Directory must exist and contain valid data
     */
    public ExpenseManager(File homeDir) {
        this.homeDir = homeDir;
    }

    public List<Expense> readAll() {
        List<Expense> expenses = new ArrayList<>();
        File[] years = homeDir.listFiles(yearFilter);
        for (File year : years) {
            File[] months = year.listFiles(monthFilter);
            for (File month : months) {
                List<Expense> monthExpenses = readFile(month);
                expenses.addAll(monthExpenses);
            }
        }
        // TODO refine signature and logic
        return expenses;
    }

    public List<Expense> readMonth(int year, Month month) {
        File expenseFile = new File(homeDir, toPath(year, month));
        if (expenseFile.isFile()) {
            return readFile(expenseFile);
        } else {
            return Collections.emptyList();
        }
    }

    public void append(Expense expense) {
        Date timestamp = expense.getTimestamp();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        int year = calendar.get(Calendar.YEAR);
        int monthNumber = calendar.get(Calendar.MONTH);
        Month month = Month.fromOrdinal(monthNumber);
    }

    private void appendToFile(File expenseFile, Expense expense) {
        FileWriter fw;
        try {
            if (!expenseFile.exists()) {
                fw = new FileWriter(expenseFile, false);
            } else if (expenseFile.isFile()) {
                fw = new FileWriter(expenseFile, true);
            } else {
                throw new IllegalStateException("Not a file: " + expenseFile);
            }
        } catch (IOException e) {
            // TODO throw proper exception
            throw new IllegalStateException(e);
        }
        PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
        pw.println();
    }

    private List<Expense> readFile(File expenseFile) {
        List<Expense> expenses = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(expenseFile))) {
            String csv;
            while ((csv = in.readLine()) != null) {
                expenses.add(Expense.fromCsv(csv));
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return expenses;
    }

    private static String toPath(int year, Month month) {
        return String.format("%2d/%s", year, month.getPemMonth());
    }

}

