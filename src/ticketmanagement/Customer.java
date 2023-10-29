package ticketmanagement;

import java.util.Date;

public class Customer {

    private int code;
    private String fullName;
    Date dob;
    String type;

    Customer(int code, String fullName, Date dob, String type) {
        this.code = code;
        this.fullName = fullName;
        this.dob = dob;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getDob() {
        return dob;
    }

    public String getType() {
        return type;
    }
}
