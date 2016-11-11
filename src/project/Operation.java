package project;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

public class Operation {
    
    private String name, cat;
    private int id, month;
    private double cash;
    private LocalDate date;
    
    public Operation(int id, String name, String cat, Date dt, double cash) {
        Calendar now = Calendar.getInstance();
        this.month =  now.get(Calendar.MONTH) + 1;
        this.id = id;
        this.name = name;
        this.cat = cat;
        this.date = dt.toLocalDate();
        this.cash = cash;
        
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCat() {
        return cat;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public double getCash() {
        return cash;
    }
    
    public int getMonth() {
        return month;
    }
}
