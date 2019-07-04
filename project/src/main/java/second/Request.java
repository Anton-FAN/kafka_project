package second;

import java.util.Date;

public class Request {
    private Date date;
    private Integer amount;
    private Integer months;
    private Borrower borrower;

    public Request(Date date, Integer amount, Integer months, Borrower borrower) {
        this.date = date;
        this.amount = amount;
        this.months = months;
        this.borrower = borrower;
    }

    public Date getDate() {
        return date;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getMonths() {
        return months;
    }

    public Borrower getBorrower() {
        return borrower;
    }
}
