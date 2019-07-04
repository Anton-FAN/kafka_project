package second;

public class Employer {
    private Integer INN;
    private String name;
    private Integer phoneNumber;

    public Employer(Integer INN, String name, Integer phoneNumber) {
        this.INN = INN;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Integer getINN() {
        return INN;
    }
    public String getName() {
        return name;
    }
    public Integer getPhoneNumber() {
        return phoneNumber;

    }
}
