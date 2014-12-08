package edio.pemdroid.model;

import java.util.Date;

public class Expense {

    public static final double CENTS_IN_ONE = 100.0;
    public static final String EMPTY = "";
    private final String description;
    private final String category;
    // original pem operates on floats, not decimals, we, however, keep cents
    private final int spent;
    private final int earned;
    private final int total;
    private final Date timestamp;

    /**
     * Create {@linkplain edio.pemdroid.model.Expense} instance setting all expense properties
     *
     * @param timestamp   expense issue time
     * @param description expense description
     * @param earned      earn amount in cents (if actually an income)
     * @param spent       spent amount in cents
     * @param category    expense category
     */
    public Expense(long timestamp, String description, int earned, int spent, String category) {
        this.description = description;
        this.category = category == null ? EMPTY : category;
        this.spent = spent;
        this.earned = earned;
        this.timestamp = new Date(timestamp/1000*1000);
        this.total = this.earned - this.spent;
    }

    /**
     * Create @{link Expense} instance from CSV string
     *
     * @param csv string containing 5 values
     * @return
     */
    public static Expense fromCsv(String csv) {
        String[] tokens = csv.split(",", -1);
        if (tokens.length != 5) {
            throw new IllegalArgumentException("CSV must contain 5 values");
        }

        try {
            long timestamp = Long.valueOf(tokens[0]) * 1000;
            String description = tokens[1];
            int earned = (int) Math.round(Double.parseDouble(tokens[2]) * 100);
            int spent = (int) Math.round(Double.parseDouble(tokens[3]) * 100);
            String category = tokens[4];
            return new Expense(timestamp, description, earned, spent, category);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String toCsv() {
        return String.format("%d,%s,%.2f,%.2f,%s", timestamp.getTime() / 1000, description,
                earned / CENTS_IN_ONE, spent / CENTS_IN_ONE, category);
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    /**
     * Get spent amount in cents
     * @return cents
     */
    public int getSpent() {
        return spent;
    }

    /**
     * Get earned amount in cents
     * @return cents
     */
    public int getEarned() {
        return earned;
    }

    /**
     * Get total (i.e. earned - spent) in cents
     * @return earned - spent
     */
    public int getTotal() {
        return total;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (earned != expense.earned) return false;
        if (spent != expense.spent) return false;
        if (category != null ? !category.equals(expense.category) : expense.category != null) return false;
        if (!description.equals(expense.description)) return false;
        if (!timestamp.equals(expense.timestamp)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + spent;
        result = 31 * result + earned;
        result = 31 * result + timestamp.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s%s %.2f", description, category.isEmpty() ? "" : " [" + category + "]", total / CENTS_IN_ONE);
    }

}
