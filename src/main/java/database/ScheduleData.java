package database;

import java.time.LocalDate;

public class ScheduleData {

    private Long employeeNumber;
    private String fullName;
    private String shift;
    private LocalDate date;
    private String scheduleNumber;
    private String shiftNumber;
    private String schedulePattern;

    // Конструктор
    public ScheduleData(Long employeeNumber, String fullName, String shift, LocalDate date, String scheduleNumber, String shiftNumber, String schedulePattern) {
        this.employeeNumber = employeeNumber;
        this.fullName = fullName;
        this.shift = shift;
        this.date = date;
        this.scheduleNumber = scheduleNumber;
        this.shiftNumber = scheduleNumber;
        this.schedulePattern = schedulePattern;
    }

    // Геттеры и сеттеры

    public Long getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Long employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getScheduleNumber() {
        return scheduleNumber;
    }

    public void setScheduleNumber(String scheduleNumber) {
        this.scheduleNumber = scheduleNumber;
    }

    public String getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(String shiftNumber) {
        this.shiftNumber = shiftNumber;
    }

    public String getSchedulePattern() {
        return schedulePattern;
    }

    public void setSchedulePattern(String schedulePattern) {
        this.schedulePattern = schedulePattern;
    }

    // Переопределяем toString() для удобства вывода данных в консоль
    @Override
    public String toString() {
        return "ScheduleData{" +
                "employeeNumber=" + employeeNumber +
                ", fullName='" + fullName + '\'' +
                ", shift='" + shift + '\'' +
                ", date=" + date +
                ", scheduleNumber='" + scheduleNumber + '\'' +
                ", shiftNumber='" + shiftNumber + '\'' +
                ", schedulePattern='" + schedulePattern + '\'' +
                '}';
    }

}
