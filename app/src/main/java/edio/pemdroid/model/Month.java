package edio.pemdroid.model;

public enum Month {
    JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC;

    private final String pemMonth;

    private Month() {
        pemMonth = toCamelCase(this.name());
    }

    /**
     * Get month as it is in pem
     *
     * @return 3-letter month code in camel case
     */
    public String getPemMonth() {
        return pemMonth;
    }

    /**
     * Get {@linkplain Month} instance from 3-letter month code
     *
     * @param pemMonth 3-letter month code as in pem
     * @return instance
     * @throws java.lang.IllegalArgumentException if passed string doesn't represent a month
     */
    public static Month fromPemMonth(String pemMonth) {
        return Month.valueOf(pemMonth.toUpperCase());
    }

    public static Month fromOrdinal(int ordinal) {
        return values()[ordinal];
    }

    /**
     * Tests whether arbitrary string represents 3-letter month code as in pem
     *
     * @param string arbitrary string
     * @return <tt>true</tt> if string represents a month, <tt>false</tt> otherwise
     */
    public static boolean isPemMonth(String string) {
        for (Month month : values()) {
            if (month.pemMonth.equals(string)) {
                return true;
            }
        }
        return false;
    }

    private static String toCamelCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            chars[i] = Character.toLowerCase(chars[i]);
        }
        return String.valueOf(chars);
    }

}
