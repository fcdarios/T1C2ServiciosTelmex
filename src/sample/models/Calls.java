package sample.models;

public class Calls {

    private  int id_calls;
    private String date_hour,telephone_number,minutes;

    public Calls(int id_calls, String date_hour, String telephone_number, String minutes) {
        this.id_calls = id_calls;
        this.date_hour = date_hour;
        this.telephone_number = telephone_number;
        this.minutes = minutes;
    }

    public int getId_calls() {
        return id_calls;
    }

    public void setId_calls(int id_calls) {
        this.id_calls = id_calls;
    }

    public String getDate_hour() {
        return date_hour;
    }

    public void setDate_hour(String date_hour) {
        this.date_hour = date_hour;
    }

    public String getTelephone_number() {
        return telephone_number;
    }

    public void setTelephone_number(String telephone_number) {
        this.telephone_number = telephone_number;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }
}
