package sample.models;

public class Customer {
    private int id_customer, cp;
    private City id_city;
    private String first_name, last_name, address;

    public Customer(int id_customer, String first_name, String last_name, String address, int cp, City id_city) {
        this.id_customer = id_customer;
        this.cp = cp;
        this.id_city = id_city;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
    }

    public Customer() {
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public City getId_city() {
        return id_city;
    }

    public void setId_city(City id_city) {
        this.id_city = id_city;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String firs_name) {
        this.first_name = firs_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Id "+id_customer;
    }
}
