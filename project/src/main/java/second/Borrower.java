package second;

import java.util.Date;

public class Borrower {
    private String name;
    private String surname;
    private String secondName;
    private Date dateOfBirth;
    private String gender;
    private Integer incomePerMonth;
    private Integer expensesPerMonth;
    private Employer employer;

    public Borrower(String name, String surname, String secondName, Date dateOfBirth, String gender, Integer incomePerMonth, Integer expensesPerMonth, Employer employer) {
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.employer = employer;
        this.incomePerMonth = incomePerMonth;
        this.expensesPerMonth = expensesPerMonth;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getOtchevstvo() {
        return secondName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public Employer getEmployer() {
        return employer;
    }

    public Integer getIncomePerMonth() {
        return incomePerMonth;
    }

    public Integer getExpensesPerMonth() {
        return expensesPerMonth;
    }
}
