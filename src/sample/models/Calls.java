package sample.models;


import java.sql.Date;

public class Calls {

    private  int id_calls, minutes, noCall;
    private Customer id_customer;
    private String phone_number;
    private String date_hour;

    public Calls(int id_calls,Customer id_customer, String date_hour, String phone_number, int minutes) {
        this.id_calls = id_calls;
        this.minutes = minutes;
        this.id_customer = id_customer;
        this.date_hour = date_hour;
        this.phone_number = phone_number;
    }

    public Calls(int noCall, String date_hour, String phone_number, int minutes) {
        this.minutes = minutes;
        this.noCall = noCall;
        this.phone_number = phone_number;
        this.date_hour = date_hour;
    }

    public Calls() {
    }

    public int getNoCall() {
        return noCall;
    }

    public void setNoCall(int noCall) {
        this.noCall = noCall;
    }

    public int getId_calls() {
        return id_calls;
    }

    public void setId_calls(int id_calls) {
        this.id_calls = id_calls;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public Customer getId_customer() {
        return id_customer;
    }

    public void setId_customer(Customer id_customer) {
        this.id_customer = id_customer;
    }

    public String getDate_hour() {
        return date_hour;
    }

    public void setDate_hour(String date_hour) {
        this.date_hour = date_hour;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
