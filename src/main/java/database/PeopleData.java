package database;

public class PeopleData {
    private int employee_number;
    private String full_name;
    private String shift;

    public PeopleData(int employee_number, String full_name, String shift) {
        this.employee_number = employee_number;
        this.full_name = full_name;
        this.shift = shift;
    }

    public int getEmployee_number() {
        return employee_number;
    }

    public void setEmployee_number(int employee_number) {
        this.employee_number = employee_number;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
