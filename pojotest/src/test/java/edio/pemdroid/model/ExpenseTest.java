package edio.pemdroid.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class ExpenseTest {
    @Test
    public void testFromCsv() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear(Calendar.MILLISECOND);

        Date timestamp = calendar.getTime();
        String description = "expense";
        double spent = 42.429;
        double earned = 100500.00001;
        String category = "tag";

        String csv = String.valueOf(timestamp.getTime() / 1000) + "," +
                description + "," +
                String.valueOf(earned) + "," +
                String.valueOf(spent) + "," +
                category;

        Expense expense = Expense.fromCsv(csv);
        Assert.assertEquals(timestamp, expense.getTimestamp());
        Assert.assertEquals(description, expense.getDescription());
        Assert.assertEquals("Rounding should work", 4243, expense.getSpent());
        Assert.assertEquals(10050000, expense.getEarned());
        Assert.assertEquals(category, expense.getCategory());
    }

    @Test
    public void testFromCsvMalformed() {
        String csv = "foo,bar";
        try {
            Expense expense = Expense.fromCsv(csv);
            Assert.fail("Exception should've been thrown");
        } catch (IllegalArgumentException iae) {
            // ok
        }
    }

    @Test
    public void testFromCsvNoTag() {
        String csv = "1,desc,3,4,";
        Expense expense = Expense.fromCsv(csv);
        Assert.assertTrue(expense.getCategory().isEmpty());
    }

    @Test
    public void testFromCsvIllegalTag() {
        String csv = "1,desc,2,3,tag,with,comma";
        try {
            Expense.fromCsv(csv);
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFromCsvInvalidNumbers() {
        String[] csvs = new String[]{
                "×,desc,2,3,tag",
                "1,desc,×,3,tag",
                "1,desc,2,×,tag"
        };
        for (String csv : csvs) {
            try {
                Expense.fromCsv(csv);
                Assert.fail("Exception should've been thrown");
            } catch (IllegalArgumentException iae) {
                // ok
            }
        }
    }

    @Test
    public void testFromCsvToCsv() {
        Random r = new Random();
        Expense expected = new Expense(System.currentTimeMillis(), "description",
                r.nextInt(), r.nextInt(), "category");
        String csv = expected.toCsv();
        Expense actual = expected.fromCsv(csv);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testFromCsvToCsvNoCategory() {
        Random r = new Random();
        Expense expected = new Expense(System.currentTimeMillis(), "description",
                r.nextInt(), r.nextInt(), null);
        String csv = expected.toCsv();
        Expense actual = expected.fromCsv(csv);
        Assert.assertEquals(expected, actual);
    }

}
