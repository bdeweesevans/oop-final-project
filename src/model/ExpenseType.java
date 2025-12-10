package model;

public enum ExpenseType {
    SCHOOL,
	MEDICAL,
    HOUSING,
    TRANSPORT,
    FOOD,
    FUN,
    MISC;
    
    @Override
    public String toString() {
        String raw = name();
        return raw.charAt(0) + raw.substring(1).toLowerCase();
    }
}